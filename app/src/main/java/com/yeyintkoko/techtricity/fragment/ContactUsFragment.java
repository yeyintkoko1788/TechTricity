package com.yeyintkoko.techtricity.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yeyintkoko.techtricity.R;
import com.yeyintkoko.techtricity.custom_control.MyanBoldTextView;
import com.yeyintkoko.techtricity.custom_control.MyanButton;
import com.yeyintkoko.techtricity.custom_control.MyanEditText;
import com.yeyintkoko.techtricity.custom_control.MyanProgressDialog;
import com.yeyintkoko.techtricity.helper.ServiceHelper;
import com.yeyintkoko.techtricity.model.MessageModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @BindView(R.id.tv_phone1)
    MyanBoldTextView tvPhone1;

    @BindView(R.id.tv_phone2)
    MyanBoldTextView tvPhone2;

    @BindView(R.id.tv_gmail)
    MyanBoldTextView tvGmail;

    @BindView(R.id.et_name)
    MyanEditText etName;

    @BindView(R.id.et_phone)
    MyanEditText etPhone;

    @BindView(R.id.et_email)
    MyanEditText etEmail;

    @BindView(R.id.et_subject)
    MyanEditText etSubject;

    @BindView(R.id.et_message)
    MyanEditText etMessage;

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private ServiceHelper.ApiService service;
    private Call<MessageModel> sendMessage;

    MyanProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, rootView);
        context = rootView.getContext();
        init();

        return rootView;
    }

    private void init(){
        service = ServiceHelper.getClient(context);
        progressDialog = new MyanProgressDialog(context);

        ivFacebook.setOnClickListener(this);
        ivGooglePlus.setOnClickListener(this);
        ivInstagram.setOnClickListener(this);
        ivYoutube.setOnClickListener(this);
        ivTwitter.setOnClickListener(this);
        tvPhone1.setOnClickListener(this);
        tvPhone2.setOnClickListener(this);
        tvGmail.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.iv_google_plus:
                flipAnimation(ivGooglePlus,"https://aboutme.google.com/u/0/?referer=gplus");
                break;
            case R.id.iv_facebook:
                //flipAnimation(ivFacebook,"https://www.facebook.com/techtricitymm");
                getOpenFacebookIntent(ivFacebook,"2127267900934650");
                break;
            case R.id.iv_instagram:
                flipAnimation(ivInstagram, "https://www.instagram.com/?hl=en");
                break;
            case R.id.iv_youtube:
                flipAnimation(ivYoutube, "https://www.youtube.com");
                break;
            case R.id.iv_twitter:
                flipAnimation(ivTwitter, "https://twitter.com/home");
                break;
            case R.id.tv_phone1:
                phoneCall(tvPhone1.getText().toString());
                break;
            case R.id.tv_phone2:
                phoneCall(tvPhone2.getText().toString());
                break;
            case R.id.tv_gmail:
                sendEmail();
                break;
            case R.id.btn_submit:
                sendMessage();
        }
    }

    private void sendMessage() {
        if (etName.getMyanmarText().isEmpty()){
            etName.setError(getResources().getString(R.string.error_msg));
            return;
        }else if (etPhone.getMyanmarText().isEmpty()){
            etPhone.setError(getResources().getString(R.string.error_msg));
            return;
        }else if (etEmail.getMyanmarText().isEmpty()){
            etEmail.setError(getResources().getString(R.string.error_msg));
            return;
        }else if (etSubject.getMyanmarText().isEmpty()){
            etSubject.setError(getResources().getString(R.string.error_msg));
            return;
        }else if (etMessage.getMyanmarText().isEmpty()){
            etMessage.setError(getResources().getString(R.string.error_msg));
            return;
        }
        progressDialog.showDialog();
        MessageModel messageModel = new MessageModel();
        messageModel.setID(0);
        messageModel.setSenderName(etName.getMyanmarText());
        messageModel.setSenderPhone(etPhone.getMyanmarText());
        messageModel.setSenderEmail(etEmail.getMyanmarText());
        messageModel.setSubject(etSubject.getMyanmarText());
        messageModel.setBodyMessage(etMessage.getMyanmarText());

        sendMessage = service.sendMessage(messageModel);
        sendMessage.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                progressDialog.hideDialog();
                if (response.isSuccessful()){
                    Toast.makeText(context,"Message Send",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                progressDialog.hideDialog();
                Toast.makeText(context,"Error sending message!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void flipAnimation(final ImageView view, String link){
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
        oa2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });
    }

    public void getOpenFacebookIntent(final ImageView view, String uri) {
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
        oa2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                try {
                    context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + uri));
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"+ uri));
                    startActivity(intent);
                }
            }
        });

    }

    public void phoneCall(String phoneNo){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+phoneNo));
        startActivity(callIntent);
    }
    public void sendEmail(){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","admin@techtricitymm.com", null));
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }
}
