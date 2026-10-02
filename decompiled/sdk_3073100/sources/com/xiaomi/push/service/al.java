package com.xiaomi.push.service;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class al implements ak.b.a {
    final /* synthetic */ ak.b a;

    al(ak.b bVar) {
        this.a = bVar;
    }

    @Override // com.xiaomi.push.service.ak.b.a
    public void a(ak.c cVar, ak.c cVar2, int i) {
        if (cVar2 == ak.c.binding) {
            this.a.p.a(this.a.q, 60000L);
        } else {
            this.a.p.b(this.a.q);
        }
    }
}
