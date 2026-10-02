package com.xiaomi.stats;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.at;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import org.apache.thrift.protocol.k;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class f {
    private String a;
    private int c;
    private long d;
    private e e;
    private boolean b = false;
    private com.xiaomi.channel.commonutils.stats.a f = com.xiaomi.channel.commonutils.stats.a.a();

    static class a {
        static final f a = new f();
    }

    private com.xiaomi.push.thrift.b a(com.xiaomi.channel.commonutils.stats.a.C0006a c0006a) {
        if (c0006a.a == 0) {
            if (c0006a.c instanceof com.xiaomi.push.thrift.b) {
                return (com.xiaomi.push.thrift.b) c0006a.c;
            }
            return null;
        }
        com.xiaomi.push.thrift.b bVarF = f();
        bVarF.a(com.xiaomi.push.thrift.a.CHANNEL_STATS_COUNTER.a());
        bVarF.c(c0006a.a);
        bVarF.c(c0006a.b);
        return bVarF;
    }

    public static f a() {
        return a.a;
    }

    private com.xiaomi.push.thrift.c b(int i) {
        ArrayList arrayList = new ArrayList();
        com.xiaomi.push.thrift.c cVar = new com.xiaomi.push.thrift.c(this.a, arrayList);
        if (!com.xiaomi.channel.commonutils.network.d.f(this.e.a)) {
            cVar.a(com.xiaomi.channel.commonutils.android.e.f(this.e.a));
        }
        org.apache.thrift.transport.b bVar = new org.apache.thrift.transport.b(i);
        org.apache.thrift.protocol.e eVarA = new k.a().a(bVar);
        try {
            cVar.b(eVarA);
        } catch (org.apache.thrift.f e) {
        }
        LinkedList<com.xiaomi.channel.commonutils.stats.a.C0006a> linkedListC = this.f.c();
        while (linkedListC.size() > 0) {
            try {
                com.xiaomi.push.thrift.b bVarA = a(linkedListC.getLast());
                if (bVarA != null) {
                    bVarA.b(eVarA);
                }
                if (bVar.a_() > i) {
                    break;
                }
                if (bVarA != null) {
                    arrayList.add(bVarA);
                }
                linkedListC.removeLast();
            } catch (NoSuchElementException e2) {
            } catch (org.apache.thrift.f e3) {
            }
        }
        return cVar;
    }

    public static e b() {
        e eVar;
        synchronized (a.a) {
            eVar = a.a.e;
        }
        return eVar;
    }

    private void g() {
        if (!this.b || System.currentTimeMillis() - this.d <= this.c) {
            return;
        }
        this.b = false;
        this.d = 0L;
    }

    public void a(int i) {
        if (i > 0) {
            int i2 = i * 1000;
            int i3 = i2 <= 604800000 ? i2 : 604800000;
            if (this.c == i3 && this.b) {
                return;
            }
            this.b = true;
            this.d = System.currentTimeMillis();
            this.c = i3;
            com.xiaomi.channel.commonutils.logger.b.c("enable dot duration = " + i3 + " start = " + this.d);
        }
    }

    public synchronized void a(XMPushService xMPushService) {
        this.e = new e(xMPushService);
        this.a = "";
        at.a().a(new g(this));
    }

    synchronized void a(com.xiaomi.push.thrift.b bVar) {
        this.f.a(bVar);
    }

    public boolean c() {
        return this.b;
    }

    boolean d() {
        g();
        return this.b && this.f.b() > 0;
    }

    synchronized com.xiaomi.push.thrift.c e() {
        com.xiaomi.push.thrift.c cVarB;
        cVarB = null;
        if (d()) {
            cVarB = b(com.xiaomi.channel.commonutils.network.d.f(this.e.a) ? 750 : 375);
        }
        return cVarB;
    }

    synchronized com.xiaomi.push.thrift.b f() {
        com.xiaomi.push.thrift.b bVar;
        bVar = new com.xiaomi.push.thrift.b();
        bVar.a(com.xiaomi.channel.commonutils.network.d.k(this.e.a));
        bVar.a = (byte) 0;
        bVar.c = 1;
        bVar.d((int) (System.currentTimeMillis() / 1000));
        if (this.e.b != null) {
            bVar.e(this.e.b.f());
        }
        return bVar;
    }
}
