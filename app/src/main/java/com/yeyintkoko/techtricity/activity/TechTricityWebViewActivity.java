package com.yeyintkoko.techtricity.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.common.BaseActivity;
import com.yeyintkoko.techtricity.custom_control.MyanButton;

import butterknife.BindView;

public class TechTricityWebViewActivity extends BaseActivity {
    private static final String TAG = "TrackingActivity";

    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.pg_bar)
    ProgressBar pg_bar;

    @BindView(R.id.errorImage)
    RelativeLayout errorImage;

    @BindView(R.id.refreshBtn)
    MyanButton refreshBtn;

    private static final int ERR_INTERNET_DISCONNECTED = -2;
    private String mURL;
    private static String IE_URL = "IE_URL";

    public static Intent getTechTricityWebViewIntent(Context context, String url) {
        Intent intent = new Intent(context,TechTricityWebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(IE_URL, url);
        return intent;

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.techtricity_webview;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setupToolbar(true);
        init();
    }

    private void init(){
        mURL = getIntent().getStringExtra(IE_URL);

        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setScrollbarFadingEnabled(true);
        webview.setInitialScale(70);
        webview.loadUrl(mURL);
        webview.setWebViewClient(new myURl());
        webview.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                try {
                    if (progress < 100 && pg_bar.getVisibility() == ProgressBar.GONE) {
                        pg_bar.setVisibility(ProgressBar.VISIBLE);
                    }

                    pg_bar.setProgress(progress);
                    if (progress == 100) {
                        try {
                            pg_bar.setVisibility(ProgressBar.GONE);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(TechTricityWebViewActivity.getTechTricityWebViewIntent(getApplicationContext(),mURL));
            }
        });
    }

    public class myURl extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                Log.e(TAG, "onReceivedError: Code -> " + error.getErrorCode() + ", Description -> " + error.getDescription());
                if (error.getErrorCode() == ERR_INTERNET_DISCONNECTED) {
                    try {
                        errorImage.setVisibility(View.VISIBLE);
                        webview.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e(TAG, "shouldOverrideUrlLoading 1 : " + url );

            if (URLUtil.isNetworkUrl(url)) {
//                view.loadUrl(url);
                return false;
            }

            try {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_VIEW);
                shareIntent.setData(Uri.parse(url));
                startActivity(shareIntent);

            }catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(),"Appropriate application not found",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return true;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();

            Log.e(TAG, "shouldOverrideUrlLoading 2 : " + url);

            if (URLUtil.isNetworkUrl(url)) {
//                view.loadUrl(url);
                return false;
            }

            try {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_VIEW);
                shareIntent.setData(Uri.parse(url));
                startActivity(shareIntent);

            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Appropriate application not found", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return true;
        }
    }
}
