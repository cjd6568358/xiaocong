package com.xiaomi.push.service;

import android.util.Base64;
import com.xiaomi.network.HttpUtils;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class au extends com.xiaomi.channel.commonutils.misc.h.b {
    boolean a = false;
    final /* synthetic */ at b;

    au(at atVar) {
        this.b = atVar;
    }

    @Override // com.xiaomi.channel.commonutils.misc.h.b
    public void b() {
        try {
            com.xiaomi.push.protobuf.a.C0011a c0011aB = com.xiaomi.push.protobuf.a.C0011a.b(Base64.decode(HttpUtils.a(com.xiaomi.channel.commonutils.android.j.a(), "http://resolver.msg.xiaomi.net/psc/?t=a", (List<com.xiaomi.channel.commonutils.network.c>) null), 10));
            if (c0011aB != null) {
                this.b.c = c0011aB;
                this.a = true;
                this.b.i();
            }
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("fetch config failure: " + e.getMessage());
        }
    }

    @Override // com.xiaomi.channel.commonutils.misc.h.b
    public void c() {
        at.a[] aVarArr;
        this.b.d = null;
        if (this.a) {
            synchronized (this.b) {
                aVarArr = (at.a[]) this.b.b.toArray(new at.a[this.b.b.size()]);
            }
            for (at.a aVar : aVarArr) {
                aVar.a(this.b.c);
            }
        }
    }
}
