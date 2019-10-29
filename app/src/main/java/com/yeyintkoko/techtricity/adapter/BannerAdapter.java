package com.yeyintkoko.techtricity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.common.BaseAdapter;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanTextView;
import com.yeyintkoko.techtricity.model.ArticleModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerAdapter extends BaseAdapter {
    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_banner, parent, false);
        return new BannerAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BannerAdapter.ViewHolder) holder).bindPost((ArticleModel) getItemsList().get(position));
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

        @BindView(R.id.tv_banner_author)
        MyanTextView tvBannerAuthor;

        @BindView(R.id.tv_banner_title)
        MyanBoldTextView tvBannerTitle;

        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this,itemView);
        }

        public void bindPost(ArticleModel articleModel) {
            Glide.with(context)
                    .asBitmap()
                    .load(articleModel.getArticlePhotoUrl())
                    .into(ivImage);
            tvBannerAuthor.setMyanmarText(articleModel.getUserName());
            tvBannerTitle.setMyanmarText(articleModel.getTitle());
        }
    }
}
