package com.tencent.android.tpush.stat;

import android.content.Context;
import android.content.IntentFilter;
import com.tencent.android.tpush.common.Constants;
import org.apache.http.HttpHost;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static volatile a d = null;
    private volatile int a = 2;
    private volatile String b = Constants.MAIN_VERSION_TAG;
    private volatile HttpHost c = null;
    private Context e;
    private com.tencent.android.tpush.stat.a.f f;

    public String a() {
        return this.b;
    }

    private a(Context context) {
        this.e = null;
        this.f = null;
        this.e = context.getApplicationContext();
        f.a(context);
        this.f = com.tencent.android.tpush.stat.a.e.b();
        f();
        d();
    }

    public boolean b() {
        return this.a == 1;
    }

    public boolean c() {
        return this.a != 0;
    }

    public static a a(Context context) {
        if (d == null) {
            synchronized (a.class) {
                if (d == null) {
                    d = new a(context);
                }
            }
        }
        return d;
    }

    private void f() {
        this.a = 0;
        this.c = null;
        this.b = null;
    }

    void d() {
        if (com.tencent.android.tpush.stat.a.h.j(this.e)) {
            this.b = com.tencent.android.tpush.stat.a.e.f(this.e);
            if (c.b()) {
                this.f.b("NETWORK name:" + this.b);
            }
            if (com.tencent.android.tpush.stat.a.e.b(this.b)) {
                if ("WIFI".equalsIgnoreCase(this.b)) {
                    this.a = 1;
                } else {
                    this.a = 2;
                }
                this.c = com.tencent.android.tpush.stat.a.e.b(this.e);
                return;
            }
            return;
        }
        if (c.b()) {
            this.f.b("NETWORK TYPE: network is close.");
        }
        f();
    }

    public void e() {
        try {
            this.e.getApplicationContext().registerReceiver(new b(this), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c("registerBroadcast", Constants.MAIN_VERSION_TAG, th);
        }
    }
}
