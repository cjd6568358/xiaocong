package com.alibaba.mtl.log.d;

import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import java.util.Random;

/* JADX INFO: compiled from: UploadEngine.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    static a a = new a();
    private int A;
    protected long z = com.alibaba.mtl.log.a.a.a();
    private boolean F = false;

    public static a a() {
        return a;
    }

    public synchronized void start() {
        this.F = true;
        if (r.a().b(2)) {
            r.a().f(2);
        }
        c();
        Random random = new Random();
        if (!b.isRunning()) {
            r.a().a(2, new b() { // from class: com.alibaba.mtl.log.d.a.1
                @Override // com.alibaba.mtl.log.d.b
                public void K() {
                    if (a.this.F) {
                        com.alibaba.mtl.log.b.a.E();
                        a.this.c();
                        i.a("UploadTask", "mPeriod:", Long.valueOf(a.this.z));
                        if (r.a().b(2)) {
                            r.a().f(2);
                        }
                        if (!b.isRunning()) {
                            r.a().a(2, this, a.this.z);
                        }
                    }
                }

                @Override // com.alibaba.mtl.log.d.b
                public void L() {
                    a.this.J();
                }
            }, random.nextInt((int) this.z));
        }
    }

    public void J() {
        if (this.A == 0) {
            this.A = 7000;
        } else {
            this.A = 0;
        }
    }

    public synchronized void stop() {
        this.F = false;
        r.a().f(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long c() {
        long jA;
        i.a("UploadEngine", "UTDC.bBackground:", Boolean.valueOf(com.alibaba.mtl.log.a.o), "AppInfoUtil.isForeground(UTDC.getContext()) ", Boolean.valueOf(com.alibaba.mtl.log.e.b.b(com.alibaba.mtl.log.a.getContext())));
        com.alibaba.mtl.log.a.o = com.alibaba.mtl.log.e.b.b(com.alibaba.mtl.log.a.getContext()) ? false : true;
        boolean z = com.alibaba.mtl.log.a.o;
        com.alibaba.mtl.log.a.a.a();
        if (z) {
            jA = com.alibaba.mtl.log.a.a.b() + ((long) this.A);
        } else {
            jA = com.alibaba.mtl.log.a.a.a() + ((long) this.A);
        }
        this.z = jA;
        if (com.alibaba.mtl.log.a.a.e()) {
            this.z = 3000L;
        }
        return this.z;
    }
}
