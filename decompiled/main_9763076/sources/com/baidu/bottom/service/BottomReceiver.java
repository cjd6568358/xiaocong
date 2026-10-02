package com.baidu.bottom.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.baidu.mobstat.at;
import com.baidu.mobstat.bd;
import com.baidu.mobstat.dd;
import com.baidu.mobstat.n;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BottomReceiver extends BroadcastReceiver {
    private static dd a;
    private static long b;
    private static long c;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (a != null) {
            bd.a("Bottom has alread analyzed.");
            return;
        }
        dd ddVar = new dd();
        if (ddVar.a()) {
            a = ddVar;
            new at(this, context, intent, ddVar).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Context context, Intent intent) throws Throwable {
        String action = intent.getAction();
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (Math.abs(c - jCurrentTimeMillis) > 30000) {
            if ("android.net.wifi.STATE_CHANGE".equals(action) || "android.net.wifi.WIFI_STATE_CHANGED".equals(action) || "android.net.conn.CONNECTIVITY_CHANGE".equals(action) || "android.net.wifi.SCAN_RESULTS".equals(action)) {
                c = jCurrentTimeMillis;
                n.a(context);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Context context, Intent intent) throws Throwable {
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_REPLACED".equals(action)) {
            String schemeSpecificPart = null;
            Uri data = intent.getData();
            if (data != null) {
                schemeSpecificPart = data.getSchemeSpecificPart();
            }
            if (!TextUtils.isEmpty(schemeSpecificPart)) {
                n.a(context, action, schemeSpecificPart);
            }
        }
    }
}
