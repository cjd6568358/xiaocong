package com.xiaomi.push.service;

import com.xiaomi.smack.l;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class a extends XMPushService.h {
    private XMPushService b;
    private com.xiaomi.smack.packet.c[] c;
    private com.xiaomi.slim.b[] d;

    public a(XMPushService xMPushService, com.xiaomi.slim.b[] bVarArr) {
        super(4);
        this.b = null;
        this.b = xMPushService;
        this.d = bVarArr;
    }

    public a(XMPushService xMPushService, com.xiaomi.smack.packet.c[] cVarArr) {
        super(4);
        this.b = null;
        this.b = xMPushService;
        this.c = cVarArr;
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
        return "batch send message.";
    }
}
