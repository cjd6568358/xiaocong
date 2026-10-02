package com.hzy.tvmao.b;

import com.kookong.app.data.ChannelEpg;

/* JADX INFO: compiled from: SDKControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class z extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ u e;
    private final /* synthetic */ int f;
    private final /* synthetic */ String g;
    private final /* synthetic */ int h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    z(u uVar, a aVar, a.c cVar, String str, int i, String str2, int i2) {
        super(cVar, str);
        this.e = uVar;
        this.f = i;
        this.g = str2;
        this.h = i2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<ChannelEpg> iVarA = com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g, this.h);
        return new com.hzy.tvmao.b.a.a(iVarA.a, iVarA.b, iVarA.e);
    }
}
