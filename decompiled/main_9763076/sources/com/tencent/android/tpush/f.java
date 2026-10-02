package com.tencent.android.tpush;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class f implements XGIOperateCallback {
    f() {
    }

    @Override // com.tencent.android.tpush.XGIOperateCallback
    public void onSuccess(Object obj, int i) {
        com.tencent.android.tpush.a.a.e(Constants.MSDK_TAG, "xg unregisterPush push onSuccess. flag:" + i);
    }

    @Override // com.tencent.android.tpush.XGIOperateCallback
    public void onFail(Object obj, int i, String str) {
        com.tencent.android.tpush.a.a.i(Constants.MSDK_TAG, "xg unregisterPush push onFail. token:" + obj + ", errCode:" + i + ",msg:" + str);
    }
}
