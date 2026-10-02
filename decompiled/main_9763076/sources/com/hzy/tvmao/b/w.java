package com.hzy.tvmao.b;

import com.hzy.tvmao.model.legacy.api.data.EPGProgramData;
import com.kookong.app.data.PlayingTimeData;

/* JADX INFO: compiled from: SDKControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class w extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ u e;
    private final /* synthetic */ short f;
    private final /* synthetic */ String g;
    private final /* synthetic */ int h;
    private final /* synthetic */ String i;
    private final /* synthetic */ boolean j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    w(u uVar, a aVar, a.c cVar, String str, short s, String str2, int i, String str3, boolean z) {
        super(cVar, str);
        this.e = uVar;
        this.f = s;
        this.g = str2;
        this.h = i;
        this.i = str3;
        this.j = z;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<PlayingTimeData> iVarA = com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g, this.h, this.i, this.j);
        EPGProgramData ePGProgramData = new EPGProgramData();
        com.hzy.tvmao.model.legacy.api.a.a(((PlayingTimeData) iVarA.e).tvs, ePGProgramData);
        return new com.hzy.tvmao.b.a.a(iVarA.a, iVarA.b, ePGProgramData);
    }
}
