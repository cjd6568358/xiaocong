package com.xiaomi.push.service;

import com.xiaomi.smack.l;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bm extends XMPushService.h {
    final /* synthetic */ String b;
    final /* synthetic */ byte[] c;
    final /* synthetic */ XMPushService d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bm(XMPushService xMPushService, int i, String str, byte[] bArr) {
        super(i);
        this.d = xMPushService;
        this.b = str;
        this.c = bArr;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        try {
            aa.a(this.d, this.b, this.c);
        } catch (l e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            this.d.a(10, e);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "send mi push message";
    }
}
