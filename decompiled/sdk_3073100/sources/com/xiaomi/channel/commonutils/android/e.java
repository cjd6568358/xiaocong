package com.xiaomi.channel.commonutils.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaomi.channel.commonutils.reflect.a;
import com.xiaomi.channel.commonutils.string.d;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class e {
    private static String a = null;
    private static String b = null;
    private static String c = null;

    public static String a() {
        if (Build.VERSION.SDK_INT > 8) {
            return Build.SERIAL;
        }
        return null;
    }

    public static String a(Context context) {
        if (b == null) {
            b = "a-" + d.b(c(context) + b(context) + a());
        }
        return b;
    }

    @TargetApi(17)
    public static int b() {
        Object objA;
        if (Build.VERSION.SDK_INT >= 17 && (objA = a.a("android.os.UserHandle", "myUserId", new Object[0])) != null) {
            return ((Integer) Integer.class.cast(objA)).intValue();
        }
        return -1;
    }

    public static String b(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
            return null;
        }
    }

    public static String c(Context context) {
        int i = 10;
        String strD = d(context);
        while (strD == null) {
            int i2 = i - 1;
            if (i <= 0) {
                break;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
            }
            strD = d(context);
            i = i2;
        }
        return strD;
    }

    public static String d(Context context) {
        Object objA;
        Object objA2;
        if (a != null) {
            return a;
        }
        try {
            String deviceId = (!g.a() || (objA = a.a("miui.telephony.TelephonyManager", "getDefault", new Object[0])) == null || (objA2 = a.a(objA, "getMiuiDeviceId", new Object[0])) == null || !(objA2 instanceof String)) ? null : (String) String.class.cast(objA2);
            if (deviceId == null && h(context)) {
                deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            }
            if (deviceId == null) {
                return deviceId;
            }
            a = deviceId;
            return deviceId;
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.a(th);
            return null;
        }
    }

    public static synchronized String e(Context context) {
        String str;
        if (c != null) {
            str = c;
        } else {
            c = d.b(b(context) + a());
            str = c;
        }
        return str;
    }

    public static String f(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getSimOperatorName();
    }

    public static String g(Context context) {
        try {
            return ((WifiManager) context.getSystemService(NetworkUtils.NETWORKTYPE_WIFI)).getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            com.xiaomi.channel.commonutils.logger.b.a(e);
            return null;
        }
    }

    private static boolean h(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }
}
