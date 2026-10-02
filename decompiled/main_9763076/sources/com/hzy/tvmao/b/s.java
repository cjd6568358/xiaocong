package com.hzy.tvmao.b;

import com.kookong.app.data.StbList;

/* JADX INFO: compiled from: OperaterControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class s extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ o e;
    private final /* synthetic */ String f;
    private final /* synthetic */ int g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    s(o oVar, a aVar, a.c cVar, String str, String str2, int i) {
        super(cVar, str);
        this.e = oVar;
        this.f = str2;
        this.g = i;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        try {
            com.hzy.tvmao.model.legacy.api.i<StbList> iVarA = com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g);
            return new com.hzy.tvmao.b.a.a(iVarA.a, iVarA.b, iVarA.e);
        } catch (Exception e) {
            return null;
        }
    }
}
