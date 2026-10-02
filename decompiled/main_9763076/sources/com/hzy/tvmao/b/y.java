package com.hzy.tvmao.b;

import com.kookong.app.data.ProgramData;

/* JADX INFO: compiled from: SDKControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class y extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ u e;
    private final /* synthetic */ int f;
    private final /* synthetic */ String g;
    private final /* synthetic */ String h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    y(u uVar, a aVar, a.c cVar, String str, int i, String str2, String str3) {
        super(cVar, str);
        this.e = uVar;
        this.f = i;
        this.g = str2;
        this.h = str3;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<ProgramData> iVarC = com.hzy.tvmao.model.legacy.api.d.c(String.valueOf(this.f), this.g, this.h);
        return new com.hzy.tvmao.b.a.a(iVarC.a, iVarC.b, iVarC.e);
    }
}
