package com.hzy.tvmao.b;

import com.kookong.app.data.IrDataList;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: IRDateControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ f e;
    private final /* synthetic */ int f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    k(f fVar, a aVar, a.c cVar, String str, int i) {
        super(cVar, str);
        this.e = fVar;
        this.f = i;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        com.hzy.tvmao.model.legacy.api.i<IrDataList> iVarA;
        Exception e;
        int i = 0;
        try {
            iVarA = com.hzy.tvmao.model.legacy.api.d.a(this.f, 5);
            try {
                i = iVarA.a;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
            }
        } catch (Exception e3) {
            iVarA = null;
            e = e3;
        }
        return new com.hzy.tvmao.b.a.a(i, Constants.MAIN_VERSION_TAG, iVarA.e);
    }
}
