package com.yeyintkoko.techtricity.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yeyintkoko.techtricity.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactUsFragment extends Fragment implements View.OnClickListener {

    private Context context;

    @BindView(R.id.iv_google_plus)
    ImageView ivGooglePlus;

    @BindView(R.id.iv_facebook)
    ImageView ivFacebook;

    @BindView(R.id.iv_instagram)
    ImageView ivInstagram;

    @BindView(R.id.iv_youtube)
    ImageView ivYoutube;

    @BindView(R.id.iv_twitter)
    ImageView ivTwitter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, rootView);

        init();
        context = rootView.getContext();
        return rootView;
    }

    private void init(){

        ivFacebook.setOnClickListener(this);
        ivGooglePlus.setOnClickListener(this);
        ivInstagram.setOnClickListener(this);
        ivYoutube.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.iv_google_plus:
                flipAnimation(ivGooglePlus);
                break;
            case R.id.iv_facebook:
                flipAnimation(ivFacebook);
                break;
            case R.id.iv_instagram:
                flipAnimation(ivInstagram);
                break;
            case R.id.iv_youtube:
                flipAnimation(ivYoutube);
                break;
            case R.id.iv_twitter:
                flipAnimation(ivTwitter);
                break;
        }
    }

    public void flipAnimation(final ImageView view){
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                oa2.start();
            }
        });
        oa1.start();
    }
}
