package com.facebook.react.common;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SystemClock {
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static long nanoTime() {
        return System.nanoTime();
    }

    public static long uptimeMillis() {
        return android.os.SystemClock.uptimeMillis();
    }
}
