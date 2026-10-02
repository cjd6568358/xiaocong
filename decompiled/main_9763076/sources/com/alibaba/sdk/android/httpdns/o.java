package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.content.SharedPreferences;
import java.net.SocketTimeoutException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class o {
    private static String f = "https://";
    private static boolean d = false;
    private static String g = null;
    private static long e = 0;
    private static o a = null;

    /* JADX INFO: renamed from: e, reason: collision with other field name */
    private int f70e = 0;

    /* JADX INFO: renamed from: a, reason: collision with other field name */
    private SharedPreferences f69a = null;

    /* JADX INFO: renamed from: f, reason: collision with other field name */
    private long f71f = 0;

    private o() {
    }

    public static o a() {
        if (a == null) {
            synchronized (o.class) {
                if (a == null) {
                    a = new o();
                }
            }
        }
        return a;
    }

    private void a(String str, long j) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            aVarA.a(str, j, com.alibaba.sdk.android.httpdns.c.b.b());
        }
    }

    private void d() {
        if (this.f70e < e.c.length - 1) {
            this.f70e++;
        } else {
            this.f70e = 0;
        }
    }

    private void d(Throwable th) {
        com.alibaba.sdk.android.httpdns.c.a aVarA = com.alibaba.sdk.android.httpdns.c.a.a();
        if (aVarA != null) {
            int iA = com.alibaba.sdk.android.httpdns.c.b.a(th);
            aVarA.a(f(), String.valueOf(iA), th.getMessage(), com.alibaba.sdk.android.httpdns.c.b.b());
        }
    }

    synchronized void a(p pVar, long j) {
        a(f(), j);
        this.f70e = 0;
        HttpDns.switchDnsService(pVar.isEnabled());
        if (a(pVar.c())) {
            h.d("Scheduler center update success");
            this.f71f = System.currentTimeMillis();
            s.g();
        }
    }

    synchronized boolean a(String[] strArr) {
        boolean z = false;
        synchronized (this) {
            if (e.a(strArr)) {
                StringBuilder sb = new StringBuilder();
                for (String str : strArr) {
                    sb.append(str);
                    sb.append(";");
                }
                sb.deleteCharAt(sb.length() - 1);
                if (this.f69a != null) {
                    SharedPreferences.Editor editorEdit = this.f69a.edit();
                    editorEdit.putString("httpdns_server_ips", sb.toString());
                    editorEdit.putLong("schedule_center_last_request_time", System.currentTimeMillis());
                    editorEdit.commit();
                    z = true;
                }
            }
        }
        return z;
    }

    synchronized void c() {
        if (System.currentTimeMillis() - this.f71f >= 300000) {
            h.d("update server ips from schedule center.");
            this.f70e = 0;
            b.a().submit(new m(e.c.length - 1));
        } else {
            h.d("update server ips from schedule center too often, give up. ");
            s.h();
        }
    }

    synchronized void c(Throwable th) {
        d(th);
        if (th instanceof SocketTimeoutException) {
            d();
            if (this.f70e == 0) {
                this.f71f = System.currentTimeMillis();
                h.f("Scheduler center update failed");
                s.h();
            }
        }
    }

    synchronized String f() {
        return f + e.c[this.f70e] + "/sc/httpdns_config?account_id=" + e.f61a + "&platform=android&sdk_version=1.1.9";
    }

    synchronized void init(Context context) {
        if (!d) {
            synchronized (o.class) {
                if (!d) {
                    if (context != null) {
                        this.f69a = context.getSharedPreferences("httpdns_config_cache", 0);
                    }
                    g = this.f69a.getString("httpdns_server_ips", null);
                    if (g != null) {
                        e.a(g.split(";"));
                    }
                    e = this.f69a.getLong("schedule_center_last_request_time", 0L);
                    if (e == 0 || System.currentTimeMillis() - e >= 86400000) {
                        q.a().a(false);
                        c();
                    }
                    d = true;
                }
            }
        }
    }
}
