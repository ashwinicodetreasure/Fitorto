package com.ct.fitorto.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.ct.fitorto.R;

/**
 * Created by codetreasure on 17/5/16.
 */
public class ExpandableTextView extends TextView {


    private static final int DEFAULT_TRIM_LENGTH = 100;
    private static final String ELLIPSIS = "<font color=#999999>....Read More</font>";

    private CharSequence originalText;
    private CharSequence trimmedText;
    private BufferType bufferType;
    private boolean trim = true;
    private int trimLength;
    SpannableString ss;


    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView);
        this.trimLength = typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH);
        typedArray.recycle();
        /*ss=  new SpannableString(ELLIPSIS);
        ss.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, 13, 0);*/
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                trim = !trim;
                setText();
                requestFocusFromTouch();
            }
        });
    }

    private void setText() {
        super.setText(getDisplayableText(), bufferType);
    }

    private CharSequence getDisplayableText() {
        return trim ? trimmedText : originalText;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        trimmedText = getTrimmedText(text);
        bufferType = type;
        setText();
    }

    private CharSequence getTrimmedText(CharSequence text) {
        if (originalText != null && originalText.length() > trimLength) {
            return new SpannableStringBuilder(originalText, 0, trimLength + 1).append(Html.fromHtml(ELLIPSIS));
        } else {
            return originalText;
        }
    }

    public CharSequence getOriginalText() {
        return originalText;
    }

    public void setTrimLength(int trimLength) {
        this.trimLength = trimLength;
        trimmedText = getTrimmedText(originalText);
        setText();
    }

    public int getTrimLength() {
        return trimLength;
    }


}
