package com.yeyintkoko.techtricity.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.common.BaseActivity;
import com.yeyintkoko.techtricity.custom_control.SplashText;

import butterknife.BindView;
import su.levenetc.android.textsurface.TextSurface;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_text)
    TextSurface splashText;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setupToolbar(false);
        setupToolbarText(" ");
        doSplash();
    }

    private void init() {
        if (getIntent().getData() != null){
            if (getIntent().getData().getQueryParameter("ID") != null){
                startActivity(NewsDetailActivity.getDetailIntent(this, Integer.parseInt(getIntent().getData().getQueryParameter("ID")),""));
            }
        }else {
            startActivity(MainActivity.getMainIntent(this));
        }
        finish();
    }

    private void doSplash() {
        splashText.postDelayed(new Runnable() {
            @Override public void run() {
                show();
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                init();
            }

        }, 5500);
    }
    private void show(){
        splashText.reset();
        SplashText.play(splashText, getAssets(), getResources().getColor(R.color.color_green_text), getResources().getColor(R.color.colorWhite));
    }
}
