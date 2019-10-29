package com.yeyintkoko.techtricity.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.common.BaseActivity;
import com.yeyintkoko.techtricity.fragment.AboutUsFragment;
import com.yeyintkoko.techtricity.fragment.AuthorFragment;
import com.yeyintkoko.techtricity.fragment.ContactUsFragment;
import com.yeyintkoko.techtricity.fragment.HomeFragment;
import com.yeyintkoko.techtricity.fragment.NewsFeedFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private String CURRENT_TAG = "";
    public static final String FRAGMENT_NEWS = "new";
    public static final String FRAGMENT_HOME = "home";
    public static final String FRAGMENT_AUTHOR = "author";
    public static final String FRAGMENT_ABOUT_US = "about_us";
    public static final String FRAGMENT_CONTACT_US = "contact_us";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_news:
                    displayView("News Feed", FRAGMENT_NEWS);
                    return true;
                case R.id.navigation_home:
                    displayView("Home", FRAGMENT_HOME);
                    return true;
                case R.id.navigation_author:
                    displayView("Author", FRAGMENT_AUTHOR);
                    return true;
                case R.id.navigation_about_us:
                    displayView("About Us", FRAGMENT_ABOUT_US);
                    return true;
                case R.id.navigation_contact_us:
                    displayView("Contact US", FRAGMENT_CONTACT_US);
                    return true;
            }
            return false;
        }
    };

    public static Intent getMainIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        setupToolbar(false);
        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView("News Feed", FRAGMENT_NEWS);
        }
        init();
    }

    private void init(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void displayView(String title, String menu) {
        if (CURRENT_TAG.equals(menu)) {
            return;
        }

        Fragment fragment = null;

        switch (menu) {
            case  FRAGMENT_NEWS:
                CURRENT_TAG = FRAGMENT_NEWS;
                fragment = new NewsFeedFragment();
                break;
            case FRAGMENT_HOME:
                CURRENT_TAG = FRAGMENT_HOME;
                fragment = new HomeFragment();
                break;
            case FRAGMENT_AUTHOR:
                CURRENT_TAG = FRAGMENT_AUTHOR;
                fragment = new AuthorFragment();
                break;
            case FRAGMENT_ABOUT_US:
                CURRENT_TAG = FRAGMENT_ABOUT_US;
                fragment = new AboutUsFragment();
                break;
            case FRAGMENT_CONTACT_US:
                CURRENT_TAG = FRAGMENT_CONTACT_US;
                fragment = new ContactUsFragment();
                break;
                default:
                    break;
        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment).commitAllowingStateLoss();
        }
    }
}
