package com.steelkiwi.library.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;

/**
 * Created by yaroslav on 8/2/17.
 */

public class CountDrawable extends Drawable {

    private TextPaint textPaint;
    private int translationY;
    private int currentCount = 0;
    private int nextCount = 1;
    private boolean isIncrement = false;

    public CountDrawable() {
        setBadgeTextParams();
    }

    private void setBadgeTextParams() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // calculate center of the canvas
        float centerX = canvas.getWidth() * .5f;
        float centerY = canvas.getHeight() * .5f;
        canvas.save();
        // translate canvas to animate it
        canvas.translate(0, translationY);
        // draw current count and next count
        drawCurrentCount(canvas, centerX, centerY);
        drawNextCount(canvas, centerX, centerY);
        // restore canvas
        canvas.restore();
    }

    private void drawCurrentCount(final Canvas canvas, float centerX, float centerY) {
        String count = String.valueOf(currentCount);
        final float textWidth = textPaint.measureText(count);
        final float textX = Math.round(centerX - textWidth * .5f);
        final float textY = Math.round(centerY + getTextHeight(count) * .5f);
        canvas.drawText(count, textX, textY, textPaint);
    }

    private void drawNextCount(final Canvas canvas, float centerX, float centerY) {
        String count = String.valueOf(nextCount);
        final float textWidth = textPaint.measureText(count);
        final float textX = Math.round(centerX - textWidth * .5f);
        final float textY = Math.round(centerY + getTextHeight(count) * .5f);
        if(isIncrement) {
            canvas.drawText(count, textX, textY - canvas.getHeight(), textPaint);
        } else {
            canvas.drawText(count, textX, textY + canvas.getHeight(), textPaint);
        }
    }

    private float getTextHeight(String text) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    @Override
    public void setAlpha(int i) {
        // no need
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // no need
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setTranslationY(int translationY) {
        this.translationY = translationY;
    }

    public void setBadgeCount(int currentCount, int nextCount) {
        this.currentCount = currentCount;
        this.nextCount = nextCount;
    }

    public void setIncrement(boolean increment) {
        isIncrement = increment;
    }

    public void setBadgeTextParams(float textSize, int textColor) {
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
    }

    public void setTypeface(Typeface typeface) {
        if(typeface != null) {
            typeface = Typeface.DEFAULT;
        }
        textPaint.setTypeface(Typeface.create(typeface, Typeface.BOLD));
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }
}
