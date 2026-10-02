package com.hzy.tvmao.b;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: SDKControl.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class v extends a.AbstractAsyncTaskC0021a {
    final /* synthetic */ u e;
    private final /* synthetic */ int f;
    private final /* synthetic */ int g;
    private final /* synthetic */ String h;
    private final /* synthetic */ String i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    v(u uVar, a aVar, a.c cVar, String str, int i, int i2, String str2, String str3) {
        super(cVar, str);
        this.e = uVar;
        this.f = i;
        this.g = i2;
        this.h = str2;
        this.i = str3;
    }

    @Override // com.hzy.tvmao.b.a.b
    protected com.hzy.tvmao.b.a.a b() {
        return new com.hzy.tvmao.b.a.a(1, Constants.MAIN_VERSION_TAG, com.hzy.tvmao.model.legacy.api.d.a(this.f, this.g, this.h, this.i).e);
    }
}
