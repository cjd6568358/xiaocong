package com.youzan.spiderman.a;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: compiled from: SpiderJobManager.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c {
    private static c a = null;
    private ExecutorService b = Executors.newCachedThreadPool();

    public static c a() {
        if (a == null) {
            a = new c();
        }
        return a;
    }

    private c() {
    }

    public void a(a job) {
        this.b.execute(job);
    }
}
