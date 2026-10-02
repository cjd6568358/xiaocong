package com.xiaomi.smack;

import com.xiaomi.push.service.XMPushService;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class i extends XMPushService.h {
    final /* synthetic */ long b;
    final /* synthetic */ h c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    i(h hVar, int i, long j) {
        super(i);
        this.c = hVar;
        this.b = j;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        Thread.yield();
        if (!this.c.k() || this.c.a(this.b)) {
            return;
        }
        this.c.r.a(22, (Exception) null);
        this.c.r.a(true);
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "check the ping-pong." + this.b;
    }
}
