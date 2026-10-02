package com.hzy.tvmao.b;

/* JADX INFO: compiled from: LineupControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class m extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ l e;
    private final /* synthetic */ int f;
    private final /* synthetic */ int g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    m(l lVar, a aVar, a.c cVar, String str, int i, int i2) {
        super(cVar, str);
        this.e = lVar;
        this.f = i;
        this.g = i2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        return new com.hzy.tvmao.b.a.a(com.hzy.tvmao.model.legacy.api.d.b(this.f, this.g));
    }
}
