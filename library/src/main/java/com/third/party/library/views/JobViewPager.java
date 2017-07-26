package com.third.party.library.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by chun on 16/5/5.
 */

public class JobViewPager extends ViewPager {

    public static  boolean scrollble = true;

    public JobViewPager(Context context) {
        super(context);
    }

    public JobViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }





    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (scrollble == false) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }

    }

    @Override
    public void scrollTo(int x, int y){
        if (scrollble){
            super.scrollTo(x, y);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!scrollble){
            return true;
        }else {
            return super.dispatchTouchEvent(ev);
        }

    }
}