package com.facebook.drawee.gestures;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class GestureDetector {
    long mActionDownTime;
    float mActionDownX;
    float mActionDownY;
    ClickListener mClickListener;
    boolean mIsCapturingGesture;
    boolean mIsClickCandidate;
    final float mSingleTapSlopPx;

    public interface ClickListener {
        boolean onClick();
    }

    public GestureDetector(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mSingleTapSlopPx = viewConfiguration.getScaledTouchSlop();
        init();
    }

    public static GestureDetector newInstance(Context context) {
        return new GestureDetector(context);
    }

    public void init() {
        this.mClickListener = null;
        reset();
    }

    public void reset() {
        this.mIsCapturingGesture = false;
        this.mIsClickCandidate = false;
    }

    public void setClickListener(ClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public boolean isCapturingGesture() {
        return this.mIsCapturingGesture;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mIsCapturingGesture = true;
                this.mIsClickCandidate = true;
                this.mActionDownTime = event.getEventTime();
                this.mActionDownX = event.getX();
                this.mActionDownY = event.getY();
                return true;
            case 1:
                this.mIsCapturingGesture = false;
                if (Math.abs(event.getX() - this.mActionDownX) > this.mSingleTapSlopPx || Math.abs(event.getY() - this.mActionDownY) > this.mSingleTapSlopPx) {
                    this.mIsClickCandidate = false;
                }
                if (this.mIsClickCandidate && event.getEventTime() - this.mActionDownTime <= ViewConfiguration.getLongPressTimeout() && this.mClickListener != null) {
                    this.mClickListener.onClick();
                }
                this.mIsClickCandidate = false;
                return true;
            case 2:
                if (Math.abs(event.getX() - this.mActionDownX) > this.mSingleTapSlopPx || Math.abs(event.getY() - this.mActionDownY) > this.mSingleTapSlopPx) {
                    this.mIsClickCandidate = false;
                }
                return true;
            case 3:
                this.mIsCapturingGesture = false;
                this.mIsClickCandidate = false;
                return true;
            default:
                return true;
        }
    }
}
