package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ay {
    public static Runnable a;

    private static String a(Context context, String str) {
        return context.getSharedPreferences("typed_shield_pref", 0).getString(str + "_title", str);
    }

    public static String a(com.xiaomi.xmpush.thrift.ab abVar) {
        Map<String, String> mapS = abVar.m().s();
        if (mapS == null) {
            return null;
        }
        return mapS.get("__typed_shield_type");
    }

    @TargetApi(19)
    static void a(Context context, com.xiaomi.xmpush.thrift.ab abVar, Notification notification) {
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        String strA = a(abVar);
        if (TextUtils.isEmpty(strA) || !"com.xiaomi.xmsf".equals(ac.a(abVar))) {
            return;
        }
        Bundle bundle = notification.extras;
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("miui.category", strA);
        bundle.putString("miui.substName", a(context, strA));
        notification.extras = bundle;
    }

    public static boolean a(Context context, com.xiaomi.xmpush.thrift.ab abVar) {
        if (!"com.xiaomi.xmsf".equals(ac.a(abVar))) {
            return false;
        }
        String strA = a(abVar);
        if (TextUtils.isEmpty(strA)) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("typed_shield_pref", 0);
        if (!sharedPreferences.contains(strA + "_shield") && a != null) {
            a.run();
        }
        return sharedPreferences.getBoolean(strA + "_shield", true);
    }
}
