package com.yeyintkoko.techtricity.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        /*final Animation anim = AnimationUtils.loadAnimation(context,R.anim.about_us_ani);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY == ( (v.getMeasuredHeight() - v.getChildAt(0).getMeasuredHeight()) )) {
                    ivContentCreation.clearAnimation();
                    ivContentCreation.startAnimation(anim);
                    ivContentPromotion.clearAnimation();
                    ivContentPromotion.startAnimation(anim);
                    ivVideoPresentation.clearAnimation();
                    ivVideoPresentation.startAnimation(anim);
                    ivInnovationAds.clearAnimation();
                    ivInnovationAds.startAnimation(anim);
                }
            }
        });
        ivProvider.clearAnimation();
        ivProvider.startAnimation(anim);*/
    }
}
