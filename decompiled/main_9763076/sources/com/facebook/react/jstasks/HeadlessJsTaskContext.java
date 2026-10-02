package com.facebook.react.jstasks;

import android.os.Handler;
import android.util.SparseArray;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import java.lang.ref.WeakReference;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class HeadlessJsTaskContext {
    private static final WeakHashMap<ReactContext, HeadlessJsTaskContext> INSTANCES = new WeakHashMap<>();
    private final WeakReference<ReactContext> mReactContext;
    private final Set<HeadlessJsTaskEventListener> mHeadlessJsTaskEventListeners = new CopyOnWriteArraySet();
    private final AtomicInteger mLastTaskId = new AtomicInteger(0);
    private final Handler mHandler = new Handler();
    private final Set<Integer> mActiveTasks = new CopyOnWriteArraySet();
    private final SparseArray<Runnable> mTaskTimeouts = new SparseArray<>();

    public static HeadlessJsTaskContext getInstance(ReactContext context) {
        HeadlessJsTaskContext helper = INSTANCES.get(context);
        if (helper == null) {
            HeadlessJsTaskContext helper2 = new HeadlessJsTaskContext(context);
            INSTANCES.put(context, helper2);
            return helper2;
        }
        return helper;
    }

    private HeadlessJsTaskContext(ReactContext reactContext) {
        this.mReactContext = new WeakReference<>(reactContext);
    }

    public void addTaskEventListener(HeadlessJsTaskEventListener listener) {
        this.mHeadlessJsTaskEventListeners.add(listener);
    }

    public void removeTaskEventListener(HeadlessJsTaskEventListener listener) {
        this.mHeadlessJsTaskEventListeners.remove(listener);
    }

    public boolean hasActiveTasks() {
        return this.mActiveTasks.size() > 0;
    }

    public synchronized void finishTask(final int taskId) {
        Assertions.assertCondition(this.mActiveTasks.remove(Integer.valueOf(taskId)), "Tried to finish non-existent task with id " + taskId + ".");
        Runnable timeout = this.mTaskTimeouts.get(taskId);
        if (timeout != null) {
            this.mHandler.removeCallbacks(timeout);
            this.mTaskTimeouts.remove(taskId);
        }
        UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.jstasks.HeadlessJsTaskContext.1
            @Override // java.lang.Runnable
            public void run() {
                for (HeadlessJsTaskEventListener listener : HeadlessJsTaskContext.this.mHeadlessJsTaskEventListeners) {
                    listener.onHeadlessJsTaskFinish(taskId);
                }
            }
        });
    }

    public synchronized boolean isTaskRunning(int taskId) {
        return this.mActiveTasks.contains(Integer.valueOf(taskId));
    }
}
