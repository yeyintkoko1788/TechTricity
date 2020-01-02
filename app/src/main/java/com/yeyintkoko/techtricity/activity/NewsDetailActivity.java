package com.yeyintkoko.techtricity.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.nex3z.flowlayout.FlowLayout;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.adapter.GamingAdapter;
import com.yeyintkoko.techtricity.common.BaseActivity;
import com.yeyintkoko.techtricity.custom_control.MyDateFormat;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanTextView;
import com.yeyintkoko.techtricity.helper.ServiceHelper;
import com.yeyintkoko.techtricity.model.ArticleDetailModel;
import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.model.AuthorModel;
import com.yeyintkoko.techtricity.model.RecentReturnModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ivDetailImage)
    ImageView ivDetailImage;

    @BindView(R.id.tv_date)
    MyanTextView tvDate;

    @BindView(R.id.tv_author)
    MyanTextView tvAuthor;

    @BindView(R.id.tv_title)
    MyanBoldTextView tvTitle;

    @BindView(R.id.main_view)
    LinearLayout mainView;

    @BindView(R.id.loading_shimmer)
    ShimmerFrameLayout loadingShimmer;

    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.tv_views)
    MyanTextView tvViews;

    @BindView(R.id.ll_share)
    LinearLayout llShare;

    @BindView(R.id.ivAd1)
    ImageView ivAd1;

    @BindView(R.id.ivAd2)
    ImageView ivAd2;

    @BindView(R.id.ll_reference)
    LinearLayout llReference;

    @BindView(R.id.iv_loading)
    ImageView ivLoading;

    @BindView(R.id.tags_list)
    FlowLayout tagsList;

    @BindView(R.id.iv_author)
    ImageView ivAuthor;

    @BindView(R.id.tv_name)
    MyanBoldTextView tvName;

    @BindView(R.id.tv_author_description)
    MyanTextView tvAuthorDescription;

    @BindView(R.id.tv_facebook)
    MyanBoldTextView tvFacebook;

    @BindView(R.id.ll_facebook)
    LinearLayout llFacebook;

    @BindView(R.id.rv_related_articles)
    RecyclerView rvRelatedArticles;

    @BindView(R.id.tv_category)
    MyanBoldTextView tvCategory;

    private static Activity activity;
    private static String IE_NEWS_ID = "NEWS_ID";
    private static String IE_PHOTO_URL = "PHOTO_URL";
    Intent intent;
    private Call<ArticleDetailModel> callNewsDetail;
    private Call<AuthorModel> callAuthorDetail;
    private Call<ArrayList<ArticleModel>> getRelatedArticle;
    private GamingAdapter gamingAdapter;
    private ArrayList<ArticleModel> articleListModels;
    private ServiceHelper.ApiService service;
    private MyDateFormat myDateFormat;
    private ArticleModel model;
    private String fbShareLink;
    private AuthorModel authorModel;

    public static Intent getDetailIntent(Context context, int newsID, String photoUrl) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(IE_NEWS_ID, newsID);
        intent.putExtra(IE_PHOTO_URL, photoUrl);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setupToolbar(true);
        setupToolbarText(" ");
        intent = getIntent();
        init();
    }
    private void init(){

        service = ServiceHelper.getClient(this);
        myDateFormat = new MyDateFormat();
        gamingAdapter = new GamingAdapter(false);

        Glide.with(this)
                .asGif()
                .load(R.drawable.ic_loading)
                .into(ivLoading);

        ivDetailImage.setTransitionName(getString(R.string.detail_transition_name));
        String Url = intent.getStringExtra(IE_PHOTO_URL);
        assert Url != null;
        if (!Url.equals("")){
            Glide.with(this)
                    .asBitmap()
                    .load(Url)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            onPalette(Palette.from(resource).generate());
                            ivDetailImage.setImageBitmap(resource);
                            return false;
                        }
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        public void onPalette(Palette palette) {
                            if (null != palette) {
                                //ViewGroup parent = (ViewGroup) imageView.getParent().getParent();
                                int color1 = palette.getDominantColor(Color.GRAY);
                                int transparentColor = adjustAlpha(color1, 0.5f);
                                int[] colors = {getColor(R.color.colorWhite),getColor(R.color.colorTransparent),transparentColor};
                                //create a new gradient color
                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP, colors);
                                gd.setCornerRadius(0f);

                                ivDetailImage.setForeground(gd);
                            }
                        }
                    })
                    .into(ivDetailImage);
        }
       /* Glide.with(this)
                .asBitmap()
                .load(Url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        onPalette(Palette.from(resource).generate());
                        ivDetailImage.setImageBitmap(resource);
                        return false;
                    }
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onPalette(Palette palette) {
                        if (null != palette) {
                            //ViewGroup parent = (ViewGroup) imageView.getParent().getParent();
                            int color1 = palette.getDominantColor(Color.GRAY);
                            int transparentColor = adjustAlpha(color1, 0.5f);
                            int[] colors = {getColor(R.color.colorWhite),getColor(R.color.colorTransparent),transparentColor};
                            //create a new gradient color
                            GradientDrawable gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP, colors);
                            gd.setCornerRadius(0f);

                            ivDetailImage.setForeground(gd);
                        }
                    }
                })
                .into(ivDetailImage);*/

        rvRelatedArticles.setHasFixedSize(true);
        rvRelatedArticles.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        rvRelatedArticles.setAdapter(gamingAdapter);
        getNewsDetail();

        llShare.setOnClickListener(this);
        llFacebook.setOnClickListener(this);
    }

    private void getAuthorDetail() {
        callAuthorDetail = service.getAuthorDetail(model.getUserID());
        callAuthorDetail.enqueue(new Callback<AuthorModel>() {
            @Override
            public void onResponse(Call<AuthorModel> call, Response<AuthorModel> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        authorModel = response.body();
                        displayView();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthorModel> call, Throwable t) {

            }
        });
    }

    private void displayView() {
        Glide.with(ivAuthor)
                .load(authorModel.getPhotoUrl())
                .into(ivAuthor);
        tvName.setMyanmarText(authorModel.getName());
        tvAuthorDescription.setMyanmarText(authorModel.getUserBlockquote());
        tvFacebook.setMyanmarText(authorModel.getFacebookUserName());
    }

    @ColorInt
    public int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    private void getNewsDetail(){
        int id = intent.getIntExtra(IE_NEWS_ID,0);
        mainView.setVisibility(View.GONE);
        //loadingShimmer.setVisibility(View.VISIBLE);
        //loadingShimmer.startShimmer();
        callNewsDetail = service.getNewsDetail(id);
        callNewsDetail.enqueue(new Callback<ArticleDetailModel>() {
            @Override
            public void onResponse(Call<ArticleDetailModel> call, Response<ArticleDetailModel> response) {
                mainView.setVisibility(View.VISIBLE);
                loadingShimmer.stopShimmer();
                loadingShimmer.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body() != null){
                        model = response.body().getArticle();
                        fbShareLink = response.body().getFbsharelink();
                        displayNewsDetail(model);
                        getAuthorDetail();
                        getRelatedArticleByID();
                    }
                }else {
                    handleFailure();
                }
            }

            private void handleFailure(){
                mainView.setVisibility(View.GONE);
                //loadingShimmer.stopShimmer();
                //loadingShimmer.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArticleDetailModel> call, Throwable t) {
                handleFailure();
            }
        });
    }

    private void getRelatedArticleByID() {
        gamingAdapter.clear();
        gamingAdapter.showLoading();

        getRelatedArticle = service.getRelatedNews(model.getID());
        getRelatedArticle.enqueue(new Callback<ArrayList<ArticleModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ArticleModel>> call, Response<ArrayList<ArticleModel>> response) {
                gamingAdapter.clearFooter();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().size() > 0){
                            for (ArticleModel articleModel : response.body()){
                                gamingAdapter.add(articleModel);
                            }
                            rvRelatedArticles.setVisibility(View.GONE);
                            rvRelatedArticles.setVisibility(View.VISIBLE);
                        }
                    }
                }else {
                    handleFailure();
                }
            }

            private void handleFailure() {
                gamingAdapter.clearFooter();
                gamingAdapter.showRetry(R.layout.recycler_footer_retry, R.id.retry_container, () -> getRelatedArticleByID());
            }

            @Override
            public void onFailure(Call<ArrayList<ArticleModel>> call, Throwable t) {
                handleFailure();
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void displayNewsDetail(ArticleModel model){
        Glide.with(this)
                .asBitmap()
                .load(model.getArticlePhotoUrl())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        onPalette(Palette.from(resource).generate());
                        ivDetailImage.setImageBitmap(resource);
                        return false;
                    }
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void onPalette(Palette palette) {
                        if (null != palette) {
                            //ViewGroup parent = (ViewGroup) imageView.getParent().getParent();
                            int color1 = palette.getDominantColor(Color.GRAY);
                            int transparentColor = adjustAlpha(color1, 0.5f);
                            int[] colors = {getColor(R.color.colorWhite),getColor(R.color.colorTransparent),transparentColor};
                            //create a new gradient color
                            GradientDrawable gd = new GradientDrawable(
                                    GradientDrawable.Orientation.BOTTOM_TOP, colors);
                            gd.setCornerRadius(0f);

                            ivDetailImage.setForeground(gd);
                        }
                    }
                })
                .into(ivDetailImage);
        Glide.with(this)
                .asBitmap()
                .load(model.getAd1PhotoUrl())
                .into(ivAd1);
        Glide.with(this)
                .asBitmap()
                .load(model.getAd2PhotoUrl())
                .into(ivAd2);
        tvCategory.setMyanmarText(model.getCategoryName());
        tvAuthor.setMyanmarText(model.getUserName());
        tvDate.setMyanmarText(myDateFormat.getDate(myDateFormat.DATE_FORMAT_DD_MM_AAA,model.getCreatedTime()));
        tvTitle.setMyanmarText(model.getTitle());
        if (model.getViewCount() < 1){
            tvViews.setMyanmarText(model.getViewCount()+" view");
        }else {
            tvViews.setMyanmarText(model.getViewCount()+" views");
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setInitialScale(250);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int progress) {
                try {
                    if (progress < 100 && ivLoading.getVisibility() == View.GONE) {
                        ivLoading.setVisibility(View.VISIBLE);
                    }
                    if (progress == 100) {
                        try {
                            ivLoading.setVisibility(View.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        String bodyHtml = "<iframe width=\"806\" height=\"453\" src=\"https://www.youtube.com/embed/g3Rf5qDuq7M\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
        String style = "<style>iframe{display: inline; height: auto; max-width: 100%;}</style>";
        String style1 = "<style>img{display: inline; height: auto; max-width: 100%;}</style>";
        webView.loadDataWithBaseURL(null, style + style1 + model.getBodyHtml(), "text/html", "utf-8", null);
        if (model.getReferenceData() != null){
            String[] separatedList =  model.getReferenceData().split(",");
            for(int i = 0; i < separatedList.length; i ++){
                String[] sepNameAndLink = separatedList[i].split("_");
                MyanTextView reference = new MyanTextView(this);
                reference.setText(sepNameAndLink[0]);
                reference.setPadding(0,10,0,10);
                reference.setTextColor(getResources().getColor(R.color.color_blue));
                reference.setClickable(true);
                reference.setLinksClickable(true);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                reference.setLayoutParams(params);
                llReference.addView(reference);
                reference.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(TechTricityWebViewActivity.getTechTricityWebViewIntent(getApplicationContext(),sepNameAndLink[1]));
                    }
                });
            }
        }
        if (model.getTagsName() != null){
            String[] separatedList =  model.getTagsName().split(",");
            for(int i = 0; i < separatedList.length; i ++){
                MyanTextView tags = new MyanTextView(this);
                tags.setText(separatedList[i]);
                tags.setPadding(30,10,30,10);
                tags.setTextColor(getResources().getColor(R.color.colorWhite));
                tags.setBackground(getResources().getDrawable(R.drawable.btn_share));
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tags.setLayoutParams(params);
                tags.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(SearchResultActivity.getSearchDetailIntent(NewsDetailActivity.this, String.valueOf(tags.getText())));
                    }
                });
                tagsList.addView(tags);
            }
        }
        tvCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ll_share:
                openShareOption();
                break;
            case R.id.ll_facebook:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(authorModel.getFacebookUserID()));
                startActivity(intent);
                break;
            case R.id.tv_category:
                startActivity(SeeAllActivity.getSeeAllIntent(NewsDetailActivity.this,model.getCategoryName()));
        }
    }

    private void openShareOption(){
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = fbShareLink;
        String shareSub = "TechTricity";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share using"));
    }
}
