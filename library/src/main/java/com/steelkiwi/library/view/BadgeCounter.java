package com.steelkiwi.library.view;

/**
 * Created by yaroslav on 8/2/17.
 */

public interface BadgeCounter {
    void increment();
    void decrement();
    void setBadgeBackground(int color);
    void setCount(int count);
    void setCountWithAnimation(int count);
    void reset();
    int getCount();
}
