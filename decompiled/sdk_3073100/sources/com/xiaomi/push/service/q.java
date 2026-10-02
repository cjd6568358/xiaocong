package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.a;
import com.xiaomi.smack.l;
import java.io.IOException;
import java.util.Collection;
import org.json.JSONException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class q extends XMPushService.h {
    private XMPushService b;
    private byte[] c;
    private String d;
    private String e;
    private String f;

    public q(XMPushService xMPushService, String str, String str2, String str3, byte[] bArr) {
        super(9);
        this.b = xMPushService;
        this.d = str;
        this.c = bArr;
        this.e = str2;
        this.f = str3;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        n nVarA;
        ak.b next;
        n nVarA2 = o.a(this.b);
        if (nVarA2 == null) {
            try {
                nVarA = o.a(this.b, this.d, this.e, this.f);
            } catch (IOException e) {
                com.xiaomi.channel.commonutils.logger.b.a(e);
                nVarA = nVarA2;
            } catch (JSONException e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                nVarA = nVarA2;
            }
        } else {
            nVarA = nVarA2;
        }
        if (nVarA == null) {
            com.xiaomi.channel.commonutils.logger.b.d("no account for mipush");
            r.a(this.b, 70000002, "no account.");
            return;
        }
        Collection<ak.b> collectionC = ak.a().c("5");
        if (collectionC.isEmpty()) {
            next = nVarA.a(this.b);
            aa.a(this.b, next);
            ak.a().a(next);
        } else {
            next = collectionC.iterator().next();
        }
        if (!this.b.f()) {
            this.b.a(true);
            return;
        }
        try {
            if (next.m == ak.c.binded) {
                aa.a(this.b, this.d, this.c);
            } else if (next.m == ak.c.unbind) {
                XMPushService xMPushService = this.b;
                XMPushService xMPushService2 = this.b;
                xMPushService2.getClass();
                xMPushService.a(xMPushService2.new a(next));
            }
        } catch (l e3) {
            com.xiaomi.channel.commonutils.logger.b.a(e3);
            this.b.a(10, e3);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "register app";
    }
}
