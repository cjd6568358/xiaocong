package com.tencent.mid.a;

import com.tencent.mid.api.MidCallback;
import com.tencent.mid.api.MidEntity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class i implements MidCallback {
    final /* synthetic */ MidCallback a;

    i(MidCallback midCallback) {
        this.a = midCallback;
    }

    @Override // com.tencent.mid.api.MidCallback
    public void onFail(int i, String str) {
        h.a.f("failed to get mid, errorcode:" + i + " ,msg:" + str);
        this.a.onFail(i, str);
    }

    @Override // com.tencent.mid.api.MidCallback
    public void onSuccess(Object obj) {
        if (obj != null) {
            MidEntity midEntity = MidEntity.parse(obj.toString());
            h.a.h("success to get mid:" + midEntity.getMid());
            this.a.onSuccess(midEntity.getMid());
        }
    }
}
