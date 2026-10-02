package com.facebook.react.uimanager.events;

import android.view.MotionEvent;
import android.view.View;
import com.facebook.react.uimanager.RootViewUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NativeGestureUtil {
    public static void notifyNativeGestureStarted(View view, MotionEvent event) {
        RootViewUtil.getRootView(view).onChildStartedNativeGesture(event);
    }
}
