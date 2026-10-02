package com.facebook.react.views.scroll;

import android.os.SystemClock;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class OnScrollDispatchHelper {
    private int mPrevX = Integer.MIN_VALUE;
    private int mPrevY = Integer.MIN_VALUE;
    private long mLastScrollEventTimeMs = -11;

    public boolean onScrollChanged(int x, int y) {
        long eventTime = SystemClock.uptimeMillis();
        boolean shouldDispatch = (eventTime - this.mLastScrollEventTimeMs <= 10 && this.mPrevX == x && this.mPrevY == y) ? false : true;
        this.mLastScrollEventTimeMs = eventTime;
        this.mPrevX = x;
        this.mPrevY = y;
        return shouldDispatch;
    }
}
