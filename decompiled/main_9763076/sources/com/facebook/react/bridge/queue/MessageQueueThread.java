package com.facebook.react.bridge.queue;

import com.facebook.proguard.annotations.DoNotStrip;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@DoNotStrip
public interface MessageQueueThread {
    @DoNotStrip
    void assertIsOnThread();

    @DoNotStrip
    <T> Future<T> callOnQueue(Callable<T> callable);

    @DoNotStrip
    boolean isOnThread();

    @DoNotStrip
    void quitSynchronous();

    @DoNotStrip
    void runOnQueue(Runnable runnable);
}
