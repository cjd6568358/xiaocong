package com.youzan.spiderman.c.e;

/* JADX INFO: compiled from: SyncPref.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class e {
    private long a = 0;
    private long b = 0;
    private long c = 0;

    public long a() {
        return this.b;
    }

    public void a(long timestamp) {
        this.b = timestamp;
    }

    public long b() {
        return this.a;
    }

    public void b(long syncTimestamp) {
        this.a = syncTimestamp;
    }

    public long c() {
        return this.c;
    }

    public void c(long configLastModifyTime) {
        this.c = configLastModifyTime;
    }
}
