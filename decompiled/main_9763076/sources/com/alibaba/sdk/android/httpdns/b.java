package com.alibaba.sdk.android.httpdns;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static final TimeUnit f54a = TimeUnit.SECONDS;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static final ThreadFactory f53a = new ThreadFactory() { // from class: com.alibaba.sdk.android.httpdns.b.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("httpdns worker");
            thread.setDaemon(false);
            thread.setUncaughtExceptionHandler(new i());
            return thread;
        }
    };
    private static final ExecutorService a = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1, f54a, new SynchronousQueue(), f53a);

    public static ExecutorService a() {
        return a;
    }
}
