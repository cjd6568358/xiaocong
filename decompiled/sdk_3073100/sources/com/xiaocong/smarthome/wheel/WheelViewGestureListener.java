package com.xiaocong.smarthome.wheel;

import android.view.GestureDetector;
import android.view.MotionEvent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class WheelViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    final WheelView wheelView;

    WheelViewGestureListener(WheelView wheelview) {
        this.wheelView = wheelview;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        this.wheelView.scrollBy(velocityY);
        return true;
    }
}
