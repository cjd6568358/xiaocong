package com.xiaomi.stats;

import com.xiaomi.push.service.XMPushService;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class b extends XMPushService.h {
    final /* synthetic */ a b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    b(a aVar, int i) {
        super(i);
        this.b = aVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        this.b.c();
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "Handling bind stats";
    }
}
