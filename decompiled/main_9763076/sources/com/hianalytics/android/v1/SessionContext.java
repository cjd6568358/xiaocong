package com.hianalytics.android.v1;

import android.content.Context;
import android.content.SharedPreferences;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SessionContext {
    private static String createSessionID(Context context) {
        return String.valueOf(System.currentTimeMillis());
    }

    public static void destroy(Context context) {
        SharedPreferences.Editor editorEdit = com.hianalytics.android.b.a.c.a(context, "sessioncontext").edit();
        editorEdit.remove("session_id");
        editorEdit.remove("refer_id");
        editorEdit.commit();
    }

    public static void destroyReferID(Context context) {
        SharedPreferences.Editor editorEdit = com.hianalytics.android.b.a.c.a(context, "sessioncontext").edit();
        editorEdit.remove("refer_id");
        editorEdit.commit();
    }

    public static String getReferID(Context context) {
        return com.hianalytics.android.b.a.c.a(context, "sessioncontext").getString("refer_id", Constants.MAIN_VERSION_TAG);
    }

    public static String getSessionID(Context context) {
        return com.hianalytics.android.b.a.c.a(context, "sessioncontext").getString("session_id", Constants.MAIN_VERSION_TAG);
    }

    public static void init(Context context, String str) {
        SharedPreferences sharedPreferencesA = com.hianalytics.android.b.a.c.a(context, "sessioncontext");
        String strCreateSessionID = createSessionID(context);
        SharedPreferences.Editor editorEdit = sharedPreferencesA.edit();
        editorEdit.remove("session_id");
        editorEdit.remove("refer_id");
        editorEdit.putString("session_id", strCreateSessionID);
        editorEdit.putString("refer_id", str);
        editorEdit.commit();
    }

    public static void setReferID(Context context, String str) {
        SharedPreferences.Editor editorEdit = com.hianalytics.android.b.a.c.a(context, "sessioncontext").edit();
        editorEdit.putString("refer_id", str);
        editorEdit.commit();
    }

    public static void setSessionID(Context context, String str) {
        SharedPreferences.Editor editorEdit = com.hianalytics.android.b.a.c.a(context, "sessioncontext").edit();
        editorEdit.putString("session_id", str);
        editorEdit.commit();
    }
}
