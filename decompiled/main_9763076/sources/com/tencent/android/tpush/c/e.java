package com.tencent.android.tpush.c;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.c.a.f;
import com.tencent.android.tpush.c.a.g;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    public static String a = Constants.MAIN_VERSION_TAG;
    public static String b = Constants.MAIN_VERSION_TAG;
    public static String c = Constants.MAIN_VERSION_TAG;
    public static String d = Constants.MAIN_VERSION_TAG;
    public static Context e = null;
    public static boolean f = false;
    private static volatile e g = null;
    private static volatile d h = null;
    private int i = -1;

    public boolean a() {
        if (h == null) {
            return false;
        }
        return h.d(e);
    }

    private e(Context context) {
        e = context;
        if (h == null) {
            if (XGPushConfig.isUsedFcmPush(context)) {
                h = new com.tencent.android.tpush.c.a.a();
                return;
            }
            String strE = e();
            if ("xiaomi".equals(strE)) {
                h = new g();
            } else if ("huawei".equals(strE)) {
                h = new com.tencent.android.tpush.c.a.b();
            } else if ("meizu".equals(strE)) {
                h = new f();
            }
        }
    }

    public static e a(Context context) {
        if (g == null) {
            synchronized (e.class) {
                if (g == null) {
                    g = new e(context);
                }
            }
        }
        return g;
    }

    public void b() {
        if (h != null && e != null && h.d(e)) {
            h.a(e);
        }
    }

    public void c() {
        if (h != null && e != null && h.d(e)) {
            h.b(e);
        }
    }

    public String d() {
        if (h == null || h == null || !h.d(e)) {
            return null;
        }
        return h.c(e);
    }

    public static String e() {
        String str = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(str)) {
            return str.trim().toLowerCase();
        }
        return str;
    }

    public static void a(Context context, String str) {
        a = str;
    }

    public static void b(Context context, String str) {
        b = str;
    }

    public static void c(Context context, String str) {
        c = str;
    }

    public static void d(Context context, String str) {
        d = str;
    }

    public String f() {
        if (h == null || h == null) {
            return null;
        }
        return h.a();
    }

    public boolean g() {
        if (h == null || h == null) {
            return false;
        }
        return h.d(e);
    }
}
