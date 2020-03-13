package com.example.scanbot.CustomFonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class MavenProTextView extends TextView {

    public MavenProTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MavenProTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MavenProTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "MavenPro-Regular.ttf");
            setTypeface(tf);
        }
    }
}
