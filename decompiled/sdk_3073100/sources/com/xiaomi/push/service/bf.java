package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.f;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bf implements ak.a {
    final /* synthetic */ XMPushService a;

    bf(XMPushService xMPushService) {
        this.a = xMPushService;
    }

    @Override // com.xiaomi.push.service.ak.a
    public void a() {
        this.a.n();
        if (ak.a().c() <= 0) {
            this.a.a(this.a.new f(12, null));
        }
    }
}
