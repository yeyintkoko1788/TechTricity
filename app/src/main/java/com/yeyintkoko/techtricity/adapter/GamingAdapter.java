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
import com.yeyintkoko.techtricity.model.ArticleModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GamingAdapter extends BaseAdapter {
    private boolean isGaming;

    public GamingAdapter(boolean isGaming){
        this.isGaming = isGaming;
    }
    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gaming, parent, false);
        return new GamingAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GamingAdapter.ViewHolder) holder).bindPost((ArticleModel) getItemsList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_news)
        ImageView ivImage;

        @BindView(R.id.tv_title)
        MyanBoldTextView tvTitle;

        @BindView(R.id.cvItem)
        CardView cvItem;

        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bindPost(ArticleModel model) {
            //resize item view of brand list
            Display display = ((Activity) cvItem.getContext()).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            try {
                display.getRealSize(size);
            } catch (NoSuchMethodError err) {
                display.getSize(size);
            }
            int width = size.x;
            int height = size.y;

            if (isGaming){
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (width/1.5), (int) convertDpToPx(context, 220));
                lp.setMargins((int) convertDpToPx(context,8), 0, 0, 0);
                cvItem.setLayoutParams(lp);
            }else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) convertDpToPx(context, 220));
                lp.setMargins((int) convertDpToPx(context,16), (int) convertDpToPx(context,16), (int) convertDpToPx(context,16), 0);
                cvItem.setLayoutParams(lp);
            }

            Glide.with(context)
                    .asBitmap()
                    .load(model.getArticlePhotoUrl())
                    .into(ivImage);
            tvTitle.setMyanmarText(model.getTitle());
            cvItem.setOnClickListener(view -> {
                Intent intent = NewsDetailActivity.getDetailIntent(context,model.getID(),model.getArticlePhotoUrl());
                context.startActivity(intent);
            });
        }

        public float convertDpToPx(Context context, float dp) {
            return dp * context.getResources().getDisplayMetrics().density;
        }
    }
}
