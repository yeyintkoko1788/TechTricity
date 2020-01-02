package com.yeyintkoko.techtricity.custom_control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.yeyintkoko.techtricity.helper.AppConstant;
import com.yeyintkoko.techtricity.helper.SharePreferenceHelper;


public class MyanTitleTextView extends AppCompatTextView {


    private Context context;
    private SharePreferenceHelper sharePreferenceHelper;

    public MyanTitleTextView(Context context) {
        super(context);
        this.context = context;
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public MyanTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public MyanTitleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
        setText(MyanTextProcessor.processText(getContext(), getText().toString()));
    }

    public void setMyanmarText(final String text) {
        applyCustomFont(context);

        this.post(new Runnable() {
            @Override
            public void run() {

                if(text != null)
                {
                    if(!text.isEmpty())
                    {
                        String myanText = MyanTextProcessor.processText(getContext(), text);
                        setText(myanText);
                    }
                    else
                    {
                        setText("-");
                    }
                }
                else
                {
                    setText("-");
                }

            }

        });

    }

    public void setMyanmarToastText(String text){
        String myanText = MyanTextProcessor.processText(getContext(), text);
        setText(myanText);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void applyCustomFont(Context context) {
        sharePreferenceHelper = new SharePreferenceHelper(getContext());
        Typeface customFont;
        if (sharePreferenceHelper.getLocaleLanguage().equals(AppConstant.myanmarLanguageCode)){
            customFont = FontCache.getTypeface("Padauk_Book.ttf", context);
        }else {
            customFont = FontCache.getTypeface("effloresce_it.ttf", context);
        }
        setTypeface(customFont);
    }
}
