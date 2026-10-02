package com.meizu.cloud.pushsdk.util;

import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public static boolean a() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean b() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean c() {
        return Build.VERSION.SDK_INT >= 19;
    }
}
