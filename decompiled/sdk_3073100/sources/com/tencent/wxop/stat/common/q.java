package com.tencent.wxop.stat.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class q {
    private static SharedPreferences a = null;

    public static int a(Context context, String str, int i) {
        return a(context).getInt(l.a(context, StatConstants.MTA_COOPERATION_TAG + str), i);
    }

    public static long a(Context context, String str, long j) {
        return a(context).getLong(l.a(context, StatConstants.MTA_COOPERATION_TAG + str), j);
    }

    static synchronized SharedPreferences a(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(".mta-wxop", 0);
        a = sharedPreferences;
        if (sharedPreferences == null) {
            a = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return a;
    }

    public static String a(Context context, String str, String str2) {
        return a(context).getString(l.a(context, StatConstants.MTA_COOPERATION_TAG + str), str2);
    }

    public static void b(Context context, String str, int i) {
        String strA = l.a(context, StatConstants.MTA_COOPERATION_TAG + str);
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putInt(strA, i);
        editorEdit.commit();
    }

    public static void b(Context context, String str, long j) {
        String strA = l.a(context, StatConstants.MTA_COOPERATION_TAG + str);
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putLong(strA, j);
        editorEdit.commit();
    }

    public static void b(Context context, String str, String str2) {
        String strA = l.a(context, StatConstants.MTA_COOPERATION_TAG + str);
        SharedPreferences.Editor editorEdit = a(context).edit();
        editorEdit.putString(strA, str2);
        editorEdit.commit();
    }
}
