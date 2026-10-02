package com.xiaocong.smarthome.network.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class NetworkUtils {
    public static final String NETWORKTYPE_2G = "2g";
    public static final String NETWORKTYPE_3G = "3g";
    public static final String NETWORKTYPE_INVALID = "unknown";
    public static final String NETWORKTYPE_MOBILE = "wap_2g_3g";
    public static final String NETWORKTYPE_WAP = "wap";
    public static final String NETWORKTYPE_WIFI = "wifi";

    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.getType() == 1;
    }

    public static String getNetWorkType(Context context) {
        String mNetWorkType;
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService("connectivity");
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return NETWORKTYPE_INVALID;
            }
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                return NETWORKTYPE_WIFI;
            }
            if (!type.equalsIgnoreCase("MOBILE")) {
                return NETWORKTYPE_INVALID;
            }
            String proxyHost = Proxy.getDefaultHost();
            if (TextUtils.isEmpty(proxyHost)) {
                mNetWorkType = isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G;
            } else {
                mNetWorkType = NETWORKTYPE_WAP;
            }
            return mNetWorkType;
        } catch (Exception e) {
            return NETWORKTYPE_INVALID;
        }
    }

    public static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        switch (telephonyManager.getNetworkType()) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            default:
                return false;
            case 3:
                return true;
            case 5:
                return true;
            case 6:
                return true;
            case 8:
                return true;
            case 9:
                return true;
            case 10:
                return true;
            case 12:
                return true;
            case 13:
                return true;
            case 14:
                return true;
            case 15:
                return true;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo[] info;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity == null || (info = connectivity.getAllNetworkInfo()) == null) {
            return false;
        }
        for (int i = 0; i < info.length; i++) {
            if (info[i].getState() == NetworkInfo.State.CONNECTED || info[i].getState() == NetworkInfo.State.CONNECTING) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context mContext) {
        ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService("connectivity");
        NetworkInfo mWifi = connManager.getNetworkInfo(1);
        if (mWifi == null || !mWifi.isConnected()) {
            return false;
        }
        return true;
    }
}
