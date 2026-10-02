package com.baidu.uaq.agent.android.stats;

import com.baidu.uaq.agent.android.UAQ;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: StatsEngine.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private ConcurrentHashMap<String, com.baidu.uaq.agent.android.metric.a> cj = new ConcurrentHashMap<>();
    private static final a ci = new a();
    private static final UAQ AGENT = UAQ.getInstance();

    private a() {
    }

    public static a br() {
        return ci;
    }

    public void L(String name) {
        com.baidu.uaq.agent.android.metric.a m = M(name);
        synchronized (m) {
            m.increment();
        }
    }

    public void c(String name, long time) {
        b(name, time / 1000.0f);
    }

    public void d(String name, long size) {
        b(name, size / 1024.0f);
    }

    public void b(String name, float value) {
        com.baidu.uaq.agent.android.metric.a m = M(name);
        synchronized (m) {
            m.a(value);
        }
    }

    private com.baidu.uaq.agent.android.metric.a M(String name) {
        com.baidu.uaq.agent.android.metric.a m = this.cj.get(name);
        if (m == null) {
            m = new com.baidu.uaq.agent.android.metric.a(name);
            if (AGENT.getConfig().isEnableStatsEngine()) {
                this.cj.put(name, m);
            }
        }
        return m;
    }

    public ConcurrentHashMap<String, com.baidu.uaq.agent.android.metric.a> bt() {
        return this.cj;
    }
}
