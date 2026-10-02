package com.tencent.android.tpush;

import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class t implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Context b;

    t(boolean z, Context context) {
        this.a = z;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.a) {
                com.tencent.android.tpush.a.a.a(2);
            } else {
                com.tencent.android.tpush.a.a.a(3);
            }
            com.tencent.android.tpush.common.n.b(this.b, "com.tencent.android.tpush.debug," + this.b.getPackageName(), this.a ? 1 : 0);
            Intent intent = new Intent("com.tencent.android.tpush.action.ENABLE_DEBUG.V3");
            intent.putExtra("debugMode", this.a);
            this.b.sendBroadcast(intent);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(XGPushConfig.a, "enableDebug ", th);
        }
    }
}
