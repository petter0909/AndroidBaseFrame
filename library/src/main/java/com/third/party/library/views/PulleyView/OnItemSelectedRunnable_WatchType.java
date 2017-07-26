package com.third.party.library.views.PulleyView;

final class OnItemSelectedRunnable_WatchType implements Runnable {
    final WheelView_WatchType loopView;

    OnItemSelectedRunnable_WatchType(WheelView_WatchType loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        if(loopView.onItemSelectedListenerWatchType !=null) {
            loopView.onItemSelectedListenerWatchType.onItemSelected(loopView.getCurrentItem());
        }
    }
}
