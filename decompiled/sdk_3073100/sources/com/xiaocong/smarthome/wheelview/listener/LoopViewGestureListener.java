package com.xiaocong.smarthome.wheelview.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.xiaocong.smarthome.wheelview.view.WheelView;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final WheelView wheelView;

    public LoopViewGestureListener(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        this.wheelView.scrollBy(velocityY);
        return true;
    }
}
