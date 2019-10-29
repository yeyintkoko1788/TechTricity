package com.yeyintkoko.techtricity.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.common.BaseActivity;
import com.yeyintkoko.techtricity.custom_control.MyDateFormat;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanTextView;
import com.yeyintkoko.techtricity.helper.ServiceHelper;
import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.model.RecentReturnModel;

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

    private static Activity activity;
    private static String IE_NEWS_ID = "NEWS_ID";
    private static String IE_PHOTO_URL = "PHOTO_URL";
    Intent intent;
    private Call<ArticleModel> callNewsDetail;
    private ServiceHelper.ApiService service;
    private MyDateFormat myDateFormat;
    private ArticleModel model;

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

        ivDetailImage.setTransitionName(getString(R.string.detail_transition_name));
        String Url = intent.getStringExtra(IE_PHOTO_URL);
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

        getNewsDetail();

        llShare.setOnClickListener(this);
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
        callNewsDetail.enqueue(new Callback<ArticleModel>() {
            @Override
            public void onResponse(Call<ArticleModel> call, Response<ArticleModel> response) {
                mainView.setVisibility(View.VISIBLE);
                loadingShimmer.stopShimmer();
                loadingShimmer.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    if (response.body() != null){
                        model = response.body();
                        displayNewsDetail(model);
                    }
                }else {
                    handleFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleModel> call, Throwable t) {
                handleFailure();
            }

            private void handleFailure(){
                mainView.setVisibility(View.GONE);
                //loadingShimmer.stopShimmer();
                //loadingShimmer.setVisibility(View.GONE);
            }
        });
    }

    private void displayNewsDetail(ArticleModel model){
        tvAuthor.setMyanmarText(model.getUserName());
        tvDate.setMyanmarText(myDateFormat.getDate(myDateFormat.DATE_FORMAT_DD_MM_AAA,model.getCreatedTime()));
        tvTitle.setMyanmarText(model.getTitle());
        if (model.getViewCount() < 1){
            tvViews.setMyanmarText(model.getViewCount()+" view");
        }else {
            tvViews.setMyanmarText(model.getViewCount()+" views");
        }
        webView.loadDataWithBaseURL(null, model.getBodyHtml(), "text/html", "utf-8", null);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvBody.setText(Html.fromHtml(model.getBodyHtml(), Html.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE));
        }else {
            tvBody.setText(Html.fromHtml(model.getBodyHtml()));
        }*/
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.ll_share:
                openShareOption();
                break;
        }
    }

    private void openShareOption(){
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "http://techtricitymm.com/Article?ID=1019";
        String shareSub = "TechTricity";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share using"));
    }
}
