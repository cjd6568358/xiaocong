package com.xiaomi.push.service.receivers;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class a implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ NetworkStatusReceiver b;

    a(NetworkStatusReceiver networkStatusReceiver, Context context) {
        this.b = networkStatusReceiver;
        this.a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a(this.a);
    }
}
