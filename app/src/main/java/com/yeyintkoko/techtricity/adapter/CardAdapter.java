package com.yeyintkoko.techtricity.adapter;

import androidx.cardview.widget.CardView;

import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.model.AuthorListModel;


public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 3;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    AuthorListModel getItemAt(int position);

    int getCount();
}
