package com.facebook.react.touch;

import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JSResponderHandler implements OnInterceptTouchEventListener {
    private volatile int mCurrentJSResponder = -1;
    private ViewParent mViewParentBlockingNativeResponder;

    public void setJSResponder(int tag, ViewParent viewParentBlockingNativeResponder) {
        this.mCurrentJSResponder = tag;
        maybeUnblockNativeResponder();
        if (viewParentBlockingNativeResponder != null) {
            viewParentBlockingNativeResponder.requestDisallowInterceptTouchEvent(true);
            this.mViewParentBlockingNativeResponder = viewParentBlockingNativeResponder;
        }
    }

    public void clearJSResponder() {
        this.mCurrentJSResponder = -1;
        maybeUnblockNativeResponder();
    }

    private void maybeUnblockNativeResponder() {
        if (this.mViewParentBlockingNativeResponder != null) {
            this.mViewParentBlockingNativeResponder.requestDisallowInterceptTouchEvent(false);
            this.mViewParentBlockingNativeResponder = null;
        }
    }

    @Override // com.facebook.react.touch.OnInterceptTouchEventListener
    public boolean onInterceptTouchEvent(ViewGroup v, MotionEvent event) {
        int currentJSResponder = this.mCurrentJSResponder;
        if (currentJSResponder == -1 || event.getAction() == 1) {
            return false;
        }
        return v.getId() == currentJSResponder;
    }
}
