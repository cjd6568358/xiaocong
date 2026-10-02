package com.hzy.tvmao.b;

import com.kookong.app.data.api.LineupData;

/* JADX INFO: compiled from: LineupControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class n extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ l e;
    private final /* synthetic */ int f;
    private final /* synthetic */ int g;
    private final /* synthetic */ int h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    n(l lVar, a aVar, a.c cVar, String str, int i, int i2, int i3) {
        super(cVar, str);
        this.e = lVar;
        this.f = i;
        this.g = i2;
        this.h = i3;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<LineupData> iVarC = com.hzy.tvmao.model.legacy.api.d.c(this.f, this.g);
        if (iVarC.e != null) {
            com.hzy.tvmao.model.db.a.a.a().a(this.h, this.g, ((LineupData) iVarC.e).list);
        }
        this.e.a(this.h);
        return new com.hzy.tvmao.b.a.a(1, null, "Save lineup data whose lineupid is " + this.g + " success !");
    }
}
