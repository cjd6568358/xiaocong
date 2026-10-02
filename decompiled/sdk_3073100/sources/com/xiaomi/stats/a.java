package com.xiaomi.stats;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.ak;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class a implements ak.b.a {
    private XMPushService a;
    private ak.b b;
    private com.xiaomi.smack.a c;
    private int e;
    private boolean f = false;
    private ak.c d = ak.c.binding;

    a(XMPushService xMPushService, ak.b bVar) {
        this.a = xMPushService;
        this.b = bVar;
    }

    private void b() {
        this.b.b(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        b();
        if (this.f && this.e != 11) {
            com.xiaomi.push.thrift.b bVarF = f.a().f();
            switch (c.a[this.d.ordinal()]) {
                case 1:
                    if (this.e == 17) {
                        bVarF.b = com.xiaomi.push.thrift.a.BIND_TCP_READ_TIMEOUT.a();
                    } else if (this.e != 21) {
                        try {
                            d.a aVarC = d.c(f.b().a());
                            bVarF.b = aVarC.a.a();
                            bVarF.c(aVarC.b);
                        } catch (NullPointerException e) {
                            bVarF = null;
                        }
                    } else {
                        bVarF.b = com.xiaomi.push.thrift.a.BIND_TIMEOUT.a();
                    }
                    break;
                case 3:
                    bVarF.b = com.xiaomi.push.thrift.a.BIND_SUCCESS.a();
                    break;
            }
            if (bVarF != null) {
                bVarF.b(this.c.d());
                bVarF.d(this.b.b);
                bVarF.c = 1;
                try {
                    bVarF.a((byte) Integer.parseInt(this.b.h));
                } catch (NumberFormatException e2) {
                }
                f.a().a(bVarF);
            }
        }
    }

    void a() {
        this.b.a(this);
        this.c = this.a.h();
    }

    @Override // com.xiaomi.push.service.ak.b.a
    public void a(ak.c cVar, ak.c cVar2, int i) {
        if (!this.f && cVar == ak.c.binding) {
            this.d = cVar2;
            this.e = i;
            this.f = true;
        }
        this.a.a(new b(this, 4));
    }
}
