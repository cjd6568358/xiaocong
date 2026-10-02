package com.youzan.spiderman.c;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: RemoteParams.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class d {
    private static String a = null;
    private static String b = null;
    private static String c = null;

    public static void a(Context context) {
        PackageInfo packageInfo;
        if (context != null) {
            c = "2.1.8";
            a = context.getPackageName();
            try {
                PackageManager pm = context.getPackageManager();
                if (pm != null && (packageInfo = pm.getPackageInfo(a, 128)) != null) {
                    b = packageInfo.versionName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, String> a() {
        Map<String, String> params = new HashMap<>();
        params.put("package_name", a);
        params.put("app_version", b);
        params.put("cache_version", c);
        params.put("platform", "android");
        return params;
    }
}
