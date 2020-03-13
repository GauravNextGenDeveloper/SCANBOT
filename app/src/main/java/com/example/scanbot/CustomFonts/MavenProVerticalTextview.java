package com.example.scanbot.CustomFonts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

public class MavenProVerticalTextview extends TextView {
    final boolean topDown;

    public MavenProVerticalTextview(Context context, AttributeSet attrs){
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "MavenPro-Regular.ttf");
        setTypeface(tf);
        final int gravity = getGravity();
        if(Gravity.isVertical(gravity) && (gravity& Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
            setGravity((gravity& Gravity.HORIZONTAL_GRAVITY_MASK) | Gravity.TOP);
            topDown = false;
        }else
            topDown = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b){
        return super.setFrame(l, t, l+(b-t), t+(r-l));
    }

    @Override
    public void draw(Canvas canvas){
        if(topDown){
            canvas.translate(getHeight(), 0);
            canvas.rotate(90);
        }else {
            canvas.translate(0, getWidth());
            canvas.rotate(-90);
        }
//        canvas.clipRect(0, 0, getWidth(), getHeight(), android.graphics.Region.Op.REPLACE);

        canvas.save(); // now save again the current state of canvas (clip and matrix) (it's state #2)
        canvas.clipRect(0, 0, getWidth(), getHeight()); // now we can do some other clipping (as we would do with Region.Op.REPLACE before)// and some other drawing
        canvas.restore();
        super.draw(canvas);
    }
}