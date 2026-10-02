package com.xiaomi.push.service;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import java.util.ArrayList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class h {
    public static void a(Context context, String str) {
        ArrayList<com.xiaomi.xmpush.thrift.j> arrayListB = e.a(context).b(str);
        if (arrayListB == null || arrayListB.size() < 1) {
            return;
        }
        if (e.a(context).e(str) == 0) {
            com.xiaomi.channel.commonutils.logger.b.a("appIsUninstalled. failed to delete geofencing with package name. name:" + str);
        }
        for (com.xiaomi.xmpush.thrift.j jVar : arrayListB) {
            if (jVar == null) {
                com.xiaomi.channel.commonutils.logger.b.a("appIsUninstalled. failed to find geofence with package name. name:" + str);
                return;
            } else {
                a(jVar.a(), context);
                if (g.a(context).b(jVar.a()) == 0) {
                    com.xiaomi.channel.commonutils.logger.b.a("appIsUninstalled. failed to delete geoMessage with package name. name:" + str + ", geoId:" + jVar.a());
                }
            }
        }
    }

    public static void a(String str, Context context) {
        new com.xiaomi.metok.geofencing.a(context).a(context, "com.xiaomi.xmsf", str);
    }

    public static boolean a(Context context) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo("com.xiaomi.metok", AsyncHttpClient.DEFAULT_SOCKET_BUFFER_SIZE);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        return packageInfo != null && packageInfo.versionCode >= 20;
    }

    public static boolean b(Context context) {
        return TextUtils.equals(context.getPackageName(), "com.xiaomi.xmsf");
    }
}
