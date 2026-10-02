package com.youzan.spiderman.d;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: compiled from: StreamDispatcher.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    private static b a = null;
    private ExecutorService b = Executors.newCachedThreadPool();

    public static b a() {
        if (a == null) {
            a = new b();
        }
        return a;
    }

    private b() {
    }

    public void a(Runnable runnable) {
        this.b.execute(runnable);
    }
}
