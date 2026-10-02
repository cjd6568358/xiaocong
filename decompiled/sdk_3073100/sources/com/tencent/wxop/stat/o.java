package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class o implements Runnable {
    final /* synthetic */ Context a;

    o(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        StatServiceImpl.flushDataToDB(this.a);
    }
}
