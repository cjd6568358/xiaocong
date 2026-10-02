package com.tencent.android.tpush.horse;

import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.horse.data.StrategyItem;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class a {
    private static final Object a = new Object();
    private b d;
    private LinkedBlockingQueue b = new LinkedBlockingQueue();
    private ConcurrentHashMap c = new ConcurrentHashMap();
    private AtomicInteger e = new AtomicInteger(0);
    private volatile boolean f = false;

    public abstract void e();

    public abstract void f();

    public void a() {
        this.e.set(0);
    }

    public boolean b() {
        return this.e.get() > 0;
    }

    public boolean c() {
        return this.f;
    }

    public LinkedBlockingQueue d() {
        return this.b;
    }

    public void g() {
        if (XGPushConfig.enableDebug) {
            com.tencent.android.tpush.a.a.c("BaseTask", "startTask() with strategyItems size = " + this.b.size());
        }
        for (int i = 0; i < 1; i++) {
            try {
                try {
                    if (this.c.get(Integer.valueOf(i)) == null || ((c) this.c.get(Integer.valueOf(i))).getState() == Thread.State.TERMINATED) {
                        c cVar = new c(this, i);
                        this.c.put(Integer.valueOf(i), cVar);
                        cVar.start();
                    } else if (!((c) this.c.get(Integer.valueOf(i))).isAlive()) {
                        ((c) this.c.get(Integer.valueOf(i))).start();
                    }
                } catch (Exception e) {
                    this.c.remove(Integer.valueOf(i));
                    com.tencent.android.tpush.a.a.c(Constants.HorseLogTag, "startTask", e);
                }
            } catch (OutOfMemoryError e2) {
                com.tencent.android.tpush.a.a.i("BaseTask", "startTask() Exception = " + e2);
                return;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:7:0x000a A[Catch: all -> 0x004a, TryCatch #0 {, blocks: (B:5:0x0004, B:14:0x001c, B:15:0x002b, B:17:0x0031, B:19:0x003f, B:7:0x000a, B:9:0x000e, B:11:0x0014), top: B:24:0x0004 }] */
    synchronized void a(List list) {
        if (list != null) {
            if (1 > list.size()) {
                if (this.d != null && !b()) {
                    this.d.a(null);
                }
            } else {
                this.b.clear();
                this.f = false;
                a();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    StrategyItem strategyItem = (StrategyItem) it.next();
                    if (!this.b.contains(strategyItem)) {
                        this.b.add(strategyItem);
                        this.e.incrementAndGet();
                    }
                }
            }
        } else if (this.d != null) {
            this.d.a(null);
        }
    }

    public void a(int i) {
        c cVar;
        try {
            if (!this.c.isEmpty()) {
                Iterator it = this.c.keySet().iterator();
                while (it.hasNext()) {
                    int iIntValue = ((Integer) it.next()).intValue();
                    if (iIntValue != i && (cVar = (c) this.c.get(Integer.valueOf(iIntValue))) != null && cVar.a() != null) {
                        cVar.a().c();
                    }
                }
                c cVar2 = (c) this.c.remove(Integer.valueOf(i));
                if (cVar2 != null) {
                    cVar2.interrupt();
                }
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("BaseTask", "stopOtherHorse", e);
        }
    }

    public void a(b bVar) {
        this.d = bVar;
    }
}
