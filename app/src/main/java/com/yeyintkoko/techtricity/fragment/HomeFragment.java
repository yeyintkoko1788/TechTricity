package com.yeyintkoko.techtricity.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.littlemango.stacklayoutmanager.StackLayoutManager;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;
import com.teresaholfeld.stories.StoriesProgressView;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.adapter.BannerAdapter;
import com.yeyintkoko.techtricity.adapter.FeatureAdapter;
import com.yeyintkoko.techtricity.adapter.NewsAdapter;
import com.yeyintkoko.techtricity.custom_control.MyDateFormat;
import com.yeyintkoko.techtricity.custom_control.TextViewFactory;
import com.yeyintkoko.techtricity.helper.ServiceHelper;
import com.yeyintkoko.techtricity.model.ArticleListModel;
import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.model.ArticleReturnModel;
import com.yeyintkoko.techtricity.model.BannerReturnModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements DiscreteScrollView.OnItemChangedListener<FeatureAdapter.ViewHolder>, StoriesProgressView.StoriesListener, View.OnClickListener {

    @BindView(R.id.rvBanner)
    RecyclerView rvBanner;

    @BindView(R.id.indicator)
    CircleIndicator2 indicator2;

    @BindView(R.id.rvNews)
    RecyclerView rvNews;

    @BindView(R.id.ts_Title)
    TextSwitcher tsTitle;

    @BindView(R.id.ts_author)
    TextSwitcher tsAuthor;

    @BindView(R.id.ts_time)
    TextSwitcher tsTime;

    @BindView(R.id.ts_description)
    TextSwitcher tsDescription;

    @BindView(R.id.rv_feature)
    DiscreteScrollView rvFeature;

    @BindView(R.id.ts_howto_author)
    TextSwitcher tsHowToAuthor;

    @BindView(R.id.ts_howto_title)
    TextSwitcher tsHowToTitle;

    @BindView(R.id.ts_howto_time)
    TextSwitcher tsHowToTime;

    @BindView(R.id.ivHowToImage)
    ImageView ivHowToImage;

    @BindView(R.id.howto)
    StoriesProgressView howTo;

    @BindView(R.id.reverse)
    View reverse;

    @BindView(R.id.skip)
    View skip;

    @BindView(R.id.cv_how_to)
    CardView cvHowTo;

    private static final int PROGRESS_COUNT = 5;
    private int counter = 0;
    private Context context;
    private CardSliderLayoutManager layoutManger;
    private Call<BannerReturnModel> callBanner;
    private Call<ArticleReturnModel> callNews;
    private Call<ArticleReturnModel> callFeature;
    private Call<ArticleReturnModel> callHowTo;
    private ServiceHelper.ApiService service;
    private BannerAdapter bannerAdapter;
    private NewsAdapter newsAdapter;
    private FeatureAdapter featureAdapter;
    private ArrayList<ArticleListModel> articleListModels;
    private ArrayList<ArticleListModel> featureArticleListModels;
    private ArrayList<ArticleListModel> howToListModels;
    private ArrayList<ArticleModel> models;
    private int currentPosition;
    private MyDateFormat myDateFormat;
    private InfiniteScrollAdapter infiniteAdapter;

    private long pressTime = 0L;
    private long limit = 500L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        context = rootView.getContext();
        init();
        return rootView;
    }
    private void init(){
        service = ServiceHelper.getClient(context);
        bannerAdapter = new BannerAdapter();
        newsAdapter = new NewsAdapter();
        featureAdapter = new FeatureAdapter();
        myDateFormat = new MyDateFormat();
        StackLayoutManager.ScrollOrientation orientation = StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP;
        StackLayoutManager manager = new StackLayoutManager(orientation,4);
        manager.setItemOffset(20);
        rvBanner.setLayoutManager(manager);
        rvBanner.setAdapter(bannerAdapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvBanner);
        indicator2.attachToRecyclerView(rvBanner, pagerSnapHelper);

        // optional
        bannerAdapter.registerAdapterDataObserver(indicator2.getAdapterDataObserver());

        rvNews.setHasFixedSize(true);
        rvNews.setAdapter(newsAdapter);
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        //resize item view of news
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        int width = size.x;
        int height = size.y;

        int marginStart = (int) convertDpToPx(context,16f);
        int cardsGap = (int) convertDpToPx(context,12f);
        layoutManger = new CardSliderLayoutManager(marginStart, (int)(width/1.3), cardsGap);
        rvNews.setLayoutManager(layoutManger);

        //feature news
        rvFeature.setHasFixedSize(true);
        rvFeature.setOrientation(DSVOrientation.HORIZONTAL);
        rvFeature.addOnItemChangedListener(this);
        rvFeature.setItemTransitionTimeMillis(300);
        rvFeature.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        new CardSnapHelper().attachToRecyclerView(rvNews);

        getData();

        reverse.setOnClickListener(this);
        skip.setOnClickListener(this);
    }

    private void getData(){
        getBanner();
        getNews();
        getFeatureNews();
        getHowTo();
    }

    private void initSwitcher(){
        tsTitle.setFactory(new TextViewFactory(context,R.style.TitleTextView, false,"bold","",true));
        tsTitle.setCurrentText(articleListModels.get(0).getArticle().getTitle());

        tsDescription.setInAnimation(context, android.R.anim.fade_in);
        tsDescription.setOutAnimation(context, android.R.anim.fade_out);
        tsDescription.setFactory(new TextViewFactory(context,R.style.DescriptionTextView, false,"text","",true));
        tsDescription.setCurrentText(articleListModels.get(0).getArticle().getDescription());

        tsAuthor.setFactory(new TextViewFactory(context,R.style.AuthorTestView,false,"text","",false));
        tsAuthor.setCurrentText(articleListModels.get(0).getArticle().getUserName());

        tsTime.setFactory(new TextViewFactory(context,R.style.TimeTextView,false,"text","end",false));
        tsTime.setCurrentText(myDateFormat.getDate(myDateFormat.DATE_FORMAT_DD_MM_AAA,articleListModels.get(0).getArticle().getCreatedTime()));
    }

    private void initSwitcherHowTo(){
        Glide.with(context)
                .load(howToListModels.get(0).getArticle().getArticlePhotoUrl())
                .into(ivHowToImage);
        tsHowToTitle.setFactory(new TextViewFactory(context,R.style.TitleTextView, false,"bold","",true));
        tsHowToTitle.setCurrentText(howToListModels.get(0).getArticle().getTitle());

        tsHowToAuthor.setFactory(new TextViewFactory(context,R.style.AuthorTestView,false,"text","",false));
        tsHowToAuthor.setCurrentText(howToListModels.get(0).getArticle().getUserName());

        tsHowToTime.setFactory(new TextViewFactory(context,R.style.TimeTextView,false,"text","end",false));
        tsHowToTime.setCurrentText(myDateFormat.getDate(myDateFormat.DATE_FORMAT_DD_MM_AAA,howToListModels.get(0).getArticle().getCreatedTime()));
    }

    private void getBanner(){
        bannerAdapter.clear();
        bannerAdapter.showLoading();
        callBanner = service.getHomeBanner();
        callBanner.enqueue(new Callback<BannerReturnModel>() {
            @Override
            public void onResponse(Call<BannerReturnModel> call, Response<BannerReturnModel> response) {
                bannerAdapter.clearFooter();
                if (response.isSuccessful()){
                    if (response.body().getResults() != null){
                        models = response.body().getResults();
                        for (ArticleModel model : models){
                            bannerAdapter.add(model);
                            final int speedScroll = 3000;
                            final Handler handler = new Handler();
                            final Runnable runnable = new Runnable() {
                                int count = 0;
                                boolean flag = true;
                                @Override
                                public void run() {
                                    if(count < bannerAdapter.getItemCount()){
                                        if(count==bannerAdapter.getItemCount()-1){
                                            flag = false;
                                        }else if(count == 0){
                                            flag = true;
                                        }
                                        if(flag) count++;
                                        else count--;

                                        rvBanner.smoothScrollToPosition(count);
                                        handler.postDelayed(this,speedScroll);
                                    }
                                }
                            };

                            handler.postDelayed(runnable,speedScroll);
                        }
                    }
                }else{
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<BannerReturnModel> call, Throwable t) {
                handleFailure();
            }

            private void handleFailure(){
                bannerAdapter.clearFooter();
                bannerAdapter.showRetry(R.layout.recycler_footer_retry, R.id.retry_container, new BannerAdapter.OnRetryListener(){
                    @Override
                    public void onRetry() {
                        getBanner();
                    }
                });
            }
        });
    }

    private void getNews(){
        newsAdapter.clear();
        newsAdapter.showLoading();
        callNews = service.getArticleByCategory(10,1,"News","");
        callNews.enqueue(new Callback<ArticleReturnModel>() {
            @Override
            public void onResponse(Call<ArticleReturnModel> call, Response<ArticleReturnModel> response) {
                newsAdapter.clearFooter();
                if (response.isSuccessful()) {
                    if (response.body().getResults() != null) {
                        articleListModels = response.body().getResults();
                        for (ArticleListModel model : articleListModels){
                            newsAdapter.add(model.getArticle());
                        }
                        initSwitcher();
                    }
                }else{
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleReturnModel> call, Throwable t) {
                handleFailure();
            }
            private void handleFailure(){
                newsAdapter.clearFooter();
                newsAdapter.showRetry(R.layout.recycler_footer_retry, R.id.retry_container, new NewsAdapter.OnRetryListener(){
                    @Override
                    public void onRetry() {
                        getNews();
                    }
                });
            }
        });
    }
    private void getFeatureNews(){
        featureAdapter.clear();
        featureAdapter.showLoading();
        callFeature = service.getArticleByCategory(10,1,"Features","");
        callFeature.enqueue(new Callback<ArticleReturnModel>() {
            @Override
            public void onResponse(Call<ArticleReturnModel> call, Response<ArticleReturnModel> response) {
                featureAdapter.clearFooter();
                if (response.isSuccessful()) {
                    if (response.body().getResults() != null) {
                        featureArticleListModels = response.body().getResults();
                        for (ArticleListModel model : featureArticleListModels){
                            featureAdapter.add(model.getArticle());
                        }
                        infiniteAdapter = InfiniteScrollAdapter.wrap(featureAdapter);
                        rvFeature.setAdapter(infiniteAdapter);
                    }
                }else{
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleReturnModel> call, Throwable t) {
                handleFailure();
            }
            private void handleFailure(){
                featureAdapter.clearFooter();
                featureAdapter.showRetry(R.layout.recycler_footer_retry, R.id.retry_container, new NewsAdapter.OnRetryListener(){
                    @Override
                    public void onRetry() {
                        getFeatureNews();
                    }
                });
            }
        });
    }

    private void getHowTo(){
        callHowTo = service.getArticleByCategory(10,1,"Reviews","");
        callHowTo.enqueue(new Callback<ArticleReturnModel>() {
            @Override
            public void onResponse(Call<ArticleReturnModel> call, Response<ArticleReturnModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResults().size() != 0){
                            howToListModels = response.body().getResults();
                            initSwitcherHowTo();
                            setUpHowTo();
                        }
                    }
                }else{
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleReturnModel> call, Throwable t) {
                handleFailure();
            }
            private void handleFailure(){
            }
        });
    }

    private void onActiveCardChange(){
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }
        onActiveCardChange(pos);
    }
    private void onActiveCardChange(int pos){
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

        tsAuthor.setInAnimation(context, animH[0]);
        tsAuthor.setOutAnimation(context, animH[1]);
        tsAuthor.setText(articleListModels.get(pos).getArticle().getUserName());

        tsTitle.setInAnimation(context, animV[0]);
        tsTitle.setOutAnimation(context, animV[1]);
        tsTitle.setText(articleListModels.get(pos).getArticle().getTitle());

        tsTime.setInAnimation(context, animV[0]);
        tsTime.setOutAnimation(context, animV[1]);
        tsTime.setText(myDateFormat.getDate(myDateFormat.DATE_FORMAT_DD_MM_AAA,articleListModels.get(pos).getArticle().getCreatedTime()));

        tsDescription.setText(articleListModels.get(pos).getArticle().getDescription());

        currentPosition = pos;
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    private FeatureAdapter.ViewHolder previous = null;

    @Override
    public void onCurrentItemChanged(@Nullable FeatureAdapter.ViewHolder viewHolder, int i) {
        if (viewHolder != null) {
            viewHolder.setTvBannerTitle();
            if (previous != null && previous != viewHolder){
                previous.removeTvBannerTitle();
            }
            previous = viewHolder;
        }
    }

    @Override
    public void onNext() {
        displayHowTo(++counter);
    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;
        displayHowTo(--counter);
    }

    @Override
    public void onComplete() {
        howTo.destroy();
        counter = 0;
        setUpHowTo();
        displayHowTo(counter);
    }

    @Override
    public void onDestroy() {
        howTo.destroy();
        super.onDestroy();
    }

    private void setUpHowTo(){
        //HowTo News
        howTo.setStoriesCount(PROGRESS_COUNT);
        howTo.setStoryDuration(5000L);
        howTo.setStoriesListener(this);
        howTo.startStories(counter);
        reverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressTime = System.currentTimeMillis();
                        howTo.pause();
                        return false;
                    case MotionEvent.ACTION_UP:
                        long now = System.currentTimeMillis();
                        howTo.resume();
                        return limit < now - pressTime;
                }
                return false;
            }
        });
        skip.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pressTime = System.currentTimeMillis();
                        howTo.pause();
                        return false;
                    case MotionEvent.ACTION_UP:
                        long now = System.currentTimeMillis();
                        howTo.resume();
                        return limit < now - pressTime;
                }
                return false;
            }
        });
    }

    private void displayHowTo(int pos){
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};
        Glide.with(context)
                .load(howToListModels.get(pos).getArticle().getArticlePhotoUrl())
                .into(ivHowToImage);
        animH[0] = R.anim.slide_in_left;
        animH[1] = R.anim.slide_out_right;

        animV[0] = R.anim.slide_in_bottom;
        animV[1] = R.anim.slide_out_top;

        tsHowToAuthor.setInAnimation(context, animH[0]);
        tsHowToAuthor.setOutAnimation(context, animH[1]);
        tsHowToAuthor.setText(howToListModels.get(pos).getArticle().getUserName());

        tsHowToTitle.setInAnimation(context, animV[0]);
        tsHowToTitle.setOutAnimation(context, animV[1]);
        tsHowToTitle.setText(howToListModels.get(pos).getArticle().getTitle());

        tsHowToTime.setInAnimation(context, animV[0]);
        tsHowToTime.setOutAnimation(context, animV[1]);
        tsHowToTime.setText(myDateFormat.getDate(myDateFormat.DATE_FORMAT_DD_MM_AAA,howToListModels.get(pos).getArticle().getCreatedTime()));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.reverse:
                howTo.reverse();
                break;
            case R.id.skip:
                howTo.skip();
                break;
        }
    }
}