package com.xiaomi.push.service;

import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bd extends com.xiaomi.smack.b {
    final /* synthetic */ XMPushService a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bd(XMPushService xMPushService, Map map, int i, String str, com.xiaomi.smack.e eVar) {
        super(map, i, str, eVar);
        this.a = xMPushService;
    }

    @Override // com.xiaomi.smack.b
    public byte[] a() {
        try {
            com.xiaomi.push.protobuf.b.C0012b c0012b = new com.xiaomi.push.protobuf.b.C0012b();
            c0012b.a(at.a().c());
            return c0012b.c();
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a("getOBBString err: " + e.toString());
            return null;
        }
    }
}
