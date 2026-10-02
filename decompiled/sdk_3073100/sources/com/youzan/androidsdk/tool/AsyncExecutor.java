package com.youzan.androidsdk.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AsyncExecutor {
    private ExecutorService mJobExecutor;

    static class a {

        /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
        static AsyncExecutor f573 = new AsyncExecutor();
    }

    public static AsyncExecutor getInstance() {
        return a.f573;
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
