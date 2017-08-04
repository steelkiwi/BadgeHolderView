package com.steelkiwi.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.steelkiwi.library.R;
import com.steelkiwi.library.util.Constants;

/**
 * Created by yaroslav on 8/2/17.
 */

public class BadgeHolderLayout extends ViewGroup implements BadgeCounter {

    private BadgeView badgeView;
    // default badge background
    private int badgeBackgroundColor;
    // font type for all texts
    private String fontName;
    // badge text size
    private float textSize;
    // badge text color
    private int textColor;
    // badge corner radius
    private int cornerRadius;

    public BadgeHolderLayout(Context context) {
        super(context);
        init(null);
    }

    public BadgeHolderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BadgeHolderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if(attrs != null) {
            retrieveAttributeSet(attrs);
        }
    }

    private void retrieveAttributeSet(AttributeSet attrs) {
        TypedArray array = getResources().obtainAttributes(attrs, R.styleable.BadgeHolderLayout);
        retrieveBadgeBackgroundColor(array.getColor(R.styleable.BadgeHolderLayout_bhl_default_badge_background,
                ContextCompat.getColor(getContext(), R.color.orange)));
        retrieveFontName(array.getString(R.styleable.BadgeHolderLayout_bhl_text_font));
        retrieveTextSize(array.getDimensionPixelSize(R.styleable.BadgeHolderLayout_bhl_text_size,
                getResources().getDimensionPixelSize(R.dimen.default_text_size)));
        retrieveTextColor(array.getColor(R.styleable.BadgeHolderLayout_bhl_text_color,
                ContextCompat.getColor(getContext(), android.R.color.white)));
        retrieveCornerRadius(array.getDimensionPixelSize(R.styleable.BadgeHolderLayout_bhl_badge_radius,
                getResources().getDimensionPixelSize(R.dimen.badge_corner_radius)));
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // init badge view for showing count
        initBadgeView();
    }

    private void initBadgeView() {
        badgeView = new BadgeView(getContext());
        badgeView.setBadgeBackground(getBadgeBackgroundColor());
        badgeView.setBadgeTextParams(getTextSize(), getTextColor(), getFontName());
        badgeView.setCornerRadius(getCornerRadius());
        addView(badgeView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = reconcileSize(MeasureSpec.getSize(widthMeasureSpec), widthMeasureSpec);
        int height = reconcileSize(MeasureSpec.getSize(heightMeasureSpec), heightMeasureSpec);
        // measure child view
        measureImageViewChild(width, height);
        measureBadgeView(width, height);
        setMeasuredDimension(width, height);
    }

    private void measureImageViewChild(int width, int height) {
        View view = getChildAt(0);
        int viewSquareSpec = MeasureSpec.makeMeasureSpec((int) (Math.max(width, height) * Constants.VIEW_SCALE_FACTOR), MeasureSpec.EXACTLY);
        view.measure(viewSquareSpec, viewSquareSpec);
    }

    private void measureBadgeView(int width, int height) {
        int badgeWidthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int badgeHeightSpec = MeasureSpec.makeMeasureSpec((int)(height * .5f * Constants.VIEW_SCALE_FACTOR), MeasureSpec.EXACTLY);
        badgeView.measure(badgeWidthSpec, badgeHeightSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        layoutImageViewChild();
        layoutBadgeView();
    }

    private void layoutImageViewChild() {
        View view = getChildAt(0);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int left = centerX - view.getMeasuredWidth() / 2;
        int top = centerY - view.getMeasuredHeight() / 2;
        int right = centerX + view.getMeasuredWidth() / 2;
        int bottom = centerY + view.getMeasuredHeight() / 2;
        view.layout(left, top, bottom, right);
    }

    private void layoutBadgeView() {
        int centerX = getWidth() / 2;
        badgeView.layout(centerX, 0, getWidth(), badgeView.getMeasuredHeight());
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

    @Override
    public void increment() {
        badgeView.increment();
    }

    @Override
    public void decrement() {
        badgeView.decrement();
    }

    @Override
    public void setBadgeBackground(int color) {
        badgeView.setBadgeBackground(color);
    }

    @Override
    public void setCount(int count) {
        badgeView.setCount(count);
    }

    @Override
    public void setCountWithAnimation(int count) {
        badgeView.setCountWithAnimation(count);
    }

    @Override
    public void reset() {
        badgeView.reset();
    }

    @Override
    public int getCount() {
        return badgeView.getCount();
    }

    private int getBadgeBackgroundColor() {
        return badgeBackgroundColor;
    }

    private void retrieveBadgeBackgroundColor(int badgeBackgroundColor) {
        this.badgeBackgroundColor = badgeBackgroundColor;
    }

    private String getFontName() {
        return fontName;
    }

    private void retrieveFontName(String fontName) {
        this.fontName = fontName;
    }

    private float getTextSize() {
        return textSize;
    }

    private void retrieveTextSize(float textSize) {
        this.textSize = textSize;
    }

    private int getTextColor() {
        return textColor;
    }

    private void retrieveTextColor(int textColor) {
        this.textColor = textColor;
    }

    private int getCornerRadius() {
        return cornerRadius;
    }

    private void retrieveCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }
}
