package com.huawei.hms.support.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/* JADX INFO: compiled from: HMSLog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static final b a = new b();

    public static void a(Context context, int i, String str) {
        a.a(context, i, str);
        StringBuilder sb = new StringBuilder();
        sb.append("============================================================================").append('\n');
        sb.append("====== ").append(a(context)).append('\n');
        sb.append("============================================================================");
        a.a(str, sb.toString());
    }

    private static String a(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return "HMS-" + packageInfo.versionName + "(" + packageInfo.versionCode + ")";
            } catch (PackageManager.NameNotFoundException e) {
                return "HMS-[unknown-version]";
            }
        }
        return "HMS-[unknown-version]";
    }

    public static boolean a() {
        return a.a(3);
    }

    public static boolean b() {
        return a.a(4);
    }

    public static boolean c() {
        return a.a(5);
    }

    public static boolean d() {
        return a.a(6);
    }

    public static void a(String str, String str2) {
        a.a(3, str, str2);
    }

    public static void b(String str, String str2) {
        a.a(4, str, str2);
    }

    public static void c(String str, String str2) {
        a.a(5, str, str2);
    }

    public static void d(String str, String str2) {
        a.a(6, str, str2);
    }
}
