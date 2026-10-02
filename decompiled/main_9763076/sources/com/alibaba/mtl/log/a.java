package com.alibaba.mtl.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.l;
import com.alibaba.mtl.log.sign.IRequestAuth;
import com.tencent.android.tpush.common.Constants;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: compiled from: UTDC.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static String B;
    public static IRequestAuth a;
    public static final AtomicInteger d;
    private static Context mContext;
    private static boolean q;
    public static boolean r;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static boolean f24a = false;
    public static boolean o = false;
    public static int s = Constants.ERRORCODE_UNKNOWN;
    public static int t = 0;
    public static long b = -1;
    public static boolean p = false;

    static {
        q = t <= s;
        B = String.valueOf(System.currentTimeMillis());
        d = new AtomicInteger(0);
        r = true;
        a = null;
    }

    public static synchronized void init(Context context) {
        try {
            if (context == null) {
                i.a("UTDC", "UTDC init failed ,context:" + context);
            } else if (!f24a) {
                f24a = true;
                mContext = context.getApplicationContext();
                com.alibaba.mtl.log.d.a.a().start();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void a(IRequestAuth iRequestAuth) {
        a = iRequestAuth;
        if (a != null) {
            com.alibaba.mtl.log.e.b.o(a.getAppkey());
        }
    }

    public static void setChannel(String channel) {
        com.alibaba.mtl.log.e.b.n(channel);
    }

    public static void l() {
        i.a("UTDC", "[onBackground]");
        o = true;
        com.alibaba.mtl.log.b.a.E();
    }

    public static void m() {
        i.a("UTDC", "[onForeground]");
        o = false;
        com.alibaba.mtl.log.d.a.a().start();
    }

    public static void a(String str, String str2, String str3, String str4, String str5, Map<String, String> map) {
        if (mContext == null) {
            i.a("UTDC", "please call UTDC.init(context) before commit log,and this log will be discarded");
        } else {
            if (a == null) {
                i.a("UTDC", "please call UTDC.setRequestAuthentication(auth) before commit log,and this log will be discarded");
                return;
            }
            i.a("UTDC", "[commit] page:", str, "eventId:", str2, "arg1:", str3, "arg2:", str4, "arg3:", str5, "args:", map);
            com.alibaba.mtl.log.b.a.l(str2);
            com.alibaba.mtl.log.c.c.a().a(new com.alibaba.mtl.log.model.a(str, str2, str3, str4, str5, map));
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static IRequestAuth a() {
        if (a == null || TextUtils.isEmpty(a.getAppkey())) {
            if (i.l()) {
                throw new RuntimeException("please Set <meta-data android:value=\"YOU KEY\" android:name=\"com.alibaba.apmplus.app_key\"></meta-data> in app AndroidManifest.xml ");
            }
            Log.w("UTDC", "please Set <meta-data android:value=\"YOU KEY\" android:name=\"com.alibaba.apmplus.app_key\"></meta-data> in app AndroidManifest.xml ");
        }
        return a;
    }

    public static String b() {
        try {
            return l.getNetworkState(getContext())[0];
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public static String c() {
        try {
            String[] networkState = l.getNetworkState(getContext());
            if (networkState[0].equals("2G/3G")) {
                return networkState[1];
            }
            return "Unknown";
        } catch (Exception e) {
            return "Unknown";
        }
    }

    public static String d() {
        return Constants.MAIN_VERSION_TAG;
    }

    public static String e() {
        return Constants.MAIN_VERSION_TAG;
    }

    public static void n() {
        com.alibaba.mtl.log.d.a.a().start();
    }
}
