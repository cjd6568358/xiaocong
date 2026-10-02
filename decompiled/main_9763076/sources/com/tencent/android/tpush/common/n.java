package com.tencent.android.tpush.common;

import android.content.Context;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class n {
    private static p a = null;

    static synchronized p a(Context context) {
        if (a == null) {
            a = p.a(context);
        }
        return a;
    }

    public static long a(Context context, String str, long j) {
        return a(context).a(str, j);
    }

    public static void b(Context context, String str, long j) {
        r rVarA = a(context).a();
        rVarA.a(str, j);
        rVarA.b();
    }

    public static int a(Context context, String str, int i) {
        return a(context).a(str, i);
    }

    public static void b(Context context, String str, int i) {
        r rVarA = a(context).a();
        rVarA.a(str, i);
        rVarA.b();
    }

    public static String a(Context context, String str, String str2) {
        return a(context).a(str, str2);
    }

    public static void b(Context context, String str, String str2) {
        r rVarA = a(context).a();
        rVarA.a(str, str2);
        rVarA.b();
    }

    public static void a(Context context, String str) {
        if (a(context) != null) {
            r rVarA = a(context).a();
            rVarA.a(str);
            rVarA.b();
        }
    }
}
