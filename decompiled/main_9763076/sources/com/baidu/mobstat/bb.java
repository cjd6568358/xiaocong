package com.baidu.mobstat;

import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class bb {
    public static boolean a = true;
    public static final String b;
    public static final String c;

    static {
        b = Build.VERSION.SDK_INT < 9 ? "http://datax.baidu.com/xs.gif" : "https://datax.baidu.com/xs.gif";
        c = Build.VERSION.SDK_INT < 9 ? "http://dxp.baidu.com/upgrade" : "https://dxp.baidu.com/upgrade";
    }
}
