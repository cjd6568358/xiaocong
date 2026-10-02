package com.baidu.location.a;

import android.location.Location;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e implements Runnable {
    final /* synthetic */ Location a;
    final /* synthetic */ d b;

    e(d dVar, Location location) {
        this.b = dVar;
        this.a = location;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a);
    }
}
