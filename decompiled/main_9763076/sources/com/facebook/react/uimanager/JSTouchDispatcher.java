package com.facebook.react.uimanager;

import android.view.MotionEvent;
import android.view.ViewGroup;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.TouchEvent;
import com.facebook.react.uimanager.events.TouchEventCoalescingKeyHelper;
import com.facebook.react.uimanager.events.TouchEventType;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSTouchDispatcher {
    private final ViewGroup mRootViewGroup;
    private int mTargetTag = -1;
    private final float[] mTargetCoordinates = new float[2];
    private boolean mChildIsHandlingNativeGesture = false;
    private final TouchEventCoalescingKeyHelper mTouchEventCoalescingKeyHelper = new TouchEventCoalescingKeyHelper();

    public JSTouchDispatcher(ViewGroup viewGroup) {
        this.mRootViewGroup = viewGroup;
    }

    public void onChildStartedNativeGesture(MotionEvent androidEvent, EventDispatcher eventDispatcher) {
        if (!this.mChildIsHandlingNativeGesture) {
            dispatchCancelEvent(androidEvent, eventDispatcher);
            this.mChildIsHandlingNativeGesture = true;
            this.mTargetTag = -1;
        }
    }

    public void handleTouchEvent(MotionEvent ev, EventDispatcher eventDispatcher) {
        int action = ev.getAction() & 255;
        if (action == 0) {
            if (this.mTargetTag != -1) {
                FLog.e("React", "Got DOWN touch before receiving UP or CANCEL from last gesture");
            }
            this.mChildIsHandlingNativeGesture = false;
            this.mTargetTag = TouchTargetHelper.findTargetTagAndCoordinatesForTouch(ev.getX(), ev.getY(), this.mRootViewGroup, this.mTargetCoordinates, null);
            eventDispatcher.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.START, ev, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
            return;
        }
        if (!this.mChildIsHandlingNativeGesture) {
            if (this.mTargetTag == -1) {
                FLog.e("React", "Unexpected state: received touch event but didn't get starting ACTION_DOWN for this gesture before");
                return;
            }
            if (action == 1) {
                eventDispatcher.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.END, ev, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
                this.mTargetTag = -1;
                return;
            }
            if (action == 2) {
                eventDispatcher.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.MOVE, ev, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
                return;
            }
            if (action == 5) {
                eventDispatcher.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.START, ev, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
                return;
            }
            if (action == 6) {
                eventDispatcher.dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.END, ev, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
                return;
            }
            if (action == 3) {
                if (this.mTouchEventCoalescingKeyHelper.hasCoalescingKey(ev.getDownTime())) {
                    dispatchCancelEvent(ev, eventDispatcher);
                } else {
                    FLog.e("React", "Received an ACTION_CANCEL touch event for which we have no corresponding ACTION_DOWN");
                }
                this.mTargetTag = -1;
                return;
            }
            FLog.w("React", "Warning : touch event was ignored. Action=" + action + " Target=" + this.mTargetTag);
        }
    }

    private void dispatchCancelEvent(MotionEvent androidEvent, EventDispatcher eventDispatcher) {
        if (this.mTargetTag == -1) {
            FLog.w("React", "Can't cancel already finished gesture. Is a child View trying to start a gesture from an UP/CANCEL event?");
        } else {
            Assertions.assertCondition(!this.mChildIsHandlingNativeGesture, "Expected to not have already sent a cancel for this gesture");
            ((EventDispatcher) Assertions.assertNotNull(eventDispatcher)).dispatchEvent(TouchEvent.obtain(this.mTargetTag, TouchEventType.CANCEL, androidEvent, this.mTargetCoordinates[0], this.mTargetCoordinates[1], this.mTouchEventCoalescingKeyHelper));
        }
    }
}
