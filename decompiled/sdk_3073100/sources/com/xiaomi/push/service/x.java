package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.smack.l;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class x extends XMPushService.h {
    final /* synthetic */ XMPushService b;
    final /* synthetic */ com.xiaomi.xmpush.thrift.ab c;
    final /* synthetic */ String d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    x(int i, XMPushService xMPushService, com.xiaomi.xmpush.thrift.ab abVar, String str) {
        super(i);
        this.b = xMPushService;
        this.c = abVar;
        this.d = str;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        try {
            com.xiaomi.xmpush.thrift.ab abVarA = s.a((Context) this.b, this.c);
            abVarA.m().a("absent_target_package", this.d);
            aa.a(this.b, abVarA);
        } catch (l e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            this.b.a(10, e);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "send app absent ack message for message.";
    }
}
