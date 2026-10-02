package com.tencent.android.tpush;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class aa implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ z b;

    aa(z zVar, Context context) {
        this.b = zVar;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            com.tencent.android.tpush.c.e.a(this.a).c();
        } catch (Throwable th) {
        }
    }
}
