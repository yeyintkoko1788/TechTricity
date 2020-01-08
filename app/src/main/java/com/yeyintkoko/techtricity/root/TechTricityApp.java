package com.yeyintkoko.techtricity.root;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yeyintkoko.techtricity.custom_control.AndroidCommonSetup;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class TechTricityApp extends Application {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        AndroidCommonSetup.getInstance().init(getApplicationContext());
    }
}
