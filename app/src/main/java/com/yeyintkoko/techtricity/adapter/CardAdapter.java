package com.yeyintkoko.techtricity.adapter;

import androidx.cardview.widget.CardView;

import com.yeyintkoko.techtricity.model.ArticleModel;


public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 3;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    ArticleModel getItemAt(int position);

    int getCount();
}
