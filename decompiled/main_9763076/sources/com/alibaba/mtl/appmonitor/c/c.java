package com.alibaba.mtl.appmonitor.c;

import com.alibaba.mtl.appmonitor.c.b;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: compiled from: ReuseItemPool.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c<T extends b> {
    private static AtomicLong c = new AtomicLong(0);
    private static AtomicLong d = new AtomicLong(0);
    private final int m = 20;
    private Integer b = null;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private AtomicLong f20a = new AtomicLong(0);

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private AtomicLong f22b = new AtomicLong(0);
    private ConcurrentLinkedQueue<T> a = new ConcurrentLinkedQueue<>();

    /* JADX INFO: renamed from: b, reason: collision with other field name */
    private Set<Integer> f21b = new HashSet();

    public T a() {
        c.getAndIncrement();
        this.f20a.getAndIncrement();
        T tPoll = this.a.poll();
        if (tPoll != null) {
            this.f21b.remove(Integer.valueOf(System.identityHashCode(tPoll)));
            this.f22b.getAndIncrement();
            d.getAndIncrement();
        }
        return tPoll;
    }

    public void a(T t) {
        t.clean();
        if (this.a.size() < 20) {
            synchronized (this.f21b) {
                int iIdentityHashCode = System.identityHashCode(t);
                if (!this.f21b.contains(Integer.valueOf(iIdentityHashCode))) {
                    this.f21b.add(Integer.valueOf(iIdentityHashCode));
                    this.a.offer(t);
                }
            }
        }
    }
}
