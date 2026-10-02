package com.facebook.react.uimanager;

import android.view.Choreographer;
import com.facebook.common.logging.FLog;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.UiThreadUtil;
import java.util.ArrayDeque;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class ReactChoreographer {
    private static ReactChoreographer sInstance;
    private int mTotalCallbacks = 0;
    private boolean mHasPostedCallback = false;
    private final Choreographer mChoreographer = Choreographer.getInstance();
    private final ReactChoreographerDispatcher mReactChoreographerDispatcher = new ReactChoreographerDispatcher();
    private final ArrayDeque<Choreographer.FrameCallback>[] mCallbackQueues = new ArrayDeque[CallbackType.values().length];

    static /* synthetic */ int access$310(ReactChoreographer x0) {
        int i = x0.mTotalCallbacks;
        x0.mTotalCallbacks = i - 1;
        return i;
    }

    public enum CallbackType {
        PERF_MARKERS(0),
        DISPATCH_UI(1),
        NATIVE_ANIMATED_MODULE(2),
        TIMERS_EVENTS(3),
        IDLE_EVENT(4);

        private final int mOrder;

        CallbackType(int order) {
            this.mOrder = order;
        }

        int getOrder() {
            return this.mOrder;
        }
    }

    public static ReactChoreographer getInstance() {
        UiThreadUtil.assertOnUiThread();
        if (sInstance == null) {
            sInstance = new ReactChoreographer();
        }
        return sInstance;
    }

    private ReactChoreographer() {
        for (int i = 0; i < this.mCallbackQueues.length; i++) {
            this.mCallbackQueues[i] = new ArrayDeque<>();
        }
    }

    public void postFrameCallback(CallbackType type, Choreographer.FrameCallback frameCallback) {
        UiThreadUtil.assertOnUiThread();
        this.mCallbackQueues[type.getOrder()].addLast(frameCallback);
        this.mTotalCallbacks++;
        Assertions.assertCondition(this.mTotalCallbacks > 0);
        if (!this.mHasPostedCallback) {
            this.mChoreographer.postFrameCallback(this.mReactChoreographerDispatcher);
            this.mHasPostedCallback = true;
        }
    }

    public void removeFrameCallback(CallbackType type, Choreographer.FrameCallback frameCallback) {
        UiThreadUtil.assertOnUiThread();
        if (this.mCallbackQueues[type.getOrder()].removeFirstOccurrence(frameCallback)) {
            this.mTotalCallbacks--;
            maybeRemoveFrameCallback();
        } else {
            FLog.e("React", "Tried to remove non-existent frame callback");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeRemoveFrameCallback() {
        Assertions.assertCondition(this.mTotalCallbacks >= 0);
        if (this.mTotalCallbacks == 0 && this.mHasPostedCallback) {
            this.mChoreographer.removeFrameCallback(this.mReactChoreographerDispatcher);
            this.mHasPostedCallback = false;
        }
    }

    private class ReactChoreographerDispatcher implements Choreographer.FrameCallback {
        private ReactChoreographerDispatcher() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long frameTimeNanos) {
            ReactChoreographer.this.mHasPostedCallback = false;
            for (int i = 0; i < ReactChoreographer.this.mCallbackQueues.length; i++) {
                int initialLength = ReactChoreographer.this.mCallbackQueues[i].size();
                for (int callback = 0; callback < initialLength; callback++) {
                    ((Choreographer.FrameCallback) ReactChoreographer.this.mCallbackQueues[i].removeFirst()).doFrame(frameTimeNanos);
                    ReactChoreographer.access$310(ReactChoreographer.this);
                }
            }
            ReactChoreographer.this.maybeRemoveFrameCallback();
        }
    }
}
