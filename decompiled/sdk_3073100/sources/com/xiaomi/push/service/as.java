package com.xiaomi.push.service;

import com.xiaomi.smack.l;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class as extends XMPushService.h {
    private XMPushService b;
    private com.xiaomi.smack.packet.d c;
    private com.xiaomi.slim.b d;

    public as(XMPushService xMPushService, com.xiaomi.slim.b bVar) {
        super(4);
        this.b = null;
        this.b = xMPushService;
        this.d = bVar;
    }

    public as(XMPushService xMPushService, com.xiaomi.smack.packet.d dVar) {
        super(4);
        this.b = null;
        this.b = xMPushService;
        this.c = dVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        try {
            if (this.c != null) {
                this.b.a(this.c);
            } else {
                this.b.a(this.d);
            }
        } catch (l e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            this.b.a(10, e);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "send a message.";
    }
}
