package com.facebook.react.uimanager;

import android.view.Choreographer;
import com.facebook.react.bridge.ReactContext;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class GuardedChoreographerFrameCallback implements Choreographer.FrameCallback {
    private final ReactContext mReactContext;

    protected abstract void doFrameGuarded(long j);

    protected GuardedChoreographerFrameCallback(ReactContext reactContext) {
        this.mReactContext = reactContext;
    }

    @Override // android.view.Choreographer.FrameCallback
    public final void doFrame(long frameTimeNanos) {
        try {
            doFrameGuarded(frameTimeNanos);
        } catch (RuntimeException e) {
            this.mReactContext.handleException(e);
        }
    }
}
