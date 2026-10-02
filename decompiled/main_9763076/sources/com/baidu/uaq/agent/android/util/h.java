package com.baidu.uaq.agent.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.baidu.uaq.agent.android.logging.a;
import com.baidu.uaq.agent.android.logging.b;
import java.text.MessageFormat;

/* JADX INFO: compiled from: NetworkUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class h {
    private static final a LOG = b.bg();

    public static String h(Context context) {
        try {
            NetworkInfo networkInfo = l(context);
            if (networkInfo == null || !a(networkInfo)) {
                return "none";
            }
            if (b(networkInfo)) {
                return m(context);
            }
            if (c(networkInfo)) {
                return "wifi";
            }
            LOG.warning(MessageFormat.format("Unknown network type: {0} [{1}]", networkInfo.getTypeName(), Integer.valueOf(networkInfo.getType())));
            return "unknown";
        } catch (SecurityException e) {
            return "unknown";
        }
    }

    public static String i(Context context) {
        try {
            NetworkInfo networkInfo = l(context);
            if (networkInfo == null || !a(networkInfo)) {
                return "none";
            }
            if (c(networkInfo)) {
                return "wifi";
            }
            if (b(networkInfo)) {
                return b(networkInfo.getSubtype());
            }
            return "unknown";
        } catch (SecurityException e) {
            return "unknown";
        }
    }

    public static boolean j(Context context) {
        try {
            NetworkInfo networkInfo = l(context);
            return a(networkInfo) && networkInfo.getType() != 1;
        } catch (SecurityException e) {
            return false;
        }
    }

    public static boolean k(Context context) {
        try {
            NetworkInfo networkInfo = l(context);
            return networkInfo != null && networkInfo.getType() == 1;
        } catch (SecurityException e) {
            return false;
        }
    }

    public static NetworkInfo l(Context context) throws SecurityException {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return null;
        }
        try {
            return connectivityManager.getActiveNetworkInfo();
        } catch (SecurityException e) {
            LOG.warning("Cannot determine network state. Enable android.permission.ACCESS_NETWORK_STATE in your manifest.");
            throw e;
        }
    }

    public static boolean a(NetworkInfo networkInfo) {
        return networkInfo != null && networkInfo.isConnected();
    }

    private static boolean b(NetworkInfo networkInfo) {
        switch (networkInfo.getType()) {
            case 0:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            case 1:
            default:
                return false;
        }
    }

    private static String m(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String networkOperator = telephonyManager.getNetworkOperatorName();
        boolean smellsLikeAnEmulator = Build.PRODUCT.equals("google_sdk") || Build.PRODUCT.equals("sdk") || Build.PRODUCT.equals("sdk_x86") || Build.FINGERPRINT.startsWith("generic");
        if (networkOperator.equals("Android") && smellsLikeAnEmulator) {
            return "wifi";
        }
        return networkOperator;
    }

    private static boolean c(NetworkInfo networkInfo) {
        switch (networkInfo.getType()) {
            case 1:
            case 6:
            case 7:
            case 9:
                return true;
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
            default:
                return false;
        }
    }

    private static String b(int subType) {
        switch (subType) {
            case 1:
                return "GPRS";
            case 2:
                return "EDGE";
            case 3:
                return "UMTS";
            case 4:
                return "CDMA";
            case 5:
                return "EVDO rev 0";
            case 6:
                return "EVDO rev A";
            case 7:
                return "1xRTT";
            case 8:
                return "HSDPA";
            case 9:
                return "HSUPA";
            case 10:
                return "HSPA";
            case 11:
                return "IDEN";
            case 12:
                return "EVDO rev B";
            case 13:
                return "LTE";
            case 14:
                return "HRPD";
            case 15:
                return "HSPAP";
            default:
                return "unknown";
        }
    }
}
