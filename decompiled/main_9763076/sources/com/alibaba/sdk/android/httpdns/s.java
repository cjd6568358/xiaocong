package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.content.SharedPreferences;
import java.net.SocketTimeoutException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class s {

    /* JADX INFO: renamed from: f, reason: collision with other field name */
    private static boolean f79f = false;
    private static boolean d = false;
    private static SharedPreferences a = null;
    private static int f = 0;
    private static int g = 0;
    private static long e = 0;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private static a f78a = a.ENABLE;

    enum a {
        ENABLE,
        PRE_DISABLE,
        DISABLE
    }

    static synchronized String a(n nVar) {
        String str = null;
        synchronized (s.class) {
            if (nVar == n.QUERY_HOST || nVar == n.SNIFF_HOST) {
                if (f78a == a.ENABLE || f78a == a.PRE_DISABLE || nVar != n.QUERY_HOST) {
                    str = e.f63b[f];
                }
            } else if (nVar == n.QUERY_SCHEDULE_CENTER || nVar != n.SNIFF_SCHEDULE_CENTER) {
            }
        }
        return str;
    }

    static synchronized void a(String str, String str2, long j) {
        b(str, str2, j);
        reportHttpDnsSuccess(str, 1);
        if (f78a != a.ENABLE && str2 != null && str2.equals(e.f63b[f])) {
            h.f((f78a == a.DISABLE ? "Disable " : "Pre_disable ") + "mode finished. Enter enable mode.");
            f78a = a.ENABLE;
            b(false);
            q.a().e();
            g = f;
        }
    }

    static synchronized void a(String str, String str2, Throwable th) {
        a(str2, th);
        if (a(th) && str2 != null && str2.equals(e.f63b[f])) {
            f();
            if (g == f) {
                q.a().a(false);
                o.a().c();
            }
            if (f78a == a.ENABLE) {
                f78a = a.PRE_DISABLE;
                h.f("enter pre_disable mode");
            } else if (f78a == a.PRE_DISABLE) {
                f78a = a.DISABLE;
                h.f("enter disable mode");
                b(true);
                h(str);
                q.a().g(str);
            }
        }
    }

    private static void a(String str, Throwable th) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            int iA = com.alibaba.sdk.android.httpdns.c.b.a(th);
            aVarA.b(str, String.valueOf(iA), com.alibaba.sdk.android.httpdns.c.b.m44a(th), com.alibaba.sdk.android.httpdns.c.b.b());
        }
    }

    private static boolean a(Throwable th) {
        if (th instanceof SocketTimeoutException) {
            return true;
        }
        if (!(th instanceof g)) {
            return false;
        }
        g gVar = (g) th;
        return gVar.getErrorCode() == 403 && gVar.getMessage().equals("ServiceLevelDeny");
    }

    static void b(int i) {
        if (a == null || i < 0 || i >= e.f63b.length) {
            return;
        }
        f = i;
        SharedPreferences.Editor editorEdit = a.edit();
        editorEdit.putInt("activiate_ip_index", i);
        editorEdit.putLong("activiated_ip_index_modified_time", System.currentTimeMillis());
        editorEdit.commit();
    }

    private static void b(String str, String str2, long j) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.b(str2, j, com.alibaba.sdk.android.httpdns.c.b.b());
        }
    }

    static synchronized void b(boolean z) {
        if (f79f != z) {
            f79f = z;
            if (a != null) {
                SharedPreferences.Editor editorEdit = a.edit();
                editorEdit.putBoolean("status", f79f);
                editorEdit.putLong("disable_modified_time", System.currentTimeMillis());
                editorEdit.commit();
            }
        }
    }

    static synchronized boolean d() {
        return f79f;
    }

    private static void f() {
        if (f == e.f63b.length - 1) {
            f = 0;
        } else {
            f++;
        }
        b(f);
    }

    static synchronized void g() {
        b(0);
        g = f;
        q.a().a(true);
    }

    static synchronized void h() {
        q.a().a(true);
    }

    private static void h(String str) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            String strF = o.a().f();
            int i = f;
            int length = i == 0 ? e.f63b.length - 1 : i - 1;
            int length2 = length == 0 ? e.f63b.length - 1 : length - 1;
            if (length < 0 || length >= e.f63b.length || length2 < 0 || length2 >= e.f63b.length) {
                return;
            }
            aVarA.b(str, strF, e.f63b[length2] + "," + e.f63b[length]);
        }
    }

    static synchronized void init(Context context) {
        if (!d) {
            synchronized (s.class) {
                if (!d) {
                    if (context != null) {
                        a = context.getSharedPreferences("httpdns_config_cache", 0);
                    }
                    f79f = a.getBoolean("status", false);
                    f = a.getInt("activiate_ip_index", 0);
                    g = f;
                    e = a.getLong("disable_modified_time", 0L);
                    if (System.currentTimeMillis() - e >= 86400000) {
                        b(false);
                    }
                    if (f79f) {
                        f78a = a.DISABLE;
                    } else {
                        f78a = a.ENABLE;
                    }
                    d = true;
                }
            }
        }
    }

    public static void reportHttpDnsSuccess(String str, int i) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.a(str, i, com.alibaba.sdk.android.httpdns.c.b.b(), com.alibaba.sdk.android.httpdns.b.b.m31a() ? 1 : 0);
        }
    }
}
