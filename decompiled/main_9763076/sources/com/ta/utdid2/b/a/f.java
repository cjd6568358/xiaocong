package com.ta.utdid2.b.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/* JADX INFO: compiled from: NetworkUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    private static ConnectivityManager a = null;
    private static final int[] d = {4, 7, 2, 1};

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static boolean m98a(Context context) {
        ConnectivityManager connectivityManagerA = a(context);
        if (connectivityManagerA != null) {
            try {
                NetworkInfo activeNetworkInfo = connectivityManagerA.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return activeNetworkInfo.isConnected();
                }
            } catch (Exception e) {
                Log.e("NetworkUtils", e.toString());
            }
        } else {
            Log.e("NetworkUtils", "connManager is null!");
        }
        return false;
    }

    public static boolean b(Context context) {
        ConnectivityManager connectivityManagerA = a(context);
        if (connectivityManagerA != null) {
            try {
                NetworkInfo activeNetworkInfo = connectivityManagerA.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    int subtype = activeNetworkInfo.getSubtype();
                    if (d.e) {
                        Log.d("NetworkUtils", "subType:" + subtype + ": name:" + activeNetworkInfo.getSubtypeName());
                    }
                    for (int i : d) {
                        if (i == subtype) {
                            return true;
                        }
                    }
                    return false;
                }
                Log.e("NetworkUtils", "networkInfo is null!");
                return false;
            } catch (Exception e) {
                Log.e("NetworkUtils", e.toString());
                return false;
            }
        }
        Log.e("NetworkUtils", "connManager is null!");
        return false;
    }

    public static ConnectivityManager a(Context context) {
        if (context == null) {
            Log.e("NetworkUtils", "context is null!");
            return null;
        }
        if (a == null) {
            a = (ConnectivityManager) context.getSystemService("connectivity");
        }
        return a;
    }
}
