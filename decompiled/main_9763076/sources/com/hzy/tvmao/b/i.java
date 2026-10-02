package com.hzy.tvmao.b;

import com.kookong.app.data.IrDataList;

/* JADX INFO: compiled from: IRDateControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class i extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ f e;
    private final /* synthetic */ String f;
    private final /* synthetic */ int g;
    private final /* synthetic */ String h;
    private final /* synthetic */ boolean i;
    private final /* synthetic */ boolean j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    i(f fVar, a aVar, a.c cVar, String str, String str2, int i, String str3, boolean z, boolean z2) {
        super(cVar, str);
        this.e = fVar;
        this.f = str2;
        this.g = i;
        this.h = str3;
        this.i = z;
        this.j = z2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        try {
            com.hzy.tvmao.model.legacy.api.i<IrDataList> iVarA = com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g, this.h, this.i, this.j);
            return new com.hzy.tvmao.b.a.a(iVarA.a, iVarA.b, iVarA.e);
        } catch (Exception e) {
            return null;
        }
    }
}
