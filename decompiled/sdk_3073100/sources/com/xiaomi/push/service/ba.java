package com.xiaomi.push.service;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class ba extends XMPushService.h {
    final /* synthetic */ XMPushService b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ba(XMPushService xMPushService, int i) {
        super(i);
        this.b = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        if (this.b.i != null) {
            this.b.i.b(15, (Exception) null);
            this.b.i = null;
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "disconnect for service destroy.";
    }
}
