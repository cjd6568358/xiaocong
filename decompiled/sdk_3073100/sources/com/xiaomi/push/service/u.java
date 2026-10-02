package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.smack.l;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class u extends XMPushService.h {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ com.xiaomi.xmpush.thrift.ab c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    u(int i, XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar) {
        super(i);
        this.b = xMPushService;
        this.c = abVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        try {
            aa.a(this.b, s.a((Context) this.b, this.c));
        } catch (l e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            this.b.a(10, e);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "send ack message for message.";
    }
}
