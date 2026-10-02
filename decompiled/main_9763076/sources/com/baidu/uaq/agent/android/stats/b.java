package com.baidu.uaq.agent.android.stats;

/* JADX INFO: compiled from: TicToc.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private long ck;
    private a cl;
    private long startTime;

    /* JADX INFO: compiled from: TicToc.java */
    private enum a {
        STOPPED,
        STARTED
    }

    public void bu() {
        this.cl = a.STARTED;
        this.startTime = System.currentTimeMillis();
    }

    public long bv() {
        this.ck = System.currentTimeMillis();
        if (this.cl != a.STARTED) {
            return -1L;
        }
        this.cl = a.STOPPED;
        return this.ck - this.startTime;
    }
}
