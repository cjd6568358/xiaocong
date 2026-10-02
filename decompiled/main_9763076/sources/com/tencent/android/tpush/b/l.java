package com.tencent.android.tpush.b;

import android.content.Intent;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class l implements Runnable {
    final /* synthetic */ i a;

    l(i iVar) {
        this.a = iVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        ArrayList arrayListA;
        if (this.a.d != null && !com.tencent.android.tpush.service.e.m.b(this.a.d.getPackageName()) && (arrayListA = d.a().a(this.a.d)) != null && arrayListA.size() > 0) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.c(i.b, "Action -> trySendCachedMsg with CachedMsgList size = " + arrayListA.size());
            }
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 < arrayListA.size()) {
                    try {
                        this.a.c((Intent) arrayListA.get(i2));
                    } catch (Exception e) {
                        com.tencent.android.tpush.a.a.c(i.b, Constants.MAIN_VERSION_TAG, e);
                    }
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
    }
}
