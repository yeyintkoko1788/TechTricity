package com.yeyintkoko.techtricity.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.activity.AuthorDetailActivity;
import com.yeyintkoko.techtricity.adapter.AuthorPagerAdapter;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanTextView;
import com.yeyintkoko.techtricity.custom_control.ShadowTransformer;
import com.yeyintkoko.techtricity.custom_control.TextViewFactory;
import com.yeyintkoko.techtricity.helper.ServiceHelper;
import com.yeyintkoko.techtricity.model.AuthorListModel;
import com.yeyintkoko.techtricity.model.AuthorReturnModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.author_view_pager)
    ViewPager authorViewPager;

    @BindView(R.id.iv_author)
    ImageView ivAuthor;

    @BindView(R.id.tv_name)
    TextSwitcher tvName;

    @BindView(R.id.tv_description)
    TextSwitcher tvDescription;

    @BindView(R.id.tv_articles)
    TextSwitcher tvArticles;

    @BindView(R.id.iv_facebook)
    ImageView ivFacebook;

    @BindView(R.id.rl_more)
    RelativeLayout rlMore;

    private Context context;
    private ServiceHelper.ApiService service;
    private Call<AuthorReturnModel> callAuthor;

    private AuthorPagerAdapter authorPagerAdapter;
    private ShadowTransformer mShadowTransformer;
    private String fblink;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_author, container, false);
        ButterKnife.bind(this, rootView);
        context = rootView.getContext();

        init();

        return rootView;
    }

    private void init(){
        service = ServiceHelper.getClient(context);
        authorPagerAdapter = new AuthorPagerAdapter();
        mShadowTransformer = new ShadowTransformer(authorViewPager,authorPagerAdapter);
        getAuthorList();

        ivFacebook.setOnClickListener(this);
        rlMore.setOnClickListener(this);
    }

    private void getAuthorList() {
        callAuthor = service.getAuthorList();
        callAuthor.enqueue(new Callback<AuthorReturnModel>() {
            @Override
            public void onResponse(Call<AuthorReturnModel> call, Response<AuthorReturnModel> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        ArrayList<AuthorListModel> models = response.body().getResults();
                        displayAuthorList(models);
                    }
                }else {
                    handleFailure();
                }
            }

            private void handleFailure() {
                Toast.makeText(context,"Load Fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AuthorReturnModel> call, Throwable t) {
                handleFailure();
            }
        });
    }

    private void displayAuthorList(ArrayList<AuthorListModel> models) {
        authorPagerAdapter.ClearData();
        for (AuthorListModel model : models){
            authorPagerAdapter.addCardItemList(model);
        }
        authorViewPager.setAdapter(authorPagerAdapter);
        authorViewPager.setPageTransformer(false, mShadowTransformer);
        authorViewPager.setOffscreenPageLimit(3);
        mShadowTransformer.enableScaling(true);
        //updateUI(models.get(0));
        initSwitcher(models.get(0));
        authorViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateUI(authorPagerAdapter.getItemAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initSwitcher(AuthorListModel model){
        Glide.with(ivAuthor)
                .load(model.getUserlist().getPhotoUrl())
                .into(ivAuthor);
        tvName.setFactory(new TextViewFactory(context,R.style.NameTextView, false,"bold","center",true));
        tvName.setCurrentText(model.getUserlist().getName());

        tvDescription.setInAnimation(context, android.R.anim.fade_in);
        tvDescription.setOutAnimation(context, android.R.anim.fade_out);
        tvDescription.setFactory(new TextViewFactory(context,R.style.DescriptionAuthorTextView, false,"text","",true));
        tvDescription.setCurrentText(model.getUserlist().getUserBlockquote());

        tvArticles.setFactory(new TextViewFactory(context,R.style.ArticlesTextView,false,"bold","center",false));
        tvArticles.setCurrentText(String.valueOf(model.getArticlecount()));

        userId = model.getUserlist().getID();
        if (model.getUserlist().getFacebookUserID() != null){
            fblink = model.getUserlist().getFacebookUserID();
            ivFacebook.setVisibility(View.VISIBLE);
        }else {
            ivFacebook.setVisibility(View.GONE);
        }
    }

    private void updateUI(AuthorListModel model){
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};
        Glide.with(ivAuthor)
                .load(model.getUserlist().getPhotoUrl())
                .into(ivAuthor);
        //tvName.setMyanmarText(model.getUserlist().getName());
        //tvDescription.setMyanmarText(model.getUserlist().getDescription());
        //tvArticles.setMyanmarText(String.valueOf(model.getArticlecount()));

        tvName.setInAnimation(context, animH[0]);
        tvName.setOutAnimation(context, animH[1]);
        tvName.setText(model.getUserlist().getName());

        tvDescription.setInAnimation(context, animV[0]);
        tvDescription.setOutAnimation(context, animV[1]);
        tvDescription.setText(model.getUserlist().getUserBlockquote());

        tvArticles.setInAnimation(context, animV[0]);
        tvArticles.setOutAnimation(context, animV[1]);
        tvArticles.setText(String.valueOf(model.getArticlecount()));
        userId = model.getUserlist().getID();
        if (model.getUserlist().getFacebookUserID() != null){
            fblink = model.getUserlist().getFacebookUserID();
            ivFacebook.setVisibility(View.VISIBLE);
        }else {
            ivFacebook.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.iv_facebook:
                flipAnimation(ivFacebook,fblink);
                break;
            case R.id.rl_more:
                startDetailActivity();
                break;
        }
    }

    private void startDetailActivity() {
        startActivity(AuthorDetailActivity.getAuthorDetailIntent(context,userId));
    }

    public void flipAnimation(final ImageView view, String link){
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                oa2.start();
            }
        });
        oa1.start();
        oa2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });
    }
}
