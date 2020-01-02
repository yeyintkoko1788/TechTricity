package com.yeyintkoko.techtricity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.activity.NewsDetailActivity;
import com.yeyintkoko.techtricity.common.BaseAdapter;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanTextView;
import com.yeyintkoko.techtricity.model.ArticleModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RumorsAdapter extends BaseAdapter {
    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rumors, parent, false);
        return new RumorsAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RumorsAdapter.ViewHolder) holder).bindPost((ArticleModel) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;

        @BindView(R.id.cvItem)
        CardView cvItem;

        @BindView(R.id.tv_banner_author)
        MyanTextView tvBannerAuthor;

        @BindView(R.id.tv_banner_title)
        MyanBoldTextView tvBannerTitle;

        @BindView(R.id.llTitle)
        LinearLayout llTitel;

        private Context context;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            ButterKnife.bind(this,view);
        }

        public void bindPost(ArticleModel articleModel) {
            //resize item view of cinema list
            Display display = ((Activity) cvItem.getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            try {
                display.getRealSize(size);
            } catch (NoSuchMethodError err) {
                display.getSize(size);
            }
            int width = size.x;
            int height = size.y;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (width/1.2),
                    height/5);
            lp.setMargins((int) convertDpToPx(context,8), 0, 0, 0);

            cvItem.setLayoutParams(lp);

            Glide.with(context)
                    .asBitmap()
                    .load(articleModel.getArticlePhotoUrl())
                    .into(ivImage);
            tvBannerAuthor.setMyanmarText(articleModel.getUserName());
            tvBannerTitle.setMyanmarText(articleModel.getTitle());
            cvItem.setOnClickListener(view -> {
                Intent intent = NewsDetailActivity.getDetailIntent(context,articleModel.getID(),articleModel.getArticlePhotoUrl());
                context.startActivity(intent);
            });
        }
    }
    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
