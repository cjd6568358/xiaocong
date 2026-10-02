package com.alibaba.mtl.log.e;

import android.app.ActivityManager;
import android.content.Context;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import com.tencent.android.tpush.common.Constants;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: AppInfoUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private static String ae = Constants.MAIN_VERSION_TAG;
    private static String g;

    public static String j() {
        String str;
        if (com.alibaba.mtl.log.a.getContext() == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            String string = com.alibaba.mtl.log.a.getContext().getSharedPreferences("UTCommon", 0).getString("_lun", Constants.MAIN_VERSION_TAG);
            str = !TextUtils.isEmpty(string) ? new String(c.decode(string.getBytes(), 2), HTTP.UTF_8) : Constants.MAIN_VERSION_TAG;
        } catch (Exception e) {
            str = Constants.MAIN_VERSION_TAG;
        }
        return str;
    }

    public static String k() {
        if (com.alibaba.mtl.log.a.getContext() == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            String string = com.alibaba.mtl.log.a.getContext().getSharedPreferences("UTCommon", 0).getString("_luid", Constants.MAIN_VERSION_TAG);
            if (TextUtils.isEmpty(string)) {
                return Constants.MAIN_VERSION_TAG;
            }
            return new String(c.decode(string.getBytes(), 2), HTTP.UTF_8);
        } catch (Exception e) {
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static String l() {
        return ae;
    }

    public static void n(String str) {
        i.a("AppInfoUtil", "[setChannle]", str);
        if (!TextUtils.isEmpty(str)) {
            int iIndexOf = str.indexOf("@");
            if (iIndexOf == -1) {
                ae = str;
            } else {
                ae = str.substring(0, iIndexOf);
            }
        }
    }

    public static String m() {
        return Constants.MAIN_VERSION_TAG;
    }

    public static String n() {
        return Constants.MAIN_VERSION_TAG;
    }

    public static String getAppkey() {
        return g;
    }

    public static void o(String str) {
        i.a("AppInfoUtil", "set Appkey:", str);
        g = str;
    }

    public static boolean b(Context context) {
        if (context == null) {
            return false;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (runningAppProcessInfo.processName.equals(packageName)) {
                if (runningAppProcessInfo.importance == 400) {
                    return false;
                }
                if (powerManager.isScreenOn()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String a(Context context) {
        if (context == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }
}
