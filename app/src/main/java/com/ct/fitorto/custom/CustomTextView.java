package com.ct.fitorto.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by codetreasure on 6/15/16.
 */
public class CustomTextView extends TextView {

    public CustomTextView(Context context) {
        super(context);
        style(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
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
                "Roboto-Regular.ttf");
        setTypeface(tf);

    }

}
