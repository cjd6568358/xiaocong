package com.alibaba.sdk.android.httpdns;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class k {
    private static Context a;
    static boolean b = false;
    private static String d;

    /* JADX INFO: Access modifiers changed from: private */
    public static String c() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) a.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return "None_Network";
        }
        String typeName = activeNetworkInfo.getTypeName();
        h.d("[detectCurrentNetwork] - Network name:" + typeName + " subType name: " + activeNetworkInfo.getSubtypeName());
        return typeName == null ? "None_Network" : typeName;
    }

    public static void setContext(Context context) {
        if (context == null) {
            throw new IllegalStateException("Context can't be null");
        }
        a = context;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.alibaba.sdk.android.httpdns.k.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                try {
                    if (!isInitialStickyBroadcast() && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                        com.alibaba.sdk.android.httpdns.b.b.a(context2);
                        String strC = k.c();
                        if (strC != "None_Network" && !strC.equalsIgnoreCase(k.d)) {
                            h.d("[BroadcastReceiver.onReceive] - Network state changed");
                            ArrayList<String> arrayListM39a = c.a().m39a();
                            c.a().clear();
                            c.a().m40a();
                            if (k.b && HttpDns.instance != null) {
                                h.d("[BroadcastReceiver.onReceive] - refresh host");
                                HttpDns.instance.setPreResolveHosts(arrayListM39a);
                            }
                        }
                        String unused = k.d = strC;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        a.registerReceiver(broadcastReceiver, intentFilter);
    }
}
