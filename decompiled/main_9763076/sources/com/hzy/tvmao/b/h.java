package com.hzy.tvmao.b;

import com.kookong.app.data.RcTestRemoteKeyList;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: IRDateControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class h extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ f e;
    private final /* synthetic */ int f;
    private final /* synthetic */ int g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    h(f fVar, a aVar, a.c cVar, String str, int i, int i2) {
        super(cVar, str);
        this.e = fVar;
        this.f = i;
        this.g = i2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<RcTestRemoteKeyList> iVarD;
        Exception e;
        int i = 0;
        try {
            iVarD = com.hzy.tvmao.model.legacy.api.d.d(this.f, this.g);
            try {
                i = iVarD.a;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
            }
        } catch (Exception e3) {
            iVarD = null;
            e = e3;
        }
        return new com.hzy.tvmao.b.a.a(i, Constants.MAIN_VERSION_TAG, iVarD.e);
    }
}
