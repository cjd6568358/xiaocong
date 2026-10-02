package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.c;
import com.xiaomi.push.service.XMPushService.k;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class az implements com.xiaomi.smack.f {
    final /* synthetic */ XMPushService a;

    az(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // com.xiaomi.smack.f
    public void a(com.xiaomi.slim.b bVar) {
        this.a.a(this.a.new c(bVar));
    }

    @Override // com.xiaomi.smack.f
    public void b(com.xiaomi.smack.packet.d dVar) {
        this.a.a(this.a.new k(dVar));
    }
}
