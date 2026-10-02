package com.alibaba.sdk.android.httpdns.b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private String m = "UNKNOWN";

    h() {
    }

    private static int a(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return 0;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return 255;
            }
            if (!activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
                return 255;
            }
            if (activeNetworkInfo.getType() == 1) {
                return 1;
            }
            if (activeNetworkInfo.getType() != 0) {
                return 0;
            }
            switch (activeNetworkInfo.getSubtype()) {
                case 1:
                case 2:
                case 4:
                case 7:
                case 11:
                    return 2;
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case 15:
                    return 3;
                case 12:
                case 14:
                default:
                    return 0;
                case 13:
                    return 4;
            }
        } catch (Exception e) {
            return 255;
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    private static String m34a(Context context) {
        try {
            String simOperator = ((TelephonyManager) context.getSystemService("phone")).getSimOperator();
            if (!TextUtils.isEmpty(simOperator)) {
                return simOperator;
            }
        } catch (Throwable th) {
        }
        return String.valueOf(0);
    }

    private static String b(Context context) {
        try {
            WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
            if (connectionInfo != null) {
                return connectionInfo.getSSID();
            }
            return null;
        } catch (Throwable th) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(Context context) {
        switch (a(context)) {
            case 1:
                this.m = b(context);
                break;
            case 2:
            case 3:
            case 4:
                this.m = m34a(context);
                break;
        }
        if (TextUtils.isEmpty(this.m)) {
            this.m = "UNKNOWN";
        }
    }

    /* JADX INFO: renamed from: b, reason: collision with other method in class */
    void m35b(final Context context) {
        com.alibaba.sdk.android.httpdns.b.a().submit(new Runnable() { // from class: com.alibaba.sdk.android.httpdns.b.h.1
            @Override // java.lang.Runnable
            public void run() {
                h.this.c(context);
            }
        });
    }

    String g() {
        return this.m;
    }
}
