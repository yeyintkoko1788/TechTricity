package com.yeyintkoko.techtricity.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.adapter.GamingAdapter;
import com.yeyintkoko.techtricity.common.BaseActivity;
import com.yeyintkoko.techtricity.common.SmartScrollListener;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanProgressDialog;
import com.yeyintkoko.techtricity.custom_control.MyanTextView;
import com.yeyintkoko.techtricity.helper.AppConstant;
import com.yeyintkoko.techtricity.helper.ServiceHelper;
import com.yeyintkoko.techtricity.model.ArticleByAuthorModel;
import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.model.ArticleReturnModel;
import com.yeyintkoko.techtricity.model.AuthorModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_author)
    ImageView ivAuthor;

    @BindView(R.id.tv_article_count)
    MyanBoldTextView tvArticleCount;

    @BindView(R.id.tv_author_name)
    MyanBoldTextView tvAuthorName;

    @BindView(R.id.tv_author_description)
    MyanTextView tvAuthorDescription;

    @BindView(R.id.tv_facebook)
    MyanBoldTextView tvFacebook;

    @BindView(R.id.rv_articles)
    RecyclerView rvArticles;

    @BindView(R.id.ll_facebook)
    LinearLayout llFacebook;

    private static String IE_AUTHOR_ID = "AUTHOR_ID";

    private Call<AuthorModel> callAuthorDetail;
    private Call<ArticleByAuthorModel> callArticleList;
    private ServiceHelper.ApiService service;
    private AuthorModel authorModel;
    private Intent intent;
    private int authorID;
    private GamingAdapter gamingAdapter;
    private ArrayList<ArticleModel> articleListModels;
    private SmartScrollListener smartScrollListener;
    MyanProgressDialog myanProgressDialog;
    private int PAGE_LIMIT = 10;
    private int pageNumber;

    public static Intent getAuthorDetailIntent(Context context, int authorID) {
        Intent intent = new Intent(context, AuthorDetailActivity.class);
        intent.putExtra(IE_AUTHOR_ID, authorID);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_author_detail;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setupToolbar(true);
        setupToolbarText(" ");
        ButterKnife.bind(this);
        intent = getIntent();
        authorID = intent.getIntExtra(IE_AUTHOR_ID,0);
        init();
    }

    private void init(){
        service = ServiceHelper.getClient(this);
        gamingAdapter = new GamingAdapter(false);
        myanProgressDialog = new MyanProgressDialog(this);

        pageNumber = AppConstant.INITITAL_PAGE_NUMBER;

        smartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {
                myanProgressDialog.showDialog();
                pageNumber++;

                callArticleList = service.getNewsByAuthor(authorID,pageNumber,PAGE_LIMIT);
                callArticleList.enqueue(new Callback<ArticleByAuthorModel>() {
                    @Override
                    public void onResponse(Call<ArticleByAuthorModel> call, Response<ArticleByAuthorModel> response) {
                        myanProgressDialog.hideDialog();
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResults().size() != 0) {
                                    articleListModels = response.body().getResults();
                                    for (ArticleModel model : articleListModels){
                                        gamingAdapter.add(model);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleByAuthorModel> call, Throwable t) {
                        myanProgressDialog.hideDialog();
                    }
                });
            }
        });

        rvArticles.setHasFixedSize(true);
        rvArticles.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        rvArticles.addOnScrollListener(smartScrollListener);
        rvArticles.setAdapter(gamingAdapter);

        getAuthorDetail();

        llFacebook.setOnClickListener(this);
    }

    private void getAuthorDetail(){
        callAuthorDetail = service.getAuthorDetail(authorID);
        callAuthorDetail.enqueue(new Callback<AuthorModel>() {
            @Override
            public void onResponse(Call<AuthorModel> call, Response<AuthorModel> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        authorModel = response.body();
                        displayView();
                        getArticlesList();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthorModel> call, Throwable t) {

            }
        });
    }

    private void getArticlesList() {
        gamingAdapter.clear();
        gamingAdapter.showLoading();
        callArticleList = service.getNewsByAuthor(authorID,pageNumber,PAGE_LIMIT);
        callArticleList.enqueue(new Callback<ArticleByAuthorModel>() {
            @Override
            public void onResponse(Call<ArticleByAuthorModel> call, Response<ArticleByAuthorModel> response) {
                gamingAdapter.clearFooter();
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if (response.body().getResults().size() != 0) {
                            articleListModels = response.body().getResults();
                            for (ArticleModel model : articleListModels){
                                gamingAdapter.add(model);
                            }
                            rvArticles.setVisibility(View.GONE);
                            rvArticles.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    handleFailure();
                }
            }

            private void handleFailure() {
                gamingAdapter.clearFooter();
                gamingAdapter.showRetry(R.layout.recycler_footer_retry, R.id.retry_container, () -> getArticlesList());
            }

            @Override
            public void onFailure(Call<ArticleByAuthorModel> call, Throwable t) {
                handleFailure();
            }
        });
    }

    private void displayView() {
        Glide.with(ivAuthor)
                .asBitmap()
                .load(authorModel.getPhotoUrl())
                .into(ivAuthor);
        tvArticleCount.setMyanmarText(String.valueOf(authorModel.getArticleCount()));
        tvAuthorDescription.setMyanmarText(authorModel.getUserBlockquote());
        tvAuthorName.setMyanmarText(authorModel.getName());
        tvFacebook.setMyanmarText(authorModel.getFacebookUserName());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ll_facebook:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(authorModel.getFacebookUserID()));
                startActivity(intent);
                break;
        }
    }
}
