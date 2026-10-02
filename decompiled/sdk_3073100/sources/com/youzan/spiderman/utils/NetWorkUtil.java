package com.youzan.spiderman.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.xiaocong.smarthome.network.util.NetworkUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class NetWorkUtil {
    public static String STATE_WIFI = NetworkUtils.NETWORKTYPE_WIFI;
    public static String STATE_4G = "4G";
    public static String STATE_3G = "3G";
    public static String STATE_2G = "2G";
    public static String STAT_UNCONNECTION = "unconnection";
    public static String UNKNOWN = NetworkUtils.NETWORKTYPE_INVALID;

    public static boolean hasNetworkPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context != null && context.checkSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0 && context.checkSelfPermission("android.permission.INTERNET") == 0;
        }
        return true;
    }

    public static boolean hasNetworkInternetPermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context != null && context.checkSelfPermission("android.permission.INTERNET") == 0;
        }
        return true;
    }

    public static boolean hasNetworkStatePermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context != null && context.checkSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
        }
        return true;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm;
        if (hasNetworkStatePermission(context) && (cm = (ConnectivityManager) context.getSystemService("connectivity")) != null) {
            return cm.getActiveNetworkInfo();
        }
        return null;
    }

    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isConnected();
    }

    public static String getConnectionStatus(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null && info.isConnected()) {
            int type = info.getType();
            int subType = info.getSubtype();
            if (type == 1) {
                return STATE_WIFI;
            }
            if (type == 0) {
                switch (subType) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return STATE_2G;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return STATE_3G;
                    case 13:
                        return STATE_4G;
                    default:
                        return UNKNOWN;
                }
            }
        }
        return STAT_UNCONNECTION;
    }
}
