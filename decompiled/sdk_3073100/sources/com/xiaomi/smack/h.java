package com.xiaomi.smack;

import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.network.Fallback;
import com.xiaomi.network.Host;
import com.xiaomi.network.HostManager;
import com.xiaomi.push.service.XMPushService;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class h extends a {
    protected Exception o;
    protected Socket p;
    String q;
    protected XMPushService r;
    protected volatile long s;
    protected volatile long t;
    protected volatile long u;
    private String v;
    private int w;

    public h(XMPushService xMPushService, b bVar) {
        super(xMPushService, bVar);
        this.o = null;
        this.q = null;
        this.s = 0L;
        this.t = 0L;
        this.u = 0L;
        this.r = xMPushService;
    }

    private void a(b bVar) throws Throwable {
        a(bVar.e(), bVar.d());
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0105  */
    /* JADX WARN: Code duplicated, block: B:50:0x0212  */
    /* JADX WARN: Code duplicated, block: B:53:0x0228 A[RETURN] */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0225, code lost:
    
        if (android.text.TextUtils.equals(r10, com.xiaomi.channel.commonutils.network.d.k(r16.r)) != false) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void a(String str, int i) throws Throwable {
        boolean z;
        boolean z2;
        boolean z3 = false;
        this.o = null;
        ArrayList<String> arrayList = new ArrayList<>();
        int iIntValue = com.xiaomi.channel.commonutils.logger.b.e("get bucket for host : " + str).intValue();
        Fallback fallbackB = b(str);
        com.xiaomi.channel.commonutils.logger.b.a(Integer.valueOf(iIntValue));
        if (fallbackB != null) {
            arrayList = fallbackB.a(true);
        }
        if (arrayList.isEmpty()) {
            arrayList.add(str);
        }
        this.u = 0L;
        String strK = com.xiaomi.channel.commonutils.network.d.k(this.r);
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = z3;
                break;
            }
            String next = it.next();
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.b++;
            try {
                com.xiaomi.channel.commonutils.logger.b.a("begin to connect to " + next);
                this.p = s();
                this.p.connect(Host.b(next, i), 8000);
                com.xiaomi.channel.commonutils.logger.b.a("tcp connected");
                this.p.setTcpNoDelay(true);
                this.v = next;
                b();
                z = true;
                try {
                    this.c = System.currentTimeMillis() - jCurrentTimeMillis;
                    this.k = strK;
                    if (fallbackB != null) {
                        fallbackB.b(next, this.c, 0L);
                    }
                    this.u = SystemClock.elapsedRealtime();
                    com.xiaomi.channel.commonutils.logger.b.a("connected to " + next + " in " + this.c);
                    break;
                } catch (l e) {
                    e = e;
                    if (fallbackB != null) {
                        try {
                            fallbackB.b(next, System.currentTimeMillis() - jCurrentTimeMillis, 0L, e);
                        } catch (Throwable th) {
                            th = th;
                            if (!z) {
                                com.xiaomi.stats.h.a(next, this.o);
                            }
                            throw th;
                        }
                    }
                    this.o = e;
                    com.xiaomi.channel.commonutils.logger.b.d("SMACK: Could not connect to:" + next);
                    sb.append("SMACK: Could not connect to ").append(next).append(" port:").append(i).append(" ").append(e.getMessage()).append("\n");
                    if (!z) {
                        com.xiaomi.stats.h.a(next, this.o);
                        if (!TextUtils.equals(strK, com.xiaomi.channel.commonutils.network.d.k(this.r))) {
                            HostManager.getInstance().persist();
                            if (!z) {
                                throw new l(sb.toString());
                            }
                        }
                    }
                    z2 = z;
                    z3 = z2;
                } catch (IOException e2) {
                    e = e2;
                    if (fallbackB != null) {
                        fallbackB.b(next, System.currentTimeMillis() - jCurrentTimeMillis, 0L, e);
                    }
                    this.o = e;
                    com.xiaomi.channel.commonutils.logger.b.d("SMACK: Could not connect to:" + next);
                    sb.append("SMACK: Could not connect to ").append(next).append(" port:").append(i).append(" ").append(e.getMessage()).append("\n");
                    if (!z) {
                        com.xiaomi.stats.h.a(next, this.o);
                        if (!TextUtils.equals(strK, com.xiaomi.channel.commonutils.network.d.k(this.r))) {
                            break;
                        }
                    }
                    z2 = z;
                    z3 = z2;
                } catch (Throwable th2) {
                    th = th2;
                    z3 = true;
                    try {
                        this.o = new Exception("abnormal exception", th);
                        com.xiaomi.channel.commonutils.logger.b.a(th);
                        if (!z3) {
                            com.xiaomi.stats.h.a(next, this.o);
                            if (!TextUtils.equals(strK, com.xiaomi.channel.commonutils.network.d.k(this.r))) {
                                z = z3;
                                break;
                            }
                        }
                        z2 = z3;
                        z3 = z2;
                    } catch (Throwable th3) {
                        th = th3;
                        z = z3;
                        if (!z) {
                            com.xiaomi.stats.h.a(next, this.o);
                        }
                        throw th;
                    }
                }
            } catch (l e3) {
                e = e3;
                z = z3;
            } catch (IOException e4) {
                e = e4;
                z = z3;
            } catch (Throwable th4) {
                th = th4;
            }
            z3 = z2;
        }
        HostManager.getInstance().persist();
        if (!z) {
            throw new l(sb.toString());
        }
    }

    protected synchronized void a(int i, Exception exc) {
        if (m() != 2) {
            a(2, i, exc);
            this.j = "";
            try {
                this.p.close();
            } catch (Throwable th) {
            }
            this.s = 0L;
            this.t = 0L;
        }
    }

    protected void a(Exception exc) {
        if (SystemClock.elapsedRealtime() - this.u >= 300000) {
            this.w = 0;
            return;
        }
        if (com.xiaomi.channel.commonutils.network.d.d(this.r)) {
            this.w++;
            if (this.w >= 2) {
                String strD = d();
                com.xiaomi.channel.commonutils.logger.b.a("max short conn time reached, sink down current host:" + strD);
                a(strD, 0L, exc);
                this.w = 0;
            }
        }
    }

    protected void a(String str, long j, Exception exc) {
        Fallback fallbacksByHost = HostManager.getInstance().getFallbacksByHost(b.b(), false);
        if (fallbacksByHost != null) {
            fallbacksByHost.b(str, j, 0L, exc);
            HostManager.getInstance().persist();
        }
    }

    protected abstract void a(boolean z);

    @Override // com.xiaomi.smack.a
    public void a(com.xiaomi.slim.b[] bVarArr) throws l {
        throw new l("Don't support send Blob");
    }

    @Override // com.xiaomi.smack.a
    public void a(com.xiaomi.smack.packet.d[] dVarArr) {
        for (com.xiaomi.smack.packet.d dVar : dVarArr) {
            a(dVar);
        }
    }

    Fallback b(String str) {
        Fallback fallbacksByHost = HostManager.getInstance().getFallbacksByHost(str, false);
        if (!fallbacksByHost.b()) {
            com.xiaomi.smack.util.e.a(new k(this, str));
        }
        this.f = 0;
        try {
            byte[] address = InetAddress.getByName(fallbacksByHost.f).getAddress();
            this.f = address[0] & 255;
            this.f |= (address[1] << 8) & 65280;
            this.f |= (address[2] << 16) & 16711680;
            this.f = ((address[3] << 24) & (-16777216)) | this.f;
        } catch (UnknownHostException e) {
        }
        return fallbacksByHost;
    }

    protected synchronized void b() {
    }

    @Override // com.xiaomi.smack.a
    public void b(int i, Exception exc) {
        a(i, exc);
        if ((exc != null || i == 18) && this.u != 0) {
            a(exc);
        }
    }

    @Override // com.xiaomi.smack.a
    public void b(boolean z) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        a(z);
        if (z) {
            return;
        }
        this.r.a(new i(this, 13, jCurrentTimeMillis), 10000L);
    }

    public void c(int i, Exception exc) {
        this.r.a(new j(this, 2, i, exc));
    }

    @Override // com.xiaomi.smack.a
    public String d() {
        return this.v;
    }

    public String q() {
        return this.j;
    }

    public synchronized void r() {
        try {
            if (k() || j()) {
                com.xiaomi.channel.commonutils.logger.b.a("WARNING: current xmpp has connected");
            } else {
                a(0, 0, (Exception) null);
                a(this.m);
            }
        } catch (IOException e) {
            throw new l(e);
        }
    }

    public Socket s() {
        return new Socket();
    }

    public void t() {
        this.s = SystemClock.elapsedRealtime();
    }

    public void u() {
        this.t = SystemClock.elapsedRealtime();
    }
}
