package com.baidu.mobstat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class n {
    public static void a(Context context) throws Throwable {
        m.a.a(context);
        az.a(context).a(u.AP_LIST, System.currentTimeMillis());
    }

    public static void a(Context context, String str, String str2) throws Throwable {
        q.a.a(context, str, str2);
        az.a(context).a(u.APP_CHANGE, System.currentTimeMillis());
    }

    public static void a(Context context, boolean z) throws Throwable {
        r.a.a(context, z);
        az.a(context).a(z ? u.APP_SYS_LIST : u.APP_USER_LIST, System.currentTimeMillis());
    }

    public static void b(Context context, boolean z) throws Throwable {
        s.a.a(context, z);
        az.a(context).a(z ? u.APP_TRACE_CURRENT : u.APP_TRACE_HIS, System.currentTimeMillis());
    }

    public static void b(Context context) throws Throwable {
        o.a.a(context);
        az.a(context).a(u.APP_APK, System.currentTimeMillis());
    }
}
