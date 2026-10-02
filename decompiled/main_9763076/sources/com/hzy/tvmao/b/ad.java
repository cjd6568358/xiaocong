package com.hzy.tvmao.b;

import com.kookong.app.data.RcTestRemoteKeyListV3;

/* JADX INFO: compiled from: SingleKeyControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ad extends a.b {
    final /* synthetic */ ac a;
    private final /* synthetic */ String e;
    private final /* synthetic */ String f;
    private final /* synthetic */ String g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ad(ac acVar, a aVar, a.c cVar, String str, String str2, String str3, String str4) {
        super(cVar, str);
        this.a = acVar;
        this.e = str2;
        this.f = str3;
        this.g = str4;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<RcTestRemoteKeyListV3> iVarA = null;
        try {
            iVarA = com.hzy.tvmao.model.legacy.api.d.a(this.e, this.f, this.g);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new com.hzy.tvmao.b.a.a(iVarA);
    }
}
