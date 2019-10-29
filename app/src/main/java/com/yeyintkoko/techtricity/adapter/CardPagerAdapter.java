package com.yeyintkoko.techtricity.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanTextView;
import com.yeyintkoko.techtricity.model.ArticleModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Yint Ko Ko
 **/

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private final String mtag = this.getClass().getSimpleName();
    private List<CardView> mViews;
    private List<ArticleModel> mData;
    private float mBaseElevation;
    private String filter;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        filter = null;
    }

    public void addCardItemList(ArticleModel item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public ArticleModel getItemAt(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_banner_2, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performClick(position,view);
            }
        });

        CardView cardView = view.findViewById(R.id.cvItem);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(ArticleModel item, View view) {

        Glide.with(view.findViewById(R.id.ivImage))
                .load(item.getArticlePhotoUrl())
                .into((ImageView) view.findViewById(R.id.ivImage));

        MyanBoldTextView tvTitle = view.findViewById(R.id.tv_banner_title);
        tvTitle.setMyanmarText(item.getTitle());
        MyanTextView tvAuthor = view.findViewById(R.id.tv_banner_author);
        tvAuthor.setMyanmarText(item.getUserName());
    }

    public void ClearData() {
        mViews.clear();
        mData.clear();
    }

    private void performClick(int position, View view){
        ArticleModel model = mData.get(position);

    }
}
