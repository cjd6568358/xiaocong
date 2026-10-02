package com.tencent.android.tpush;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class af implements XGIOperateCallback {
    af() {
    }

    @Override // com.tencent.android.tpush.XGIOperateCallback
    public void onSuccess(Object obj, int i) {
        com.tencent.android.tpush.a.a.f(XGPushManager.a, "XG deleteAllAccout  success with token = " + obj);
    }

    @Override // com.tencent.android.tpush.XGIOperateCallback
    public void onFail(Object obj, int i, String str) {
        com.tencent.android.tpush.a.a.j(XGPushManager.a, "XG deleteAllAccout failed with token = " + obj + ", errCode =  " + i + ", msg = " + str);
    }
}
