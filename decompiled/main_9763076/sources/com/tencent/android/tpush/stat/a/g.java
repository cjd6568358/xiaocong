package com.tencent.android.tpush.stat.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static SharedPreferences a = null;

    static synchronized SharedPreferences a(Context context) {
        SharedPreferences sharedPreferences;
        try {
            if (Build.VERSION.SDK_INT >= 11) {
                a = context.getSharedPreferences(".tpush_mta", 4);
            } else {
                a = context.getSharedPreferences(".tpush_mta", 0);
            }
            if (a == null) {
                a = PreferenceManager.getDefaultSharedPreferences(context);
            }
            sharedPreferences = a;
        } catch (Throwable th) {
            try {
                a = PreferenceManager.getDefaultSharedPreferences(context);
                sharedPreferences = a;
            } catch (Exception e) {
                sharedPreferences = null;
            }
        }
        return sharedPreferences;
    }

    public static long a(Context context, String str, long j) {
        return a(context).getLong(e.a(context, "tpush_" + str), j);
    }

    public static void b(Context context, String str, long j) {
        String strA = e.a(context, "tpush_" + str);
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putLong(strA, j);
        editorEdit.commit();
    }

    public static int a(Context context, String str, int i) {
        return a(context).getInt(e.a(context, "tpush_" + str), i);
    }

    public static void b(Context context, String str, int i) {
        String strA = e.a(context, "tpush_" + str);
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putInt(strA, i);
        editorEdit.commit();
    }
}
