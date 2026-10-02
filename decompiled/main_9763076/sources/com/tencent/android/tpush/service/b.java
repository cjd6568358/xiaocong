package com.tencent.android.tpush.service;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ a b;

    b(a aVar, Context context) {
        this.b = aVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.tencent.android.tpush.common.t.e(this.a);
    }
}
