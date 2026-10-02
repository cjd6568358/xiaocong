package com.tencent.android.tpush;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class w implements XGIOperateCallback {
    final /* synthetic */ Context a;

    w(Context context) {
        this.a = context;
    }

    @Override // com.tencent.android.tpush.XGIOperateCallback
    public void onSuccess(Object obj, int i) {
        XGPushManager.a(this.a);
    }

    @Override // com.tencent.android.tpush.XGIOperateCallback
    public void onFail(Object obj, int i, String str) {
        XGPushManager.a(this.a);
    }
}
