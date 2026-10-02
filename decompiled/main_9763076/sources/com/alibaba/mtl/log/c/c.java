package com.alibaba.mtl.log.c;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: compiled from: LogStoreMgr.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static c a;
    private List<com.alibaba.mtl.log.model.a> l = new CopyOnWriteArrayList();
    private Runnable b = new Runnable() { // from class: com.alibaba.mtl.log.c.c.1
        @Override // java.lang.Runnable
        public void run() {
            c.this.G();
        }
    };

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private com.alibaba.mtl.log.c.a f32a = new b(com.alibaba.mtl.log.a.getContext());

    private c() {
        com.alibaba.mtl.log.d.a.a().start();
        r.a().b(new a());
    }

    public static synchronized c a() {
        if (a == null) {
            a = new c();
        }
        return a;
    }

    public void a(com.alibaba.mtl.log.model.a aVar) {
        i.a("LogStoreMgr", "[add] :", aVar.X);
        com.alibaba.mtl.log.b.a.m(aVar.T);
        this.l.add(aVar);
        if (this.l.size() >= 100) {
            r.a().f(1);
            r.a().a(1, this.b, 0L);
        } else if (!r.a().b(1)) {
            r.a().a(1, this.b, 5000L);
        }
    }

    public int a(List<com.alibaba.mtl.log.model.a> list) {
        i.a("LogStoreMgr", list);
        return this.f32a.a(list);
    }

    public List<com.alibaba.mtl.log.model.a> a(String str, int i) {
        List<com.alibaba.mtl.log.model.a> listA = this.f32a.a(str, i);
        i.a("LogStoreMgr", "[get]", listA);
        return listA;
    }

    public synchronized void G() {
        i.a("LogStoreMgr", "[store]");
        ArrayList arrayList = null;
        try {
            synchronized (this.l) {
                if (this.l.size() > 0) {
                    arrayList = new ArrayList(this.l);
                    this.l.clear();
                }
            }
            if (arrayList != null && arrayList.size() > 0) {
                this.f32a.mo22a((List<com.alibaba.mtl.log.model.a>) arrayList);
            }
        } catch (Throwable th) {
        }
    }

    public void clear() {
        i.a("LogStoreMgr", "[clear]");
        this.f32a.clear();
        this.l.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void H() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, -3);
        this.f32a.c("time", String.valueOf(calendar.getTimeInMillis()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void I() {
        this.f32a.e(1000);
    }

    /* JADX INFO: compiled from: LogStoreMgr.java */
    class a implements Runnable {
        a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            c.this.H();
            if (c.this.f32a.g() > 9000) {
                c.this.I();
            }
        }
    }
}
