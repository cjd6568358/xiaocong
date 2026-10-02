package com.baidu.uaq.agent.android.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: NamedThreadFactory.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f implements ThreadFactory {
    private final ThreadGroup cP;
    private final String cQ;
    private final AtomicInteger cR = new AtomicInteger(1);

    public f(String factoryName) {
        SecurityManager s = System.getSecurityManager();
        this.cP = s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
        this.cQ = "APM_" + factoryName + "-";
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.cP, r, this.cQ + this.cR.getAndIncrement(), 0L);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != 5) {
            t.setPriority(5);
        }
        return t;
    }
}
