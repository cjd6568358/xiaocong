package com.tencent.android.tpush.service.e;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f {
    private static String a(String str) {
        return com.tencent.android.tpush.encrypt.a.a(str);
    }

    public static boolean a(Context context, String str, String str2) {
        try {
            g.b(context, a(str), str2);
            return true;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("ServicePushInfoMd5Pref", "putString", e);
            return false;
        }
    }

    public static String a(Context context, String str) {
        try {
            return g.a(context, a(str), (String) null);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("ServicePushInfoMd5Pref", "getString", e);
            return null;
        }
    }

    public static boolean a(Context context, String str, long j) {
        try {
            g.b(context, a(str), j);
            return false;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("ServicePushInfoMd5Pref", "putLong", e);
            return false;
        }
    }

    public static long b(Context context, String str, long j) {
        try {
            return g.a(context, a(str), j);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("ServicePushInfoMd5Pref", "getLong", e);
            return 0L;
        }
    }

    public static boolean a(Context context, String str, int i) {
        try {
            g.b(context, a(str), i);
            return true;
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("ServicePushInfoMd5Pref", "putInt", e);
            return false;
        }
    }

    public static int b(Context context, String str, int i) {
        try {
            return g.a(context, a(str), i);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("ServicePushInfoMd5Pref", "getInt", e);
            return 0;
        }
    }
}
