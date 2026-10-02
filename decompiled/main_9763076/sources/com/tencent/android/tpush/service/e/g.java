package com.tencent.android.tpush.service.e;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static SharedPreferences a = null;

    static synchronized SharedPreferences a(Context context) {
        if (a == null) {
            a = context.getSharedPreferences(".tpns.service.xml", 0);
        }
        return a;
    }

    public static long a(Context context, String str, long j) {
        return a(context).getLong(str, j);
    }

    public static void b(Context context, String str, long j) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putLong(str, j);
        a(editorEdit);
    }

    public static int a(Context context, String str, int i) {
        return a(context).getInt(str, i);
    }

    public static void b(Context context, String str, int i) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putInt(str, i);
        a(editorEdit);
    }

    public static String a(Context context, String str, String str2) {
        if (a(context).contains(str)) {
            return a(context).getString(str, str2);
        }
        return str2;
    }

    public static void b(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putString(str, str2);
        a(editorEdit);
    }

    @SuppressLint({"NewApi"})
    private static void a(SharedPreferences.Editor editor) {
        if (Build.VERSION.SDK_INT >= 9) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
