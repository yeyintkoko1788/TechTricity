package com.yeyintkoko.techtricity.custom_control;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ViewSwitcher;

import androidx.annotation.StyleRes;

public class TextViewFactory implements  ViewSwitcher.ViewFactory {

    @StyleRes
    private int styleId;
    private boolean center;
    private String textStyle;
    private String textAlignment;
    private Context context;
    private boolean ellopise;

    public TextViewFactory(Context context, @StyleRes int styleId, boolean center, String textStyle, String textAlignment, boolean ellipise) {
        this.styleId = styleId;
        this.center = center;
        this.textStyle = textStyle;
        this.textAlignment = textAlignment;
        this.context = context;
        this.ellopise = ellipise;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View makeView() {
        if (textStyle.equals("text")){
            final MyanTextView textView = new MyanTextView(context);
            if (center) {
                textView.setGravity(Gravity.CENTER);
            }
            if (ellopise){
                textView.setMaxLines(2);
                textView.setEllipsize(TextUtils.TruncateAt.END);
            }
            if (textAlignment.equals("end")){
                textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
            if (textAlignment.equals("center")){
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(context, styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }else if (textStyle.equals("bold")){
            final MyanBoldTextView textView = new MyanBoldTextView(context);
            if (center) {
                textView.setGravity(Gravity.CENTER);
            }
            if (ellopise){
                textView.setMaxLines(2);
                textView.setEllipsize(TextUtils.TruncateAt.END);
            }

            if (textAlignment.equals("end")){
                textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
            if (textAlignment.equals("center")){
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(context, styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }
        return null;
    }

}