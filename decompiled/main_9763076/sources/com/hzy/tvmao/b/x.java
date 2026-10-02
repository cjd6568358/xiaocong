package com.hzy.tvmao.b;

/* JADX INFO: compiled from: SDKControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class x extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ u e;
    private final /* synthetic */ short f;
    private final /* synthetic */ String g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    x(u uVar, a aVar, a.c cVar, String str, short s, String str2) {
        super(cVar, str);
        this.e = uVar;
        this.f = s;
        this.g = str2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        return new com.hzy.tvmao.b.a.a(com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g));
    }
}
