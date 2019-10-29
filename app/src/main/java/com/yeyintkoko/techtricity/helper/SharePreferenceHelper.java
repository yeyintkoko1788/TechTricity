package com.yeyintkoko.techtricity.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceHelper {

    private SharedPreferences sharedPreference;

    private static String SHARE_PREFRENCE = "techtricityPref";

    private static String LOCALE_LANGUAGE = "locale_language";

    public SharePreferenceHelper(Context context)
    {
        sharedPreference = context.getSharedPreferences(SHARE_PREFRENCE, Context.MODE_PRIVATE);
    }

    public void logoutSharePreference()
    {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }

    public void setLocaleLanguage(String language){
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(LOCALE_LANGUAGE,language);
        editor.apply();
        editor.commit();
    }

    public String getLocaleLanguage(){
        return sharedPreference.getString(LOCALE_LANGUAGE,"en");
    }
}
