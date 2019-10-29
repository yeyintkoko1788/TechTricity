package com.yeyintkoko.techtricity.root;

import android.app.Application;

import com.yeyintkoko.techtricity.custom_control.AndroidCommonSetup;

public class TechTricityApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidCommonSetup.getInstance().init(getApplicationContext());
    }
}
