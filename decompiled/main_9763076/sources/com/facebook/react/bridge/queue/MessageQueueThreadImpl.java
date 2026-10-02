package com.facebook.react.bridge.queue;

import android.os.Looper;
import android.os.Process;
import com.facebook.common.logging.FLog;
import com.facebook.proguard.annotations.DoNotStrip;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.futures.SimpleSettableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public class MessageQueueThreadImpl implements MessageQueueThread {
    private final MessageQueueThreadHandler mHandler;
    private final Looper mLooper;
    private final String mName;
    private volatile boolean mIsFinished = false;
    private final String mAssertionErrorMessage = "Expected to be called from the '" + getName() + "' thread!";

    private MessageQueueThreadImpl(String name, Looper looper, QueueThreadExceptionHandler exceptionHandler) {
        this.mName = name;
        this.mLooper = looper;
        this.mHandler = new MessageQueueThreadHandler(looper, exceptionHandler);
    }

    @Override // com.facebook.react.bridge.queue.MessageQueueThread
    @DoNotStrip
    public void runOnQueue(Runnable runnable) {
        if (this.mIsFinished) {
            FLog.w("React", "Tried to enqueue runnable on already finished thread: '" + getName() + "... dropping Runnable.");
        }
        this.mHandler.post(runnable);
    }

    @Override // com.facebook.react.bridge.queue.MessageQueueThread
    @DoNotStrip
    public <T> Future<T> callOnQueue(final Callable<T> callable) {
        final SimpleSettableFuture<T> future = new SimpleSettableFuture<>();
        runOnQueue(new Runnable() { // from class: com.facebook.react.bridge.queue.MessageQueueThreadImpl.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    future.set(callable.call());
                } catch (Exception e) {
                    future.setException(e);
                }
            }
        });
        return future;
    }

    @Override // com.facebook.react.bridge.queue.MessageQueueThread
    @DoNotStrip
    public boolean isOnThread() {
        return this.mLooper.getThread() == Thread.currentThread();
    }

    @Override // com.facebook.react.bridge.queue.MessageQueueThread
    @DoNotStrip
    public void assertIsOnThread() {
        SoftAssertions.assertCondition(isOnThread(), this.mAssertionErrorMessage);
    }

    @Override // com.facebook.react.bridge.queue.MessageQueueThread
    @DoNotStrip
    public void quitSynchronous() {
        this.mIsFinished = true;
        this.mLooper.quit();
        if (this.mLooper.getThread() != Thread.currentThread()) {
            try {
                this.mLooper.getThread().join();
            } catch (InterruptedException e) {
                throw new RuntimeException("Got interrupted waiting to join thread " + this.mName);
            }
        }
    }

    public String getName() {
        return this.mName;
    }

    public static MessageQueueThreadImpl create(MessageQueueThreadSpec spec, QueueThreadExceptionHandler exceptionHandler) {
        switch (spec.getThreadType()) {
            case MAIN_UI:
                return createForMainThread(spec.getName(), exceptionHandler);
            case NEW_BACKGROUND:
                return startNewBackgroundThread(spec.getName(), spec.getStackSize(), exceptionHandler);
            default:
                throw new RuntimeException("Unknown thread type: " + spec.getThreadType());
        }
    }

    private static MessageQueueThreadImpl createForMainThread(String name, QueueThreadExceptionHandler exceptionHandler) {
        Looper mainLooper = Looper.getMainLooper();
        MessageQueueThreadImpl mqt = new MessageQueueThreadImpl(name, mainLooper, exceptionHandler);
        if (UiThreadUtil.isOnUiThread()) {
            Process.setThreadPriority(-4);
            MessageQueueThreadRegistry.register(mqt);
        } else {
            UiThreadUtil.runOnUiThread(new Runnable() { // from class: com.facebook.react.bridge.queue.MessageQueueThreadImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    Process.setThreadPriority(-4);
                    MessageQueueThreadRegistry.register(MessageQueueThreadImpl.this);
                }
            });
        }
        return mqt;
    }

    public static MessageQueueThreadImpl startNewBackgroundThread(String name, long stackSize, QueueThreadExceptionHandler exceptionHandler) {
        final SimpleSettableFuture<Looper> looperFuture = new SimpleSettableFuture<>();
        final SimpleSettableFuture<MessageQueueThread> mqtFuture = new SimpleSettableFuture<>();
        Thread bgThread = new Thread(null, new Runnable() { // from class: com.facebook.react.bridge.queue.MessageQueueThreadImpl.3
            @Override // java.lang.Runnable
            public void run() {
                Looper.prepare();
                looperFuture.set(Looper.myLooper());
                MessageQueueThreadRegistry.register((MessageQueueThread) mqtFuture.getOrThrow());
                Looper.loop();
            }
        }, "mqt_" + name, stackSize);
        bgThread.start();
        Looper myLooper = looperFuture.getOrThrow();
        MessageQueueThreadImpl mqt = new MessageQueueThreadImpl(name, myLooper, exceptionHandler);
        mqtFuture.set(mqt);
        return mqt;
    }
}
