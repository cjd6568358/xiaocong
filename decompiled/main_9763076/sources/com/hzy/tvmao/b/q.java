package com.hzy.tvmao.b;

import com.kookong.app.data.SpList;

/* JADX INFO: compiled from: OperaterControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class q extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ o e;
    private final /* synthetic */ int f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    q(o oVar, a aVar, a.c cVar, String str, int i) {
        super(cVar, str);
        this.e = oVar;
        this.f = i;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        try {
            com.hzy.tvmao.model.legacy.api.i<SpList> iVarB = com.hzy.tvmao.model.legacy.api.d.b(this.f);
            return new com.hzy.tvmao.b.a.a(iVarB.a, iVarB.b, iVarB.e);
        } catch (Exception e) {
            return null;
        }
    }
}
