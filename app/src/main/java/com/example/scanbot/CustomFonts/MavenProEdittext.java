package com.example.scanbot.CustomFonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;

@SuppressLint("AppCompatCustomView")
public class MavenProEdittext  extends TextInputEditText {

    public MavenProEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MavenProEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MavenProEdittext(Context context) {
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
