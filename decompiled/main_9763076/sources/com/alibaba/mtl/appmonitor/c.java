package com.alibaba.mtl.appmonitor;

import com.alibaba.mtl.appmonitor.a.e;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: UploadTask.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements Runnable {
    private static Map<Integer, c> f;
    private static boolean j = false;
    private int d;
    private int e;
    private long startTime = System.currentTimeMillis();

    static void init() {
        if (!j) {
            i.a("CommitTask", "init StatisticsAlarmEvent");
            f = new ConcurrentHashMap();
            for (f fVar : f.values()) {
                if (fVar.isOpen()) {
                    int iM12a = fVar.m12a();
                    c cVar = new c(iM12a, fVar.c() * 1000);
                    f.put(Integer.valueOf(iM12a), cVar);
                    r.a().a(a(iM12a), cVar, cVar.d);
                }
            }
            j = true;
        }
    }

    static void destroy() {
        for (f fVar : f.values()) {
            r.a().f(a(fVar.m12a()));
        }
        j = false;
        f = null;
    }

    static void a(int i, int i2) {
        i.a("CommitTask", "[setStatisticsInterval] eventId" + i + " statisticsInterval:" + i2);
        synchronized (f) {
            c cVar = f.get(Integer.valueOf(i));
            if (cVar == null) {
                if (i2 > 0) {
                    c cVar2 = new c(i, i2 * 1000);
                    f.put(Integer.valueOf(i), cVar2);
                    i.a("CommitTask", "post next eventId" + i + ": uploadTask.interval " + cVar2.d);
                    r.a().a(a(i), cVar2, cVar2.d);
                }
            } else if (i2 > 0) {
                if (cVar.d != i2 * 1000) {
                    r.a().f(a(i));
                    cVar.d = i2 * 1000;
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    long j2 = ((long) cVar.d) - (jCurrentTimeMillis - cVar.startTime);
                    long j3 = j2 >= 0 ? j2 : 0L;
                    i.a("CommitTask", cVar + "post next eventId" + i + " next:" + j3 + "  uploadTask.interval: " + cVar.d);
                    r.a().a(a(i), cVar, j3);
                    cVar.startTime = jCurrentTimeMillis;
                }
            } else {
                i.a("CommitTask", "uploadTasks.size:" + f.size());
                f.remove(Integer.valueOf(i));
                i.a("CommitTask", "uploadTasks.size:" + f.size());
            }
        }
    }

    private c(int i, int i2) {
        this.d = 180000;
        this.e = i;
        this.d = i2;
    }

    @Override // java.lang.Runnable
    public void run() {
        i.a("CommitTask", "check&commit event:", Integer.valueOf(this.e));
        e.a().m11a(this.e);
        if (f.containsValue(this)) {
            this.startTime = System.currentTimeMillis();
            i.a("CommitTask", "next:" + this.e);
            r.a().a(a(this.e), this, this.d);
        }
    }

    static void e() {
        for (f fVar : f.values()) {
            e.a().m11a(fVar.m12a());
        }
    }

    private static int a(int i) {
        switch (i) {
            case 65133:
                return 11;
            case 65501:
                return 6;
            case 65502:
                return 9;
            case 65503:
                return 10;
            default:
                return 0;
        }
    }
}
