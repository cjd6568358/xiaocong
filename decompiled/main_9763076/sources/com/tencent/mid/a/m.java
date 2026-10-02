package com.tencent.mid.a;

import com.tencent.mid.api.MidCallback;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class m implements MidCallback {
    final /* synthetic */ k a;

    m(k kVar) {
        this.a = kVar;
    }

    @Override // com.tencent.mid.api.MidCallback
    public void onFail(int i, String str) {
        this.a.d.b("checkServer failed, errCode:" + i + ",msg:" + str);
    }

    @Override // com.tencent.mid.api.MidCallback
    public void onSuccess(Object obj) {
        this.a.d.b("checkServer success:" + obj);
    }
}
