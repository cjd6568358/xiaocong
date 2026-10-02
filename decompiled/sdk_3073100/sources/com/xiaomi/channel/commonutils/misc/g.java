package com.xiaomi.channel.commonutils.misc;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class g extends f.b {
    final /* synthetic */ String a;
    final /* synthetic */ f b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    g(f fVar, f.a aVar, String str) {
        super(aVar);
        this.b = fVar;
        this.a = str;
    }

    @Override // com.xiaomi.channel.commonutils.misc.f.b
    void a() {
        super.a();
    }

    @Override // com.xiaomi.channel.commonutils.misc.f.b
    void b() {
        this.b.e.edit().putLong(this.a, System.currentTimeMillis()).commit();
    }
}
