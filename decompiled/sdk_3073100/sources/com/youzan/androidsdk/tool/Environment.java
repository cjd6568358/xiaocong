package com.youzan.androidsdk.tool;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.text.TextUtils;
import com.youzan.spiderman.utils.NetWorkUtil;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Environment {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final AtomicInteger f574 = new AtomicInteger(255);

    public static boolean isOnLaunchedApplication(Context context) {
        String processName;
        if (context == null || (processName = m82(context, Process.myPid())) == null) {
            return false;
        }
        return processName.equals(context.getPackageName());
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static String m82(Context context, int pid) {
        List<ActivityManager.RunningAppProcessInfo> runningApps;
        if (context != null) {
            try {
                ActivityManager am = (ActivityManager) context.getSystemService("activity");
                if (am != null && (runningApps = am.getRunningAppProcesses()) != null) {
                    for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                        if (procInfo.pid == pid) {
                            return procInfo.processName;
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    static String m81(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
            return "App/" + pkName + "_v" + versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int generateRequestId() {
        int result;
        int newValue;
        do {
            result = f574.get();
            newValue = result + 1;
            if (newValue >= 65535) {
                newValue = 255;
            }
        } while (!f574.compareAndSet(result, newValue));
        return result;
    }

    public static void copyText(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService("clipboard");
        ClipData clip = ClipData.newPlainText("textMessage", text);
        clipboard.setPrimaryClip(clip);
    }

    /* JADX WARN: Bottom block not found for handler: Throwable -> 0x0019 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean appInstalled(Context context, String pkg) {
        if (context == null || TextUtils.isEmpty(pkg)) {
            return true;
        }
        synchronized (PackageManager.class) {
            try {
                PackageManager pm = context.getPackageManager();
                pm.getPackageInfo(pkg, 1);
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public static boolean isNetworkConnect(Context context) {
        return NetWorkUtil.isConnected(context);
    }
}
