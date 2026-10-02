package com.xiaocong.smarthome.httplib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class NetworkUtils {
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
}
