package com.meizu.cloud.pushsdk.common.b;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static ExecutorService a;

    private static synchronized Executor a() {
        if (a == null) {
            a = new ThreadPoolExecutor(0, 5, 180L, TimeUnit.SECONDS, new ArrayBlockingQueue(100, true));
        }
        return a;
    }

    public static void a(a aVar) {
        a();
        try {
            a.execute(aVar);
        } catch (RejectedExecutionException e) {
            new Thread(aVar).start();
        }
    }

    public static abstract class a implements Runnable {
        public abstract void a();

        @Override // java.lang.Runnable
        public final void run() {
            a();
        }
    }
}
