package com.fire.refresh.footerView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @auther deadline
 * @time   2016/10/22
 */
public abstract class BaseFooterView extends FrameLayout implements FooterViewListener{

    public ValueAnimator valueAnimator;
    public boolean start = false;
    public boolean visable = false;


    public BaseFooterView(Context context) {
        super(context);
    }

    public BaseFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void start();

    public abstract void visable();

    public abstract void stop();
    public abstract void setColorSchemeColors(int ... colors);
}
