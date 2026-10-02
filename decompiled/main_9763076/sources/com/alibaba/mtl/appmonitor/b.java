package com.alibaba.mtl.appmonitor;

import com.alibaba.mtl.appmonitor.a.e;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;

/* JADX INFO: compiled from: CleanTask.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static b f19a;
    private static boolean j = false;
    private static long a = 300000;

    private b() {
    }

    static void init() {
        if (!j) {
            i.a("CleanTask", "init TimeoutEventManager");
            f19a = new b();
            r.a().a(5, f19a, a);
            j = true;
        }
    }

    static void destroy() {
        r.a().f(5);
        j = false;
        f19a = null;
    }

    @Override // java.lang.Runnable
    public void run() {
        i.a("CleanTask", "clean TimeoutEvent");
        e.a().h();
        r.a().a(5, f19a, a);
    }
}
