package com.xiaomi.push.service;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class ab implements ak.b.a {
    final /* synthetic */ XMPushService a;

    ab(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // com.xiaomi.push.service.ak.b.a
    public void a(ak.c cVar, ak.c cVar2, int i) {
        if (cVar2 == ak.c.binded) {
            r.a(this.a);
            r.b(this.a);
        } else if (cVar2 == ak.c.unbind) {
            r.a(this.a, 70000001, " the push is not connected.");
        }
    }
}
