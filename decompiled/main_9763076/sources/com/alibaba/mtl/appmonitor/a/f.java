package com.alibaba.mtl.appmonitor.a;

import com.alibaba.mtl.log.e.i;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: EventType.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public enum f {
    ALARM(65501, 30, "alarmData", 5000),
    COUNTER(65502, 30, "counterData", 5000),
    OFFLINE_COUNTER(65133, 30, "counterData", 5000),
    STAT(65503, 30, "statData", 5000);

    static String TAG = "EventType";
    private int e;
    private int h;
    private int k;
    private String t;
    private int i = 25;
    private int j = 180;
    private boolean m = true;

    f(int i, int i2, String str, int i3) {
        this.e = i;
        this.h = i2;
        this.t = str;
        this.k = i3;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public int m12a() {
        return this.e;
    }

    public boolean isOpen() {
        return this.m;
    }

    public void b(boolean z) {
        this.m = z;
    }

    public int b() {
        return this.h;
    }

    public void b(int i) {
        i.a(TAG, "[setTriggerCount]", this.t, i + Constants.MAIN_VERSION_TAG);
        this.h = i;
    }

    public static f a(int i) {
        for (f fVar : values()) {
            if (fVar != null && fVar.m12a() == i) {
                return fVar;
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public String m13a() {
        return this.t;
    }

    public int c() {
        return this.i;
    }

    public int d() {
        return this.j;
    }

    public void setStatisticsInterval(int statisticsInterval) {
        this.i = statisticsInterval;
        this.j = statisticsInterval;
    }

    public int e() {
        return this.k;
    }

    public void c(int i) {
        this.k = i;
    }
}
