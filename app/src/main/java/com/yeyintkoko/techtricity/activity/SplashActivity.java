package com.yeyintkoko.techtricity.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.common.BaseActivity;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_loading)
    ImageView ivLoading;

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
        startActivity(MainActivity.getMainIntent(this));
        finish();
    }

    private void doSplash() {
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading)
                .into(ivLoading);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                init();
            }

        }, 2500);
    }
}
