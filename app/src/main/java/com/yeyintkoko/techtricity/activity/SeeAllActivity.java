package com.yeyintkoko.techtricity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.adapter.GamingAdapter;
import com.yeyintkoko.techtricity.common.BaseActivity;
import com.yeyintkoko.techtricity.common.SmartScrollListener;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanProgressDialog;
import com.yeyintkoko.techtricity.helper.AppConstant;
import com.yeyintkoko.techtricity.helper.ServiceHelper;
import com.yeyintkoko.techtricity.model.ArticleListModel;
import com.yeyintkoko.techtricity.model.ArticleReturnModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    MyanBoldTextView tvTitle;

    @BindView(R.id.rvRecycler)
    RecyclerView rvRecycler;

    private static String IE_TITLE = "TITLE";

    private Call<ArticleReturnModel> callNews;
    private ServiceHelper.ApiService service;
    private GamingAdapter gamingAdapter;
    private SmartScrollListener smartScrollListener;
    private int PAGE_LIMIT = 10;
    private int pageNumber;
    private String title;
    private MyanProgressDialog myanProgressDialog;

    public static Intent getSeeAllIntent(Context context, String titile) {
        Intent intent = new Intent(context, SeeAllActivity.class);
        intent.putExtra(IE_TITLE, titile);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_see_all;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setupToolbar(true);
        setupToolbarText(" ");
        ButterKnife.bind(this);

        title = getIntent().getStringExtra(IE_TITLE);

        tvTitle.setMyanmarText(title);

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

                callNews = service.getArticleByCategory(PAGE_LIMIT, pageNumber,title,"");
                callNews.enqueue(new Callback<ArticleReturnModel>() {
                    @Override
                    public void onResponse(Call<ArticleReturnModel> call, Response<ArticleReturnModel> response) {
                        myanProgressDialog.hideDialog();
                        if (response.isSuccessful()){
                            if (response.body() != null){
                                ArrayList<ArticleListModel> reviewsList = response.body().getResults();
                                for (ArticleListModel model : reviewsList){
                                    gamingAdapter.add(model.getArticle());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleReturnModel> call, Throwable t) {
                        myanProgressDialog.hideDialog();
                    }
                });
            }
        });
        rvRecycler.setHasFixedSize(true);
        rvRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        rvRecycler.addOnScrollListener(smartScrollListener);
        rvRecycler.setAdapter(gamingAdapter);

        getAllNews();
    }

    private void getAllNews() {
        gamingAdapter.clear();
        gamingAdapter.showLoading();

        callNews = service.getArticleByCategory(PAGE_LIMIT,pageNumber,title,"");
        callNews.enqueue(new Callback<ArticleReturnModel>() {
            @Override
            public void onResponse(Call<ArticleReturnModel> call, Response<ArticleReturnModel> response) {
                gamingAdapter.clearFooter();
                if (response.isSuccessful()){
                    if (response.body() != null){
                        ArrayList<ArticleListModel> reviewsList = response.body().getResults();
                        for (ArticleListModel model : reviewsList){
                            gamingAdapter.add(model.getArticle());
                        }
                    }
                }else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleReturnModel> call, Throwable t) {
                handleFailure();
            }

            private void handleFailure(){
                gamingAdapter.clearFooter();
                gamingAdapter.showRetry(R.layout.recycler_footer_retry, R.id.retry_container, () -> getAllNews());
            }
        });
    }
}
