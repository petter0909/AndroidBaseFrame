package com.third.party.library.views.PulleyView;

import android.view.MotionEvent;

final class LoopViewGestureListener_WatchType extends android.view.GestureDetector.SimpleOnGestureListener {

    final WheelView_WatchType loopView;

    LoopViewGestureListener_WatchType(WheelView_WatchType loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }
}
