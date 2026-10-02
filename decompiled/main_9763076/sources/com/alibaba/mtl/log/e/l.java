package com.alibaba.mtl.log.e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

/* JADX INFO: compiled from: NetworkUtil.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class l {
    private static a a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static b f37a;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static String[] f38a = {"Unknown", "Unknown"};

    static {
        f37a = new b();
        a = new a();
    }

    public static String t() {
        NetworkInfo activeNetworkInfo;
        Context context = com.alibaba.mtl.log.a.getContext();
        if (context == null) {
            return "Unknown";
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) == 0 && (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) != null && activeNetworkInfo.isConnected()) {
                if (activeNetworkInfo.getType() == 1) {
                    return "wifi";
                }
                if (activeNetworkInfo.getType() == 0) {
                    return a(activeNetworkInfo.getSubtype());
                }
            }
        } catch (Throwable th) {
        }
        return "Unknown";
    }

    public static boolean isConnected() {
        Context context = com.alibaba.mtl.log.a.getContext();
        if (context != null) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        return activeNetworkInfo.isConnected();
                    }
                    return false;
                }
            } catch (Exception e) {
            }
        }
        return true;
    }

    private static String a(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return "2G";
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return "3G";
            case 13:
                return "4G";
            default:
                return "Unknown";
        }
    }

    public static String[] getNetworkState(Context paramContext) {
        return f38a;
    }

    public static String getWifiAddress(Context context) {
        WifiInfo connectionInfo;
        if (context != null && (connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo()) != null) {
            String macAddress = connectionInfo.getMacAddress();
            if (TextUtils.isEmpty(macAddress)) {
                return "00:00:00:00:00:00";
            }
            return macAddress;
        }
        return "00:00:00:00:00:00";
    }

    public static void a(Context context) {
        if (context != null) {
            context.registerReceiver(f37a, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        }
    }

    public static void b(Context context) {
        if (context != null && f37a != null) {
            context.unregisterReceiver(f37a);
        }
    }

    /* JADX INFO: compiled from: NetworkUtil.java */
    private static class b extends BroadcastReceiver {
        private b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            r.a().b(l.a.a(context));
        }
    }

    /* JADX INFO: compiled from: NetworkUtil.java */
    private static class a implements Runnable {
        private Context a;

        private a() {
        }

        public a a(Context context) {
            this.a = context;
            return this;
        }

        @Override // java.lang.Runnable
        public void run() {
            ConnectivityManager connectivityManager;
            if (this.a != null) {
                try {
                    if (this.a.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.a.getPackageName()) != 0 || (connectivityManager = (ConnectivityManager) this.a.getSystemService("connectivity")) == null) {
                        l.f38a[0] = "Unknown";
                    } else {
                        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                            if (1 == activeNetworkInfo.getType()) {
                                l.f38a[0] = "Wi-Fi";
                            } else if (activeNetworkInfo.getType() == 0) {
                                l.f38a[0] = "2G/3G";
                                l.f38a[1] = activeNetworkInfo.getSubtypeName();
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
