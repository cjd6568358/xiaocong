package com.tencent.android.tpush.service;

import com.tencent.android.tpush.XGPushConfig;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class z implements Runnable {
    final /* synthetic */ XGPushServiceV3 a;

    z(XGPushServiceV3 xGPushServiceV3) {
        this.a = xGPushServiceV3;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z = com.tencent.android.tpush.common.n.a(this.a.getApplicationContext(), new StringBuilder().append("com.tencent.android.tpush.debug,").append(this.a.getApplicationContext().getPackageName()).toString(), 0) == 1;
        XGPushConfig.enableDebug = true;
        if (z) {
            com.tencent.android.tpush.a.a.a(2);
        } else {
            com.tencent.android.tpush.a.a.a(3);
        }
    }
}
