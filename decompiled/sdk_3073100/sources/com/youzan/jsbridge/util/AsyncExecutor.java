package com.youzan.jsbridge.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AsyncExecutor {
    private ExecutorService mJobExecutor;

    static class AsyncExecutorHolder {
        static AsyncExecutor sInstance = new AsyncExecutor();
    }

    public static AsyncExecutor getInstance() {
        return AsyncExecutorHolder.sInstance;
    }

    private AsyncExecutor() {
        this.mJobExecutor = Executors.newCachedThreadPool();
    }

    public void post(Runnable runnable) {
        if (runnable != null) {
            this.mJobExecutor.execute(runnable);
        }
    }
}
