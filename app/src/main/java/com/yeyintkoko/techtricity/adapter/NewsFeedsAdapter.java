package com.yeyintkoko.techtricity.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.activity.MainActivity;
import com.yeyintkoko.techtricity.activity.NewsDetailActivity;
import com.yeyintkoko.techtricity.common.BaseAdapter;
import com.yeyintkoko.techtricity.custom_control.MyDateFormat;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanTextView;
import com.yeyintkoko.techtricity.fragment.NewsFeedFragment;
import com.yeyintkoko.techtricity.model.ArticleModel;
import com.yeyintkoko.techtricity.tech_tricity_interface.NewsFeedInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFeedsAdapter extends BaseAdapter {

    private NewsFeedInterface listener;
    private int lastPosition = -1;
    private Context context;

    public NewsFeedsAdapter(NewsFeedInterface listener, Context context){
        this.listener = listener;
        this.context = context;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_new_feeds, parent, false);
        return new NewsFeedsAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindCustomViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
        ((NewsFeedsAdapter.ViewHolder) holder).bindPost((ArticleModel) getItemsList().get(position),position);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateCustomHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindCustomHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private MyDateFormat myDateFormat;

        @BindView(R.id.ivImage)
        ImageView ivImage;

        @BindView(R.id.tv_date)
        MyanTextView tvDate;

        @BindView(R.id.tv_author)
        MyanTextView tvAuthor;

        @BindView(R.id.tv_title)
        MyanBoldTextView tvTitle;

        @BindView(R.id.tv_sub_title)
        MyanTextView tvSubTitle;

        @BindView(R.id.item_news)
        LinearLayout itemNews;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            myDateFormat = new MyDateFormat();
            ButterKnife.bind(this, itemView);
        }

        public void bindPost(final ArticleModel articleModel, int position) {
            tvAuthor.setMyanmarText(articleModel.getUserName());
            tvDate.setMyanmarText(myDateFormat.getDate(myDateFormat.DATE_FORMAT_DD_MM_AAA,articleModel.getCreatedTime()));
            tvTitle.setMyanmarText(articleModel.getTitle());
            tvSubTitle.setMyanmarText(articleModel.getDescription());
            Glide.with(context)
                    .asBitmap()
                    .load(articleModel.getArticlePhotoUrl())
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            onPalette(Palette.from(resource).generate());
                            ivImage.setImageBitmap(resource);
                            return false;
                        }
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        public void onPalette(Palette palette) {
                            if (null != palette) {
                                //ViewGroup parent = (ViewGroup) imageView.getParent().getParent();
                                int color1 = palette.getDominantColor(Color.GRAY);
                                int transparentColor = adjustAlpha(color1, 0.5f);
                                int[] colors = {context.getResources().getColor(R.color.colorWhite),context.getResources().getColor(R.color.colorTransparent),
                                        context.getResources().getColor(R.color.colorTransparent)};
                                //create a new gradient color
                                GradientDrawable gd = new GradientDrawable(
                                        GradientDrawable.Orientation.BOTTOM_TOP, colors);
                                gd.setCornerRadius(0f);

                                ivImage.setForeground(gd);
                                //itemNews.setForeground(new ColorDrawable(transparentColor));
                            }
                        }
                    })
                    .into(ivImage);
            //setAnimation(itemView, position);

            itemNews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = NewsDetailActivity.getDetailIntent(context,articleModel.getID(),articleModel.getArticlePhotoUrl());
                    listener.performTransation(intent,ivImage);
                }
            });
        }
       /* private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }*/

        @ColorInt
        public int adjustAlpha(@ColorInt int color, float factor) {
            int alpha = Math.round(Color.alpha(color) * factor);
            int red = Color.red(color);
            int green = Color.green(color);
            int blue = Color.blue(color);
            return Color.argb(alpha, red, green, blue);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}
