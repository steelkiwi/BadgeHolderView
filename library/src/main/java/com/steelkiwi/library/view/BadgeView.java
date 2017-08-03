package com.steelkiwi.library.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.steelkiwi.library.drawable.CountDrawable;
import com.steelkiwi.library.util.Constants;

/**
 * Created by yaroslav on 8/2/17.
 */

public class BadgeView extends View {

    // paint instance for drawing badge
    private Paint badgePaint;
    // count drawable
    private CountDrawable countDrawable;
    // count value for current count and next count
    private int currentBadgeCount = 0;
    private int nextBadgeCount = 1;
    // flag for checking if need update next count
    private boolean isUpdateWhenDecrement = false;
    private boolean isUpdateWhenIncrement = false;
    // flag for checking if animation is finished
    private boolean isAnimationFinish = true;
    // badge corner radius
    private int cornerRadius;

    public BadgeView(Context context) {
        super(context);
        init();
    }

    public BadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initBadgePaint();
        initCountDrawable();
    }

    private void initBadgePaint() {
        badgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        badgePaint.setStyle(Paint.Style.FILL);
    }

    private void initCountDrawable() {
        countDrawable = new CountDrawable();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = reconcileSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec);
        int height = reconcileSize(MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBadgeBackground(canvas);
        countDrawable.draw(canvas);
    }

    private void drawBadgeBackground(final Canvas canvas) {
        canvas.drawRoundRect(0f, 0f, getWidth(), getHeight(), getCornerRadius(), getCornerRadius(), badgePaint);
    }

    private int reconcileSize(int contentSize, int measureSpec) {
        final int mode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);
        switch(mode) {
            case MeasureSpec.EXACTLY:
                return specSize;
            case MeasureSpec.AT_MOST:
                if (contentSize < specSize) {
                    return contentSize;
                } else {
                    return specSize;
                }
            case MeasureSpec.UNSPECIFIED:
            default:
                return contentSize;
        }
    }

    public void increment() {
        if(isAnimationFinish()) {
            setAnimationFinish(false);
            // check where is need draw next count text
            countDrawable.setIncrement(true);
            // update next count when count is will be increment
            onNextCountUpdateWhenIncrement();
            // animate drawable
            scrollDrawableAnimation(0, getHeight(), Constants.DURATION_500)
                    .addListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            currentBadgeCount++;
                            setNextBadgeCount(getCurrentBadgeCount() + 1);
                            countDrawable.setBadgeCount(getCurrentBadgeCount(), getNextBadgeCount());
                            countDrawable.setTranslationY(0);
                            setUpdateWhenDecrement(true);
                            setAnimationFinish(true);
                            invalidate();
                        }
                    });
        }
    }

    public void decrement() {
        if(isAnimationFinish() && getCurrentBadgeCount() > 0) {
            setAnimationFinish(false);
            // check where is need draw next count
            countDrawable.setIncrement(false);
            // update next count when count is will be increment
            onNextCountUpdateWhenDecrement();
            // animate drawable
            scrollDrawableAnimation(0, -getHeight(), Constants.DURATION_500)
                    .addListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if(getCurrentBadgeCount() > 0) {
                                currentBadgeCount--;
                                setNextBadgeCount(getCurrentBadgeCount() - 1);
                            } else {
                                setCurrentBadgeCount(0);
                                setNextBadgeCount(getCurrentBadgeCount() + 1);
                            }
                            countDrawable.setBadgeCount(getCurrentBadgeCount(), getNextBadgeCount());
                            countDrawable.setTranslationY(0);
                            setUpdateWhenIncrement(true);
                            setAnimationFinish(true);
                            invalidate();

                        }
                    });
        }
    }

    public void setBadgeBackground(int color) {
        badgePaint.setColor(color);
        invalidate();
    }

    public void setCount(int count) {
        setCurrentBadgeCount(count);
        setNextBadgeCount(getCurrentBadgeCount() + 1);
        countDrawable.setBadgeCount(getCurrentBadgeCount(), getNextBadgeCount());
        invalidate();
    }

    public void reset() {
        setCurrentBadgeCount(0);
        setNextBadgeCount(getCurrentBadgeCount() + 1);
        countDrawable.setBadgeCount(getCurrentBadgeCount(), getNextBadgeCount());
        invalidate();
    }

    public int getCount() {
        return getCurrentBadgeCount();
    }

    private ValueAnimator scrollDrawableAnimation(int start, int end, long duration) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int translationY = (int) valueAnimator.getAnimatedValue();
                countDrawable.setTranslationY(translationY);
                postInvalidate();
            }
        });
        animator.setDuration(duration);
        animator.start();
        return animator;
    }

    private void onNextCountUpdateWhenIncrement() {
        if (isUpdateWhenIncrement()) {
            setNextBadgeCount(getCurrentBadgeCount() + 1);
            countDrawable.setBadgeCount(getCurrentBadgeCount(), getNextBadgeCount());
            invalidate();
            setUpdateWhenIncrement(false);
        }
    }

    private void onNextCountUpdateWhenDecrement() {
        if (isUpdateWhenDecrement()) {
            setNextBadgeCount(getCurrentBadgeCount() - 1);
            countDrawable.setBadgeCount(getCurrentBadgeCount(), getNextBadgeCount());
            invalidate();
            setUpdateWhenDecrement(false);
        }
    }

    private int getCurrentBadgeCount() {
        return currentBadgeCount;
    }

    private void setCurrentBadgeCount(int currentBadgeCount) {
        this.currentBadgeCount = currentBadgeCount;
    }

    private int getNextBadgeCount() {
        return nextBadgeCount;
    }

    private void setNextBadgeCount(int nextBadgeCount) {
        this.nextBadgeCount = nextBadgeCount;
    }

    private boolean isUpdateWhenDecrement() {
        return isUpdateWhenDecrement;
    }

    private void setUpdateWhenDecrement(boolean updateWhenDecrement) {
        isUpdateWhenDecrement = updateWhenDecrement;
    }

    private boolean isUpdateWhenIncrement() {
        return isUpdateWhenIncrement;
    }

    private void setUpdateWhenIncrement(boolean updateWhenIncrement) {
        isUpdateWhenIncrement = updateWhenIncrement;
    }

    private boolean isAnimationFinish() {
        return isAnimationFinish;
    }

    private void setAnimationFinish(boolean animationFinish) {
        isAnimationFinish = animationFinish;
    }

    public void setBadgeTextParams(float textSize, int textColor, String fontName) {
        countDrawable.setBadgeTextParams(textSize, textColor);
        if (fontName != null) {
            countDrawable.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName));
        }
        invalidate();
    }

    private int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }
}
