package com.tencent.mid.a;

import com.tencent.mid.api.MidCallback;
import com.tencent.mid.api.MidEntity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class j implements MidCallback {
    j() {
    }

    @Override // com.tencent.mid.api.MidCallback
    public void onFail(int i, String str) {
        h.a.f("failed to get mid, errorcode:" + i + " ,msg:" + str);
    }

    @Override // com.tencent.mid.api.MidCallback
    public void onSuccess(Object obj) {
        if (obj != null) {
            h.a.h("success to get mid:" + MidEntity.parse(obj.toString()).getMid());
        }
    }
}
