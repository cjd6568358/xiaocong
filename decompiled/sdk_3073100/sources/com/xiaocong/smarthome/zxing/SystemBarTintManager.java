package com.xiaocong.smarthome.zxing;

import android.os.Build;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SystemBarTintManager {
    private static String sNavBarOverride;

    static {
        if (Build.VERSION.SDK_INT >= 19) {
            try {
                Method m = Class.forName("android.os.SystemProperties").getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable th) {
                sNavBarOverride = null;
            }
        }
    }
}
