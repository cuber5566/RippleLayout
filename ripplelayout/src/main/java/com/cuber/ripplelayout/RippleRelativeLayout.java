package com.cuber.ripplelayout;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

public class RippleRelativeLayout extends RelativeLayout {

    private Path path;
    private boolean isBuild = false, isAnimation = false;
    private int duration = 450;
    private TimeInterpolator interpolator = new DecelerateInterpolator();

    public RippleRelativeLayout(Context context) {
        this(context, null);
    }

    public RippleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        path = new Path();
        setWillNotDraw(false);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        canvas.save();
        canvas.clipPath(path);
        super.draw(canvas);
        canvas.restore();
    }

    public RippleRelativeLayout duration(int duration) {
        this.duration = duration;
        return this;
    }

    public RippleRelativeLayout interpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public void startRippleAnimation(final View view, final boolean expand) {

        if (!isAnimation)

            if (!isBuild)
                getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        isBuild = true;
                        getLocationValue(view, expand);
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

            else
                getLocationValue(view, expand);
    }

    private void getLocationValue(View view, boolean expand) {

        int location[] = new int[2];
        view.getLocationOnScreen(location);

        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();

        int centerX = location[0] + view.getWidth() / 2;
        int centerY = location[1] + view.getHeight() / 2;

        int min = viewWidth > viewHeight ? viewWidth : viewHeight;
        int max = (int) Math.sqrt(getWidth() * getWidth() + getHeight() * getHeight());

        startRippleAnimation(centerX, centerY, min, max, expand);
    }

    public void startRippleAnimation(final int centerX, final int centerY, int min, int max, boolean expand) {

        isAnimation = true;

        ValueAnimator valueAnimator;

        if (expand)
            valueAnimator = ValueAnimator.ofInt(min, max);
        else
            valueAnimator = ValueAnimator.ofInt(max, 0);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                path.reset();
                path.addCircle(centerX, centerY, (int) animation.getAnimatedValue(), Path.Direction.CW);
                path.close();
                invalidate();
            }
        });

        valueAnimator.setInterpolator(interpolator);
        valueAnimator.setDuration(duration).start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                isAnimation = false;
            }
        }, duration);
    }
}
