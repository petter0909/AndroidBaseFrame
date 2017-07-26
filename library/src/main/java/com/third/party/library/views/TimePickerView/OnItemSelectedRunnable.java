package com.third.party.library.views.TimePickerView;

final class OnItemSelectedRunnable implements Runnable {
    final WheelView loopView;

    OnItemSelectedRunnable(WheelView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        if(loopView.onItemSelectedListener!=null) {
            loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
        }
    }
}
