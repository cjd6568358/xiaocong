package com.hzy.tvmao.b;

/* JADX INFO: compiled from: SDKControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class aa extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ u e;
    private final /* synthetic */ String f;
    private final /* synthetic */ short g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    aa(u uVar, a aVar, a.c cVar, String str, String str2, short s) {
        super(cVar, str);
        this.e = uVar;
        this.f = str2;
        this.g = s;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        return new com.hzy.tvmao.b.a.a(com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g));
    }
}
