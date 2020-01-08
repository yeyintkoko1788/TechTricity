package com.yeyintkoko.techtricity.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.request.transition.TransitionFactory;
import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.custom_control.TextViewFactory;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class AboutUsFragment extends Fragment {

    @BindView(R.id.nestedScroll)
    NestedScrollView nestedScrollView;

    @BindView(R.id.iv_provider)
    ImageView ivProvider;

    @BindView(R.id.iv_content_promotion)
    ImageView ivContentPromotion;

    @BindView(R.id.iv_content_creation)
    ImageView ivContentCreation;

    @BindView(R.id.iv_video_presentation)
    ImageView ivVideoPresentation;

    @BindView(R.id.iv_innovative_ads)
    ImageView ivInnovationAds;

    @BindView(R.id.cv_chat_bot)
    CardView cvChatBot;

    @BindView(R.id.ll_chat_bot)
    LinearLayout llChatBot;

    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, rootView);
        context = rootView.getContext();
        init();
        return rootView;
    }

    private void init(){
        //resize item view of cinema list
        Display display = ((Activity) cvChatBot.getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        int width = size.x;
        int height = size.y;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) (width/2.2),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.setMargins((int) convertDpToPx(context,8), (int) convertDpToPx(context,16), (int) convertDpToPx(context,8), (int) convertDpToPx(context,16));
        cvChatBot.setLayoutParams(lp);
    }

    public float convertDpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
