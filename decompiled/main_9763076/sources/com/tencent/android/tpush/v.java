package com.tencent.android.tpush;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class v implements Runnable {
    final /* synthetic */ Context a;

    v(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tencent.android.tpush.b.d.a().c(this.a);
    }
}
