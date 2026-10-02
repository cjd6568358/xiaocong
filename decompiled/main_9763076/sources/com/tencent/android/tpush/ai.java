package com.tencent.android.tpush;

import android.app.Activity;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class ai implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ Intent b;

    ai(Activity activity, Intent intent) {
        this.a = activity;
        this.b = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        XGPushManager.a(this.a, this.b);
        XGPushManager.c(this.a, this.b);
    }
}
