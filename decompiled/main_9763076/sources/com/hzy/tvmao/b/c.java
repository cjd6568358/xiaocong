package com.hzy.tvmao.b;

import com.kookong.app.data.BrandList;

/* JADX INFO: compiled from: BrandListControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ b e;
    private final /* synthetic */ int f;
    private final /* synthetic */ String g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    c(b bVar, a aVar, a.c cVar, String str, int i, String str2) {
        super(cVar, str);
        this.e = bVar;
        this.f = i;
        this.g = str2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        try {
            com.hzy.tvmao.model.legacy.api.i<BrandList> iVarA = com.hzy.tvmao.model.legacy.api.d.a(String.valueOf(this.f), this.g);
            return new com.hzy.tvmao.b.a.a(iVarA.a, iVarA.b, iVarA.e);
        } catch (Exception e) {
            return null;
        }
    }
}
