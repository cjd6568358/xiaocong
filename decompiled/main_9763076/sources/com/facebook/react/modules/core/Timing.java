package com.facebook.react.modules.core;

import android.util.SparseArray;
import android.view.Choreographer;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ExecutorToken;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.common.SystemClock;
import com.facebook.react.devsupport.DevSupportManager;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;
import com.facebook.react.uimanager.ReactChoreographer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class Timing extends ReactContextBaseJavaModule implements LifecycleEventListener, HeadlessJsTaskEventListener {
    private static final float FRAME_DURATION_MS = 16.666666f;
    private static final float IDLE_CALLBACK_FRAME_DEADLINE_MS = 1.0f;
    protected static final String NAME = "Timing";
    private final AtomicBoolean isPaused;
    private final AtomicBoolean isRunningTasks;
    private IdleCallbackRunnable mCurrentIdleCallbackRunnable;
    private final DevSupportManager mDevSupportManager;
    private boolean mFrameCallbackPosted;
    private boolean mFrameIdleCallbackPosted;
    private final List<ExecutorToken> mIdleCallbackContextsToCall;
    private final Object mIdleCallbackGuard;
    private final IdleFrameCallback mIdleFrameCallback;
    private ReactChoreographer mReactChoreographer;
    private final Set<ExecutorToken> mSendIdleEventsExecutorTokens;
    private final TimerFrameCallback mTimerFrameCallback;
    private final Object mTimerGuard;
    private final Map<ExecutorToken, SparseArray<Timer>> mTimerIdsToTimers;
    private final PriorityQueue<Timer> mTimers;

    private static class Timer {
        private final int mCallbackID;
        private final ExecutorToken mExecutorToken;
        private final int mInterval;
        private final boolean mRepeat;
        private long mTargetTime;

        private Timer(ExecutorToken executorToken, int callbackID, long initialTargetTime, int duration, boolean repeat) {
            this.mExecutorToken = executorToken;
            this.mCallbackID = callbackID;
            this.mTargetTime = initialTargetTime;
            this.mInterval = duration;
            this.mRepeat = repeat;
        }
    }

    private class TimerFrameCallback implements Choreographer.FrameCallback {
        private final HashMap<ExecutorToken, WritableArray> mTimersToCall;

        private TimerFrameCallback() {
            this.mTimersToCall = new HashMap<>();
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long frameTimeNanos) {
            if (!Timing.this.isPaused.get() || Timing.this.isRunningTasks.get()) {
                long frameTimeMillis = frameTimeNanos / 1000000;
                synchronized (Timing.this.mTimerGuard) {
                    while (!Timing.this.mTimers.isEmpty() && ((Timer) Timing.this.mTimers.peek()).mTargetTime < frameTimeMillis) {
                        Timer timer = (Timer) Timing.this.mTimers.poll();
                        WritableArray timersForContext = this.mTimersToCall.get(timer.mExecutorToken);
                        if (timersForContext == null) {
                            timersForContext = Arguments.createArray();
                            this.mTimersToCall.put(timer.mExecutorToken, timersForContext);
                        }
                        timersForContext.pushInt(timer.mCallbackID);
                        if (timer.mRepeat) {
                            timer.mTargetTime = ((long) timer.mInterval) + frameTimeMillis;
                            Timing.this.mTimers.add(timer);
                        } else {
                            SparseArray<Timer> timers = (SparseArray) Timing.this.mTimerIdsToTimers.get(timer.mExecutorToken);
                            if (timers != null) {
                                timers.remove(timer.mCallbackID);
                                if (timers.size() == 0) {
                                    Timing.this.mTimerIdsToTimers.remove(timer.mExecutorToken);
                                }
                            }
                        }
                    }
                }
                for (Map.Entry<ExecutorToken, WritableArray> entry : this.mTimersToCall.entrySet()) {
                    ((JSTimersExecution) Timing.this.getReactApplicationContext().getJSModule(entry.getKey(), JSTimersExecution.class)).callTimers(entry.getValue());
                }
                this.mTimersToCall.clear();
                ((ReactChoreographer) Assertions.assertNotNull(Timing.this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this);
            }
        }
    }

    private class IdleFrameCallback implements Choreographer.FrameCallback {
        private IdleFrameCallback() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long frameTimeNanos) {
            if (!Timing.this.isPaused.get() || Timing.this.isRunningTasks.get()) {
                if (Timing.this.mCurrentIdleCallbackRunnable != null) {
                    Timing.this.mCurrentIdleCallbackRunnable.cancel();
                }
                Timing.this.mCurrentIdleCallbackRunnable = Timing.this.new IdleCallbackRunnable(frameTimeNanos);
                Timing.this.getReactApplicationContext().runOnJSQueueThread(Timing.this.mCurrentIdleCallbackRunnable);
                ((ReactChoreographer) Assertions.assertNotNull(Timing.this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this);
            }
        }
    }

    private class IdleCallbackRunnable implements Runnable {
        private volatile boolean mCancelled = false;
        private final long mFrameStartTime;

        public IdleCallbackRunnable(long frameStartTime) {
            this.mFrameStartTime = frameStartTime;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.mCancelled) {
                long frameTimeMillis = this.mFrameStartTime / 1000000;
                long timeSinceBoot = SystemClock.uptimeMillis();
                long frameTimeElapsed = timeSinceBoot - frameTimeMillis;
                long time = SystemClock.currentTimeMillis();
                long absoluteFrameStartTime = time - frameTimeElapsed;
                if (Timing.FRAME_DURATION_MS - frameTimeElapsed >= Timing.IDLE_CALLBACK_FRAME_DEADLINE_MS) {
                    Timing.this.mIdleCallbackContextsToCall.clear();
                    synchronized (Timing.this.mIdleCallbackGuard) {
                        Timing.this.mIdleCallbackContextsToCall.addAll(Timing.this.mSendIdleEventsExecutorTokens);
                    }
                    for (ExecutorToken context : Timing.this.mIdleCallbackContextsToCall) {
                        ((JSTimersExecution) Timing.this.getReactApplicationContext().getJSModule(context, JSTimersExecution.class)).callIdleCallbacks(absoluteFrameStartTime);
                    }
                    Timing.this.mCurrentIdleCallbackRunnable = null;
                }
            }
        }

        public void cancel() {
            this.mCancelled = true;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Timing(ReactApplicationContext reactContext, DevSupportManager devSupportManager) {
        super(reactContext);
        this.mTimerGuard = new Object();
        this.mIdleCallbackGuard = new Object();
        this.isPaused = new AtomicBoolean(true);
        this.isRunningTasks = new AtomicBoolean(false);
        this.mTimerFrameCallback = new TimerFrameCallback();
        this.mIdleFrameCallback = new IdleFrameCallback();
        this.mFrameCallbackPosted = false;
        this.mFrameIdleCallbackPosted = false;
        this.mDevSupportManager = devSupportManager;
        this.mTimers = new PriorityQueue<>(11, new Comparator<Timer>() { // from class: com.facebook.react.modules.core.Timing.1
            @Override // java.util.Comparator
            public int compare(Timer lhs, Timer rhs) {
                long diff = lhs.mTargetTime - rhs.mTargetTime;
                if (diff == 0) {
                    return 0;
                }
                if (diff < 0) {
                    return -1;
                }
                return 1;
            }
        });
        this.mTimerIdsToTimers = new HashMap();
        this.mSendIdleEventsExecutorTokens = new HashSet();
        this.mIdleCallbackContextsToCall = new ArrayList();
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void initialize() {
        this.mReactChoreographer = ReactChoreographer.getInstance();
        getReactApplicationContext().addLifecycleEventListener(this);
        HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance(getReactApplicationContext());
        headlessJsTaskContext.addTaskEventListener(this);
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostPause() {
        this.isPaused.set(true);
        clearChoreographerCallback();
        maybeClearChoreographerIdleCallback();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostDestroy() {
        clearChoreographerCallback();
        maybeClearChoreographerIdleCallback();
    }

    @Override // com.facebook.react.bridge.LifecycleEventListener
    public void onHostResume() {
        this.isPaused.set(false);
        setChoreographerCallback();
        maybeSetChoreographerIdleCallback();
    }

    public void onHeadlessJsTaskStart(int taskId) {
        if (!this.isRunningTasks.getAndSet(true)) {
            setChoreographerCallback();
            maybeSetChoreographerIdleCallback();
        }
    }

    @Override // com.facebook.react.jstasks.HeadlessJsTaskEventListener
    public void onHeadlessJsTaskFinish(int taskId) {
        HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance(getReactApplicationContext());
        if (!headlessJsTaskContext.hasActiveTasks()) {
            this.isRunningTasks.set(false);
            clearChoreographerCallback();
            maybeClearChoreographerIdleCallback();
        }
    }

    @Override // com.facebook.react.bridge.BaseJavaModule, com.facebook.react.bridge.NativeModule
    public void onCatalystInstanceDestroy() {
        clearChoreographerCallback();
        clearChoreographerIdleCallback();
        HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance(getReactApplicationContext());
        headlessJsTaskContext.removeTaskEventListener(this);
    }

    private void maybeSetChoreographerIdleCallback() {
        synchronized (this.mIdleCallbackGuard) {
            if (this.mSendIdleEventsExecutorTokens.size() > 0) {
                setChoreographerIdleCallback();
            }
        }
    }

    private void maybeClearChoreographerIdleCallback() {
        if (this.isPaused.get() && !this.isRunningTasks.get()) {
            clearChoreographerCallback();
        }
    }

    private void setChoreographerCallback() {
        if (!this.mFrameCallbackPosted) {
            ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this.mTimerFrameCallback);
            this.mFrameCallbackPosted = true;
        }
    }

    private void clearChoreographerCallback() {
        HeadlessJsTaskContext headlessJsTaskContext = HeadlessJsTaskContext.getInstance(getReactApplicationContext());
        if (this.mFrameCallbackPosted && this.isPaused.get() && !headlessJsTaskContext.hasActiveTasks()) {
            ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).removeFrameCallback(ReactChoreographer.CallbackType.TIMERS_EVENTS, this.mTimerFrameCallback);
            this.mFrameCallbackPosted = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setChoreographerIdleCallback() {
        if (!this.mFrameIdleCallbackPosted) {
            ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).postFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this.mIdleFrameCallback);
            this.mFrameIdleCallbackPosted = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearChoreographerIdleCallback() {
        if (this.mFrameIdleCallbackPosted) {
            ((ReactChoreographer) Assertions.assertNotNull(this.mReactChoreographer)).removeFrameCallback(ReactChoreographer.CallbackType.IDLE_EVENT, this.mIdleFrameCallback);
            this.mFrameIdleCallbackPosted = false;
        }
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return NAME;
    }

    @Override // com.facebook.react.bridge.BaseJavaModule
    public boolean supportsWebWorkers() {
        return true;
    }

    public void onExecutorDestroyed(ExecutorToken executorToken) {
        synchronized (this.mTimerGuard) {
            SparseArray<Timer> timersForContext = this.mTimerIdsToTimers.remove(executorToken);
            if (timersForContext != null) {
                for (int i = 0; i < timersForContext.size(); i++) {
                    Timer timer = timersForContext.get(timersForContext.keyAt(i));
                    this.mTimers.remove(timer);
                }
                synchronized (this.mIdleCallbackGuard) {
                    this.mSendIdleEventsExecutorTokens.remove(executorToken);
                }
            }
        }
    }

    @ReactMethod
    public void createTimer(ExecutorToken executorToken, int callbackID, int duration, double jsSchedulingTime, boolean repeat) {
        long deviceTime = SystemClock.currentTimeMillis();
        long remoteTime = (long) jsSchedulingTime;
        if (this.mDevSupportManager.getDevSupportEnabled()) {
            long driftTime = Math.abs(remoteTime - deviceTime);
            if (driftTime > 60000) {
                ((JSTimersExecution) getReactApplicationContext().getJSModule(executorToken, JSTimersExecution.class)).emitTimeDriftWarning("Debugger and device times have drifted by more than 60s. Please correct this by running adb shell \"date `date +%m%d%H%M%Y.%S`\" on your debugger machine.");
            }
        }
        long adjustedDuration = Math.max(0L, (remoteTime - deviceTime) + ((long) duration));
        if (duration == 0 && !repeat) {
            WritableArray timerToCall = Arguments.createArray();
            timerToCall.pushInt(callbackID);
            ((JSTimersExecution) getReactApplicationContext().getJSModule(executorToken, JSTimersExecution.class)).callTimers(timerToCall);
            return;
        }
        long initialTargetTime = (SystemClock.nanoTime() / 1000000) + adjustedDuration;
        Timer timer = new Timer(executorToken, callbackID, initialTargetTime, duration, repeat);
        synchronized (this.mTimerGuard) {
            this.mTimers.add(timer);
            SparseArray<Timer> timersForContext = this.mTimerIdsToTimers.get(executorToken);
            if (timersForContext == null) {
                timersForContext = new SparseArray<>();
                this.mTimerIdsToTimers.put(executorToken, timersForContext);
            }
            timersForContext.put(callbackID, timer);
        }
    }

    @ReactMethod
    public void deleteTimer(ExecutorToken executorToken, int timerId) {
        synchronized (this.mTimerGuard) {
            SparseArray<Timer> timersForContext = this.mTimerIdsToTimers.get(executorToken);
            if (timersForContext != null) {
                Timer timer = timersForContext.get(timerId);
                if (timer != null) {
                    timersForContext.remove(timerId);
                    if (timersForContext.size() == 0) {
                        this.mTimerIdsToTimers.remove(executorToken);
                    }
                    this.mTimers.remove(timer);
                }
            }
        }
    }

    @ReactMethod
    public void setSendIdleEvents(ExecutorToken executorToken, boolean sendIdleEvents) {
        synchronized (this.mIdleCallbackGuard) {
            try {
                if (sendIdleEvents) {
                    this.mSendIdleEventsExecutorTokens.add(executorToken);
                } else {
                    this.mSendIdleEventsExecutorTokens.remove(executorToken);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.modules.core.Timing.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (Timing.this.mIdleCallbackGuard) {
                    if (Timing.this.mSendIdleEventsExecutorTokens.size() > 0) {
                        Timing.this.setChoreographerIdleCallback();
                    } else {
                        Timing.this.clearChoreographerIdleCallback();
                    }
                }
            }
        });
    }
}
