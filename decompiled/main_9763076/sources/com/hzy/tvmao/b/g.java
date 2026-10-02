package com.hzy.tvmao.b;

import com.kookong.app.data.RemoteList;

/* JADX INFO: compiled from: IRDateControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class g extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ f e;
    private final /* synthetic */ int f;
    private final /* synthetic */ int g;
    private final /* synthetic */ int h;
    private final /* synthetic */ int i;
    private final /* synthetic */ String j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    g(f fVar, a aVar, a.c cVar, String str, int i, int i2, int i3, int i4, String str2) {
        super(cVar, str);
        this.e = fVar;
        this.f = i;
        this.g = i2;
        this.h = i3;
        this.i = i4;
        this.j = str2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        try {
            com.hzy.tvmao.model.legacy.api.i<RemoteList> iVarA = com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g, this.h, this.i, this.j);
            return new com.hzy.tvmao.b.a.a(iVarA.a, iVarA.b, iVarA.e);
        } catch (Exception e) {
            return null;
        }
    }
}
