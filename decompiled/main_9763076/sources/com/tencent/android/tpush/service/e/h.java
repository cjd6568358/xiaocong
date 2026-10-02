package com.tencent.android.tpush.service.e;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private static SharedPreferences b = null;
    static int a = 100;

    public static String a(Context context, String str, String str2) {
        a(context);
        return b.getString(str, str2);
    }

    public static void b(Context context, String str, String str2) {
        try {
            SharedPreferences.Editor editorEdit = a(context).edit();
            editorEdit.putString(str, str2);
            editorEdit.commit();
        } catch (Throwable th) {
        }
    }

    public static int a(Context context, String str, int i) {
        a(context);
        return b.getInt(str, i);
    }

    public static void b(Context context, String str, int i) {
        try {
            SharedPreferences.Editor editorEdit = a(context).edit();
            editorEdit.putInt(str, i);
            editorEdit.commit();
        } catch (Throwable th) {
        }
    }

    public static long a(Context context, String str, long j) {
        a(context);
        return b.getLong(str, j);
    }

    public static void b(Context context, String str, long j) {
        try {
            SharedPreferences.Editor editorEdit = a(context).edit();
            editorEdit.putLong(str, j);
            editorEdit.commit();
        } catch (Throwable th) {
        }
    }

    private static SharedPreferences a(Context context) {
        if (b == null) {
            b = context.getSharedPreferences("tpush.shareprefs", 0);
        }
        return b;
    }
}
