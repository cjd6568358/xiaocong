package com.xiaomi.push.service;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bl extends XMPushService.h {
    final /* synthetic */ XMPushService b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bl(XMPushService xMPushService, int i) {
        super(i);
        this.b = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        if (this.b.i != null) {
            this.b.i.h();
            this.b.i = null;
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "disconnect for disable push";
    }
}
