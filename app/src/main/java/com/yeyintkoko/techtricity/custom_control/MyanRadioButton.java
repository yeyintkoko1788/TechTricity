package com.yeyintkoko.techtricity.custom_control;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.yeyintkoko.techtricity.helper.AppConstant;
import com.yeyintkoko.techtricity.helper.SharePreferenceHelper;


public class MyanRadioButton extends AppCompatRadioButton {

    private Context context;
    private SharePreferenceHelper sharePreferenceHelper;

    public MyanRadioButton(Context context) {
        super(context);
        this.context = context;
        setMyanmarText(getText().toString());
    }

    public MyanRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyanmarText(getText().toString());
    }

    public MyanRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMyanmarText(getText().toString());
    }

    public String getMyanmarText() {
        if (MyanmarZawgyiConverter.isZawgyiEncoded(getText().toString())) {
            return Rabbit.zg2uni(getText().toString());
        } else {
            return getText().toString();
        }
    }

    public void setMyanmarText(String text) {
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), text));
    }

    private void applyCustomFont(Context context) {
        sharePreferenceHelper = new SharePreferenceHelper(getContext());
        Typeface customFont;
        if (sharePreferenceHelper.getLocaleLanguage().equals(AppConstant.myanmarLanguageCode)){
            customFont = FontCache.getTypeface("Padauk_Regular.ttf", context);
        }else {
            customFont = FontCache.getTypeface("Geomanist_Regular.ttf", context);
        }
        setTypeface(customFont);
    }
}
