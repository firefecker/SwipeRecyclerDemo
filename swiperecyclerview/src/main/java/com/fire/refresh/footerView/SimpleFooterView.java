package com.fire.refresh.footerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fire.android.swiperecycler.R;


/**
 * @author deadline
 * @time 2016/10/22
 */
public class SimpleFooterView extends BaseFooterView{

    private TextView mText;

    private ProgressBar progressBar;
    private CircleImageView mCircleViewBottom;
    private MaterialProgressDrawable mProgressBottom;

    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private FrameLayout layoutCircle;

    private ValueAnimator valueAnimator;
    boolean start = false;
    boolean visable = false;

    private int[] colors = {
            0xFFFF0000,0xFFFF7F00,0xFFFFFF00,0xFF00FF00
            ,0xFF00FFFF,0xFF0000FF,0xFF8B00FF};


    public SimpleFooterView(Context context) {
        this(context, null);
    }

    public SimpleFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer_view, this);
        progressBar = (ProgressBar) view.findViewById(R.id.footer_view_progressbar);
        mText = (TextView) view.findViewById(R.id.footer_view_tv);
        layoutCircle = (FrameLayout) view.findViewById(R.id.layout_circle);
        mCircleViewBottom = new CircleImageView(getContext(), CIRCLE_BG_LIGHT);
        mProgressBottom = new MaterialProgressDrawable(getContext(),mCircleViewBottom);

        mProgressBottom.setBackgroundColor(CIRCLE_BG_LIGHT);
        //圈圈颜色,可以是多种颜色
        mProgressBottom.setColorSchemeColors(colors);
        //设置圈圈的各种大小
        mProgressBottom.updateSizes(MaterialProgressDrawable.LARGE);

        mCircleViewBottom.setImageDrawable(mProgressBottom);
        layoutCircle.addView(mCircleViewBottom);
    }

    @Override
    public void setColorSchemeColors(int ... colors) {
        if (mProgressBottom == null) {
            return;
        }
        mProgressBottom.setColorSchemeColors(colors);
    }



    @Override
    public void onLoadingMore() {
        mCircleViewBottom.setVisibility(View.VISIBLE);
        visable();
        progressBar.setVisibility(GONE);
        mText.setVisibility(GONE);
    }

    public void showText(){
        mCircleViewBottom.setVisibility(View.GONE);
        stop();
        progressBar.setVisibility(GONE);
        mText.setVisibility(VISIBLE);
        mCircleViewBottom.setVisibility(View.GONE);

    }

    @Override
    public void visable() {
        if(valueAnimator == null) {
            valueAnimator = valueAnimator.ofFloat(0f,1f);
            valueAnimator.setDuration(600);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float n = (float) animation.getAnimatedValue();
                    //圈圈的旋转角度
                    mProgressBottom.setProgressRotation(n * 0.5f);
                    //圈圈周长，0f-1F
                    mProgressBottom.setStartEndTrim(0f, n * 0.8f);
                    //箭头大小，0f-1F
                    mProgressBottom.setArrowScale(n);
                    //透明度，0-255
                    mProgressBottom.setAlpha((int) (255 * n));
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    visable = true;
                    start();
                }
            });
        }
        if(!valueAnimator.isRunning()) {
            if(!visable) {
                //是否显示箭头
                mProgressBottom.showArrow(true);
                valueAnimator.start();
            }
        }
    }

    @Override
    public void start() {
        if(visable) {
            if (!start) {
                mProgressBottom.start();
                start = true;
            }
        }
    }

    @Override
    public void stop() {
        if (start) {
            mProgressBottom.stop();
            start = false;
            visable = false;
        }
    }

    /**************文字自行修改或根据传入的参数动态修改****************/

    @Override
    public void onNoMore(CharSequence message) {
        showText();
        mText.setText("-- the end --");
    }

    @Override
    public void onError(CharSequence message) {
        showText();
        mText.setText("啊哦，好像哪里不对劲!");
    }

    @Override
    public void onNetChange(boolean isAvailable) {
        showText();
        mText.setText("网络连接不通畅!");
    }
}
