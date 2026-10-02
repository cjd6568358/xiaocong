package com.facebook.react.views.swiperefresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.events.NativeGestureUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactSwipeRefreshLayout extends SwipeRefreshLayout {
    private boolean mDidLayout;
    private boolean mIntercepted;
    private float mPrevTouchX;
    private float mProgressViewOffset;
    private boolean mRefreshing;
    private int mTouchSlop;

    public ReactSwipeRefreshLayout(ReactContext reactContext) {
        super(reactContext);
        this.mDidLayout = false;
        this.mRefreshing = false;
        this.mProgressViewOffset = 0.0f;
        this.mTouchSlop = ViewConfiguration.get(reactContext).getScaledTouchSlop();
    }

    @Override // android.support.v4.widget.SwipeRefreshLayout
    public void setRefreshing(boolean refreshing) {
        this.mRefreshing = refreshing;
        if (this.mDidLayout) {
            super.setRefreshing(refreshing);
        }
    }

    public void setProgressViewOffset(float offset) {
        this.mProgressViewOffset = offset;
        if (this.mDidLayout) {
            int diameter = getProgressCircleDiameter();
            int start = Math.round(PixelUtil.toPixelFromDIP(offset)) - diameter;
            int end = Math.round(PixelUtil.toPixelFromDIP(64.0f + offset) - diameter);
            setProgressViewOffset(false, start, end);
        }
    }

    @Override // android.support.v4.widget.SwipeRefreshLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!this.mDidLayout) {
            this.mDidLayout = true;
            setProgressViewOffset(this.mProgressViewOffset);
            setRefreshing(this.mRefreshing);
        }
    }

    @Override // android.support.v4.widget.SwipeRefreshLayout, android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    @Override // android.support.v4.widget.SwipeRefreshLayout, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!shouldInterceptTouchEvent(ev) || !super.onInterceptTouchEvent(ev)) {
            return false;
        }
        NativeGestureUtil.notifyNativeGestureStarted(this, ev);
        return true;
    }

    private boolean shouldInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                this.mPrevTouchX = ev.getX();
                this.mIntercepted = false;
                break;
            case 2:
                float eventX = ev.getX();
                float xDiff = Math.abs(eventX - this.mPrevTouchX);
                if (this.mIntercepted || xDiff > this.mTouchSlop) {
                    this.mIntercepted = true;
                    return false;
                }
                break;
        }
        return true;
    }
}
