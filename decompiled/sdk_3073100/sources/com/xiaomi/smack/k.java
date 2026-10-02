package com.xiaomi.smack;

import com.xiaomi.network.HostManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class k implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ h b;

    k(h hVar, String str) {
        this.b = hVar;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        HostManager.getInstance().getFallbacksByHost(this.a, true);
    }
}
