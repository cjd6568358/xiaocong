package com.hzy.tvmao.b;

import com.kookong.app.data.ProgramData;

/* JADX INFO: compiled from: TVWallDataControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class af extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ ae e;
    private final /* synthetic */ int f;
    private final /* synthetic */ String g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    af(ae aeVar, a aVar, a.c cVar, String str, int i, String str2) {
        super(cVar, str);
        this.e = aeVar;
        this.f = i;
        this.g = str2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<ProgramData> iVarC = com.hzy.tvmao.model.legacy.api.d.c(String.valueOf(this.f), this.g, null);
        return new com.hzy.tvmao.b.a.a(iVarC.a, iVarC.b, com.hzy.tvmao.model.legacy.api.l.a((ProgramData) iVarC.e));
    }
}
