package com.xiaomi.push.service;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class bk extends XMPushService.h {
    final /* synthetic */ int b;
    final /* synthetic */ byte[] c;
    final /* synthetic */ String d;
    final /* synthetic */ XMPushService e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bk(XMPushService xMPushService, int i, int i2, byte[] bArr, String str) {
        super(i);
        this.e = xMPushService;
        this.b = i2;
        this.c = bArr;
        this.d = str;
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public void a() {
        o.b(this.e);
        ak.a().a("5");
        com.xiaomi.channel.commonutils.misc.a.a(this.b);
        this.e.c.b(com.xiaomi.smack.b.b());
        this.e.a(this.c, this.d);
    }

    @Override // com.xiaomi.push.service.XMPushService.h
    public String b() {
        return "clear account cache.";
    }
}
