package com.yeyintkoko.techtricity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.activity.AuthorDetailActivity;
import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.model.AuthorListModel;
import com.yeyintkoko.techtricity.tech_tricity_interface.AuthorListInterface;

import java.util.ArrayList;
import java.util.List;

public class AuthorPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<AuthorListModel> mData;
    private List<CardView> mViews;
    private float mBaseElevation;
   // private AuthorListInterface mInterface;

    public AuthorPagerAdapter(){
        mViews = new ArrayList<>();
        mData = new ArrayList<>();
        //this.mInterface = authorListInterface;
    }

    public void addCardItemList(AuthorListModel item) {
        mViews.add(null);
        mData.add(item);
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public AuthorListModel getItemAt(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_author, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        ImageView imageView = view.findViewById(R.id.iv_author);
        CardView cardView = view.findViewById(R.id.cv_author);
        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        view.setOnClickListener(view1 -> {
            container.getContext().startActivity(AuthorDetailActivity.getAuthorDetailIntent(container.getContext(),mData.get(position).getUserlist().getID()));
        });

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    private void bind(AuthorListModel model, View view) {
        Glide.with(view.findViewById(R.id.iv_author))
                .load(model.getUserlist().getPhotoUrl())
                //.placeholder(R.drawable.cinema_placeholder)
                .into((ImageView) view.findViewById(R.id.iv_author));
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    public void ClearData() {
        mViews.clear();
        mData.clear();
    }
}
