package com.xiaocong.smarthome.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PackageInfoUtil {
    public static int getVersionCode(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo packInfo = getPackageInfo(context);
        return packInfo.versionCode;
    }

    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo packInfo = getPackageInfo(context);
        return packInfo.versionName;
    }

    private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packInfo;
    }
}
