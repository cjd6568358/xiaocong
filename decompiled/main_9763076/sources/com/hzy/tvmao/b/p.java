package com.hzy.tvmao.b;

/* JADX INFO: compiled from: OperaterControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class p extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ o e;
    private final /* synthetic */ String f;
    private final /* synthetic */ String g;
    private final /* synthetic */ String h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    p(o oVar, a aVar, a.c cVar, String str, String str2, String str3, String str4) {
        super(cVar, str);
        this.e = oVar;
        this.f = str2;
        this.g = str3;
        this.h = str4;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        try {
            com.hzy.tvmao.model.legacy.api.i<Integer> iVarB = com.hzy.tvmao.model.legacy.api.d.b(this.f, this.g, this.h);
            return new com.hzy.tvmao.b.a.a(iVarB.a, iVarB.b, iVarB.e);
        } catch (Exception e) {
            return null;
        }
    }
}
