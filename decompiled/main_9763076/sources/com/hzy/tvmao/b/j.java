package com.hzy.tvmao.b;

import com.hzy.tvmao.utils.LogUtil;
import com.kookong.app.data.RemoteList;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: IRDateControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ f e;
    private final /* synthetic */ int f;
    private final /* synthetic */ int g;
    private final /* synthetic */ String h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    j(f fVar, a aVar, a.c cVar, String str, int i, int i2, String str2) {
        super(cVar, str);
        this.e = fVar;
        this.f = i;
        this.g = i2;
        this.h = str2;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        try {
            com.hzy.tvmao.model.legacy.api.i<RemoteList> iVarB = com.hzy.tvmao.model.legacy.api.d.b(this.f, this.g, this.h);
            LogUtil.d("TestIRcodelist==" + iVarB.a);
            return new com.hzy.tvmao.b.a.a(iVarB.a, Constants.MAIN_VERSION_TAG, iVarB.e);
        } catch (Exception e) {
            return null;
        }
    }
}
