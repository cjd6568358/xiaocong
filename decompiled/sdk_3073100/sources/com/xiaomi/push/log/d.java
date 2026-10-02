package com.xiaomi.push.log;

import com.xiaomi.channel.commonutils.misc.h;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class d extends h.b {
    h.b a;
    final /* synthetic */ b b;

    d(b bVar) {
        this.b = bVar;
    }

    @Override // com.xiaomi.channel.commonutils.misc.h.b
    public void b() {
        b.C0010b c0010b = (b.C0010b) this.b.a.peek();
        if (c0010b == null || !c0010b.d()) {
            return;
        }
        this.a = (h.b) this.b.a.remove();
        this.a.b();
    }

    @Override // com.xiaomi.channel.commonutils.misc.h.b
    public void c() {
        if (this.a != null) {
            this.a.c();
        }
    }
}
