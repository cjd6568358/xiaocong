package com.xiaomi.slim;

import android.text.TextUtils;
import com.google.protobuf.micro.a;
import com.google.protobuf.micro.d;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.ak;
import com.xiaomi.smack.h;
import com.xiaomi.smack.l;
import com.xiaomi.smack.util.g;
import java.util.Iterator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends h {
    private Thread v;
    private c w;
    private d x;

    public f(XMPushService xMPushService, com.xiaomi.smack.b bVar) {
        super(xMPushService, bVar);
    }

    private b c(boolean z) {
        b bVar = new b();
        bVar.a("PING", (String) null);
        if (z) {
            bVar.a("1");
        } else {
            bVar.a(PushConstants.PUSH_TYPE_NOTIFY);
        }
        com.xiaomi.push.protobuf.b.j jVar = new com.xiaomi.push.protobuf.b.j();
        byte[] bArrA = c().a();
        if (bArrA != null) {
            try {
                jVar.a(com.xiaomi.push.protobuf.b.b.b(bArrA));
            } catch (d e) {
            }
        }
        byte[] bArrC = com.xiaomi.stats.h.c();
        if (bArrC != null) {
            jVar.a(a.a(bArrC));
        }
        bVar.a(jVar.c(), (String) null);
        return bVar;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.xiaomi.smack.l */
    private void v() throws l {
        try {
            this.w = new c(this.p.getInputStream(), this);
            this.x = new d(this.p.getOutputStream(), this);
            this.v = new g(this, "Blob Reader (" + this.l + ")");
            this.v.start();
        } catch (Exception e) {
            throw new l("Error to init reader and writer", e);
        }
    }

    protected synchronized void a(int i, Exception exc) {
        if (this.w != null) {
            this.w.b();
            this.w = null;
        }
        if (this.x != null) {
            try {
                this.x.b();
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
            }
            this.x = null;
        }
        super.a(i, exc);
    }

    public synchronized void a(ak.b bVar) {
        a.a(bVar, q(), this);
    }

    void a(b bVar) {
        if (bVar == null) {
            return;
        }
        if (bVar.d()) {
            com.xiaomi.channel.commonutils.logger.b.a("[Slim] RCV blob chid=" + bVar.c() + "; id=" + bVar.h() + "; errCode=" + bVar.e() + "; err=" + bVar.f());
        }
        if (bVar.c() == 0) {
            if ("PING".equals(bVar.a())) {
                com.xiaomi.channel.commonutils.logger.b.a("[Slim] RCV ping id=" + bVar.h());
                u();
            } else if ("CLOSE".equals(bVar.a())) {
                c(13, null);
            }
        }
        Iterator it = this.g.values().iterator();
        while (it.hasNext()) {
            ((com.xiaomi.smack.a.a) it.next()).a(bVar);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.xiaomi.smack.l */
    @Deprecated
    public void a(com.xiaomi.smack.packet.d dVar) throws l {
        b(b.a(dVar, (String) null));
    }

    public synchronized void a(String str, String str2) {
        a.a(str, str2, this);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.xiaomi.smack.l */
    protected void a(boolean z) throws l {
        if (this.x == null) {
            throw new l("The BlobWriter is null.");
        }
        b bVarC = c(z);
        com.xiaomi.channel.commonutils.logger.b.a("[Slim] SND ping id=" + bVarC.h());
        b(bVarC);
        t();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.xiaomi.smack.l */
    public void a(b[] bVarArr) throws l {
        for (b bVar : bVarArr) {
            b(bVar);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.xiaomi.smack.l */
    public void a(com.xiaomi.smack.packet.d[] dVarArr) throws l {
        for (com.xiaomi.smack.packet.d dVar : dVarArr) {
            a(dVar);
        }
    }

    public boolean a() {
        return true;
    }

    protected synchronized void b() {
        v();
        this.x.a();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.xiaomi.smack.l */
    public void b(b bVar) throws l {
        if (this.x == null) {
            throw new l("the writer is null.");
        }
        try {
            int iA = this.x.a(bVar);
            String strI = bVar.i();
            if (!TextUtils.isEmpty(strI)) {
                g.a(this.n, strI, iA, false, System.currentTimeMillis());
            }
            Iterator it = this.h.values().iterator();
            while (it.hasNext()) {
                ((com.xiaomi.smack.a.a) it.next()).a(bVar);
            }
        } catch (Exception e) {
            throw new l(e);
        }
    }

    void b(com.xiaomi.smack.packet.d dVar) {
        if (dVar == null) {
            return;
        }
        Iterator it = this.g.values().iterator();
        while (it.hasNext()) {
            ((com.xiaomi.smack.a.a) it.next()).a(dVar);
        }
    }
}
