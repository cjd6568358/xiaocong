package com.tencent.android.tpush.service;

import android.os.PowerManager;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class y {
    private static y a = null;
    private PowerManager.WakeLock b = null;

    private y() {
    }

    public static y a() {
        if (a == null) {
            a = new y();
        }
        return a;
    }

    public PowerManager.WakeLock b() {
        return this.b;
    }

    public void a(PowerManager.WakeLock wakeLock) {
        this.b = wakeLock;
    }
}
