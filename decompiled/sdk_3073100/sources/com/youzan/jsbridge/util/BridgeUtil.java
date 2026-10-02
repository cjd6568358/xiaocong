package com.youzan.jsbridge.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class BridgeUtil {
    public static boolean sShouldInjectJs = false;

    public static String getDataPath(Context context) {
        PackageManager m = context.getPackageManager();
        String s = context.getPackageName();
        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            return p.applicationInfo.dataDir;
        } catch (Exception e) {
            Logger.e("Error Package name not found ");
            return s;
        }
    }

    public static boolean shouldInjectJs() {
        return sShouldInjectJs || Build.VERSION.SDK_INT < 17;
    }
}
