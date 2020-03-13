package com.example.scanbot.CustomFonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")
public class MavenProButton extends Button {

    public MavenProButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MavenProButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MavenProButton(Context context) {
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
