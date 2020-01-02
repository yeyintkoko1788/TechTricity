package com.yeyintkoko.techtricity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.adapter.GamingAdapter;
import com.yeyintkoko.techtricity.adapter.NewsFeedsAdapter;
import com.yeyintkoko.techtricity.common.SmartScrollListener;
import com.yeyintkoko.techtricity.helper.AppConstant;
import com.yeyintkoko.techtricity.helper.ServiceHelper;
import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.model.RecentReturnModel;
import com.yeyintkoko.techtricity.tech_tricity_interface.NewsFeedInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFeedFragment extends Fragment implements NewsFeedInterface, PullRefreshLayout.OnRefreshListener {
    private Context context;

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerView;

    @BindView(R.id.rv_news_feed)
    RecyclerView rvNewsFeed;

    @BindView(R.id.loading_shimmer)
    ShimmerFrameLayout loadingShimmer;

    @BindView(R.id.swipeRefreshLayout)
    PullRefreshLayout swipRefresh;

    private NewsFeedsAdapter newsFeedsAdapter;
    private Call<RecentReturnModel> callNews;
    private ArrayList<ArticleModel> articleListModels;
    private ServiceHelper.ApiService service;
    private SmartScrollListener smartScrollListener;
    private int PAGE_LIMIT = 10;
    private int pageNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);
        ButterKnife.bind(this, rootView);
        context = rootView.getContext();
        init();
        return rootView;
    }

    private void init(){
        service = ServiceHelper.getClient(context);
        newsFeedsAdapter = new NewsFeedsAdapter(this,context);

        pageNumber = AppConstant.INITITAL_PAGE_NUMBER;

        smartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {
                pageNumber++;
                loadingShimmer.setVisibility(View.VISIBLE);
                loadingShimmer.startShimmer();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        handler.postDelayed(this, 2000);
                    }
                }, 2000);
                callNews = service.getRecentNews(PAGE_LIMIT,pageNumber);
                callNews.enqueue(new Callback<RecentReturnModel>() {
                    @Override
                    public void onResponse(Call<RecentReturnModel> call, Response<RecentReturnModel> response) {
                        //loadingShimmer.hideShimmer();
                        loadingShimmer.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getResults().size() != 0) {
                                    articleListModels = response.body().getResults();
                                    for (ArticleModel model : articleListModels){
                                        newsFeedsAdapter.add(model);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RecentReturnModel> call, Throwable t) {
                        loadingShimmer.stopShimmer();
                        loadingShimmer.setVisibility(View.GONE);
                    }
                });
            }
        });
        rvNewsFeed.setHasFixedSize(true);
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
        rvNewsFeed.addOnScrollListener(smartScrollListener);
        rvNewsFeed.setAdapter(newsFeedsAdapter);
        getNewsFeed();

        swipRefresh.setOnRefreshListener(this);
        swipRefresh.setRefreshing(false);
        swipRefresh.setRefreshStyle(PullRefreshLayout.STYLE_CIRCLES);
    }

    private void getNewsFeed(){
        shimmerView.setVisibility(View.VISIBLE);
        shimmerView.startShimmer();

        callNews = service.getRecentNews(PAGE_LIMIT,pageNumber);
        callNews.enqueue(new Callback<RecentReturnModel>() {
            @Override
            public void onResponse(Call<RecentReturnModel> call, Response<RecentReturnModel> response) {
                //shimmerView.stopShimmer();
                //shimmerView.hideShimmer();
                shimmerView.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResults().size() != 0) {
                            articleListModels = response.body().getResults();
                            if (response.body().getResults().size() != 0){
                                articleListModels = response.body().getResults();
                                for (ArticleModel model : articleListModels){
                                    newsFeedsAdapter.add(model);
                                }
                                rvNewsFeed.setVisibility(View.GONE);
                                rvNewsFeed.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<RecentReturnModel> call, Throwable t) {
                handleFailure();
            }

            private void handleFailure(){
                //shimmerView.stopShimmer();
               // shimmerView.hideShimmer();
                shimmerView.setVisibility(View.GONE);
                newsFeedsAdapter.clearFooter();
                newsFeedsAdapter.showRetry(R.layout.recycler_footer_retry, R.id.retry_container, new NewsFeedsAdapter.OnRetryListener(){
                    @Override
                    public void onRetry() {
                        getNewsFeed();
                    }
                });
            }
        });
    }

    @Override
    public void performTransation(Intent intent, ImageView imageView) {
        androidx.core.util.Pair pair = new Pair(imageView, context.getResources().getString(R.string.detail_transition_name));
        //final Pair<ImageView, String> pair = Pair.create(imageView, activity.getString(R.string.detail_transition_name));
        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pair);
        //final ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, pair);
        ActivityCompat.startActivity(context, intent, activityOptions.toBundle());
    }

    @Override
    public void onRefresh() {
        init();
    }
}
