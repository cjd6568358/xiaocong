package com.tencent.android.tpush.common;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class u implements Runnable {
    final /* synthetic */ Context a;

    u(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (t.c(this.a) != 1) {
            try {
                com.tencent.android.tpush.service.n.a(this.a);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }
}
