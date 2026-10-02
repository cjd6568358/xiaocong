package com.xiaocong.smarthome.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceInfoUtils {
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        String deviceId = tm.getDeviceId();
        if (TextUtils.isEmpty(deviceId) || deviceId.equals("")) {
            return Build.SERIAL;
        }
        return deviceId;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService("window");
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static String getBrandName(Context context) {
        return Build.BOARD;
    }

    public static String getDeviceVersion(Context context) {
        return Build.VERSION.RELEASE;
    }

    public static String getPhoneModel(Context context) {
        return Build.MODEL;
    }

    public static String getAppMetaData(Context ctx, String key) {
        ApplicationInfo applicationInfo;
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager == null || (applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), 128)) == null || applicationInfo.metaData == null) {
                return null;
            }
            String resultData = applicationInfo.metaData.getString(key);
            return resultData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
