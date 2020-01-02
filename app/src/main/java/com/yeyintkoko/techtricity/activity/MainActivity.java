package com.yeyintkoko.techtricity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

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
    private SearchView searchView;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        //return super.onCreateOptionsMenu(menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            //noinspection RestrictedApi
            m.setOptionalIconsVisible(true);
        }
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //mAdapter.getFilter().filter(query);
                getSearchList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                //mAdapter.getFilter().filter(query);
//                name = query;
//                getBookList();
                return false;
            }
        });

        return true;
    }

    private void getSearchList(String title){
        startActivity(SearchResultActivity.getSearchDetailIntent(MainActivity.this, title));
    }

    private void init(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void displayView(String title, String menu) {
        if (CURRENT_TAG.equals(menu)) {
            return;
        }

        Fragment fragment = null;
        Fragment previousFragment = null;

        switch (menu) {
            case  FRAGMENT_NEWS:
                //previousFragment = getCurrentTagFragment();
                CURRENT_TAG = FRAGMENT_NEWS;
                fragment = new NewsFeedFragment();
                break;
            case FRAGMENT_HOME:
                //previousFragment = getCurrentTagFragment();
                CURRENT_TAG = FRAGMENT_HOME;
                fragment = new HomeFragment();
                break;
            case FRAGMENT_AUTHOR:
                //previousFragment = getCurrentTagFragment();
                CURRENT_TAG = FRAGMENT_AUTHOR;
                fragment = new AuthorFragment();
                break;
            case FRAGMENT_ABOUT_US:
                //previousFragment = getCurrentTagFragment();
                CURRENT_TAG = FRAGMENT_ABOUT_US;
                fragment = new AboutUsFragment();
                break;
            case FRAGMENT_CONTACT_US:
                //previousFragment = getCurrentTagFragment();
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
           /* if (previousFragment != null){
                fragmentTransaction.hide(previousFragment).show(fragment).commitAllowingStateLoss();
            }else {
                fragmentTransaction.show(fragment).commitAllowingStateLoss();
            }*/
           fragmentTransaction.replace(R.id.fragment_container,fragment).commitAllowingStateLoss();
        }
    }
    private Fragment getCurrentTagFragment(){
        if (!CURRENT_TAG.equals("")){
            Fragment fragment = null;
            switch (CURRENT_TAG){
                case  FRAGMENT_NEWS:
                    fragment = new NewsFeedFragment();
                    break;
                case FRAGMENT_HOME:
                    fragment = new HomeFragment();
                    break;
                case FRAGMENT_AUTHOR:
                    fragment = new AuthorFragment();
                    break;
                case FRAGMENT_ABOUT_US:
                    fragment = new AboutUsFragment();
                    break;
                case FRAGMENT_CONTACT_US:
                    fragment = new ContactUsFragment();
                    break;
            }
            return fragment;
        }
        return null;
    }
}
