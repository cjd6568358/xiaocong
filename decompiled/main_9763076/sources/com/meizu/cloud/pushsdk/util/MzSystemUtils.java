package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.provider.Settings;
import android.text.TextUtils;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.common.b.h;
import com.tencent.android.tpush.common.Constants;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MzSystemUtils {
    private static String c(Context context, String str) {
        ServiceInfo[] serviceInfoArr;
        try {
            serviceInfoArr = context.getPackageManager().getPackageInfo(str, 4).services;
        } catch (PackageManager.NameNotFoundException e) {
            serviceInfoArr = null;
        }
        if (serviceInfoArr == null) {
            return null;
        }
        for (int i = 0; i < serviceInfoArr.length; i++) {
            if ("com.meizu.cloud.pushsdk.pushservice.MzPushService".equals(serviceInfoArr[i].name)) {
                return serviceInfoArr[i].processName;
            }
        }
        return null;
    }

    public static String a(Context context) {
        String packageName = context.getPackageName();
        try {
            String strC = c(context, "com.meizu.cloud");
            if (!TextUtils.isEmpty(strC) && strC.contains("mzservice_v1")) {
                return "com.meizu.cloud";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DebugLogger.i("SystemUtils", "startservice package name " + packageName);
        return packageName;
    }

    public static String a(Context context, String str) {
        try {
            String str2 = context.getPackageManager().getPackageInfo(str, 0).versionName;
            if (str2 == null || str2.length() <= 0) {
                return Constants.MAIN_VERSION_TAG;
            }
            return str2;
        } catch (Exception e) {
            DebugLogger.e("VersionInfo", "Exception message " + e.getMessage());
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static boolean a(String str, String str2) {
        String[] strArrSplit = str.split("\\.");
        String[] strArrSplit2 = str2.split("\\.");
        int iMin = Math.min(strArrSplit.length, strArrSplit2.length);
        int length = 0;
        for (int i = 0; i < iMin; i++) {
            length = strArrSplit[i].length() - strArrSplit2[i].length();
            if (length != 0 || (length = strArrSplit[i].compareTo(strArrSplit2[i])) != 0) {
                break;
            }
        }
        if (length == 0) {
            length = strArrSplit.length - strArrSplit2.length;
        }
        return length >= 0;
    }

    public static String a(Context context, String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        Intent intent = new Intent(str);
        intent.setPackage(str2);
        List<ResolveInfo> listQueryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (listQueryBroadcastReceivers == null || listQueryBroadcastReceivers.size() <= 0) {
            return null;
        }
        return listQueryBroadcastReceivers.get(0).activityInfo.name;
    }

    public static void a(Context context, String str, boolean z) {
        try {
            Settings.System.putInt(context.getContentResolver(), str, z ? 0 : 1);
        } catch (Exception e) {
            DebugLogger.e("MzSystemUtils", "Setting setCurrentPackageName exception " + e.getMessage());
        }
    }

    public static boolean b(Context context, String str) {
        try {
            int i = Settings.System.getInt(context.getContentResolver(), str);
            DebugLogger.e("MzSystemUtils", "isRemoved " + i);
            return i == 0;
        } catch (Exception e) {
            DebugLogger.e("MzSystemUtils", "get removed package fail " + e.getMessage());
            return false;
        }
    }

    public static String b(Context context) {
        return com.meizu.cloud.pushsdk.common.b.b.a(context);
    }

    public static boolean isBrandMeizu() {
        return h.a();
    }
}
