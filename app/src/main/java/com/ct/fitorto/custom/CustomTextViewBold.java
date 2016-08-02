package com.ct.fitorto.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by arvindshukla on 28/07/16.
 */
public class CustomTextViewBold extends TextView {

        public CustomTextViewBold(Context context) {
        super(context);
        style(context);
    }

        public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context);
    }

        public CustomTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context);
    }

    /*
    android:fontFamily="sans-serif"           // roboto regular
android:fontFamily="sans-serif-light"     // roboto light
android:fontFamily="sans-serif-condensed" // roboto condensed
android:fontFamily="sans-serif-thin"      // roboto thin (android 4.2)
android:fontFamily="sans-serif-medium"
     */
    private void style(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "Roboto-Black.ttf");
        setTypeface(tf);

    }

}
