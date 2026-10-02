package com.hzy.tvmao.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.hzy.tvmao.KookongSDK;

/* JADX INFO: compiled from: NetStatusUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    public static boolean a = false;

    public static boolean a() {
        NetworkInfo networkInfoA = a(KookongSDK.getContext());
        a = networkInfoA != null && networkInfoA.isConnected();
        return a;
    }

    public static NetworkInfo a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo();
        }
        return null;
    }
}
