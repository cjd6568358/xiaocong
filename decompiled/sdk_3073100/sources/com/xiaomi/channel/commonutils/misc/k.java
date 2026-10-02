package com.xiaomi.channel.commonutils.misc;

import android.os.Looper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class k {
    public static void a(boolean z) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread() && z) {
            throw new RuntimeException("can't do this on ui thread when debug_switch:" + z);
        }
        if (Looper.getMainLooper().getThread() != Thread.currentThread() || z) {
            return;
        }
        com.xiaomi.channel.commonutils.logger.b.a("can't do this on ui thread when debug_switch:" + z);
    }
}
