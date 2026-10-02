package com.tencent.android.tpush;

import android.content.Context;
import com.tencent.android.tpush.horse.Tools;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class d implements Runnable {
    final /* synthetic */ Context a;

    d(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!com.tencent.android.tpush.service.e.m.b(com.tencent.android.tpush.common.n.a(this.a, XGPush4Msdk.b(this.a), (String) null))) {
            com.tencent.android.tpush.common.n.a(this.a, XGPush4Msdk.b(this.a));
            Tools.clearCacheServerItems(this.a);
            Tools.clearOptStrategyItem(this.a);
        }
    }
}
