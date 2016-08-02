package com.ct.fitorto.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by codetreasure on 7/30/16.
 */
public class LogoTextView extends TextView {

    public LogoTextView(Context context) {
        super(context);
        style(context);
    }

    public LogoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context);
    }

    public LogoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context);
    }


    private void style(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "Helvetica-Condensed.otf");
        setTypeface(tf);

    }

}
