package ir.awlrhm.calendar.animator;

import android.animation.ValueAnimator;

import ir.awlrhm.calendar.listener.SmallAnimationListener;

public class CalendarAnimation extends ValueAnimator {
    public void setListener(SmallAnimationListener smallAnimationListener) {
        addUpdateListener(smallAnimationListener);
        addListener(smallAnimationListener);
        start();
    }
}

