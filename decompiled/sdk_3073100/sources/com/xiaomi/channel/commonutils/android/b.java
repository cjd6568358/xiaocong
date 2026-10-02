package com.xiaomi.channel.commonutils.android;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {

    public enum a {
        UNKNOWN(0),
        ALLOWED(1),
        NOT_ALLOWED(2);

        private final int d;

        a(int i) {
            this.d = i;
        }

        public int a() {
            return this.d;
        }
    }

    public static String a(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            packageInfo = null;
        }
        return packageInfo != null ? packageInfo.versionName : "1.0";
    }

    public static int b(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 16384);
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            packageInfo = null;
        }
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }

    public static String c(Context context) {
        if (context == null) {
            return null;
        }
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        int iMyPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    @TargetApi(19)
    public static a d(Context context, String str) {
        a aVar;
        if (context == null || TextUtils.isEmpty(str) || Build.VERSION.SDK_INT < 19) {
            return a.UNKNOWN;
        }
        try {
            Integer num = (Integer) com.xiaomi.channel.commonutils.reflect.a.a((Class<? extends Object>) AppOpsManager.class, "OP_POST_NOTIFICATION");
            if (num == null) {
                aVar = a.UNKNOWN;
            } else {
                Integer num2 = (Integer) com.xiaomi.channel.commonutils.reflect.a.a((AppOpsManager) context.getSystemService("appops"), "checkOpNoThrow", num, Integer.valueOf(context.getPackageManager().getApplicationInfo(str, 0).uid), str);
                aVar = (num2 == null || num2.intValue() != 0) ? a.NOT_ALLOWED : a.ALLOWED;
            }
            return aVar;
        } catch (Throwable th) {
            return a.UNKNOWN;
        }
    }

    public static String d(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        if (runningAppProcesses != null && runningAppProcesses.size() > 0) {
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (it.hasNext()) {
                String[] strArr = it.next().pkgList;
                for (int i = 0; strArr != null && i < strArr.length; i++) {
                    if (!arrayList.contains(strArr[i])) {
                        arrayList.add(strArr[i]);
                        if (arrayList.size() == 1) {
                            sb.append(((String) arrayList.get(0)).hashCode() % 100000);
                        } else {
                            sb.append("#").append(strArr[i].hashCode() % 100000);
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    public static boolean f(Context context, String str) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, AsyncHttpClient.DEFAULT_SOCKET_BUFFER_SIZE);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null;
    }
}
