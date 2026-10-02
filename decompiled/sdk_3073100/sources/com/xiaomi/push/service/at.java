package com.xiaomi.push.service;

import android.content.SharedPreferences;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class at {
    private static String a;
    private static at e = new at();
    private List<a> b = new ArrayList();
    private com.xiaomi.push.protobuf.a.C0011a c;
    private com.xiaomi.channel.commonutils.misc.h.b d;

    public static abstract class a {
        public void a(com.xiaomi.push.protobuf.a.C0011a c0011a) {
        }

        public void a(com.xiaomi.push.protobuf.b.C0012b c0012b) {
        }
    }

    private at() {
    }

    public static at a() {
        return e;
    }

    public static synchronized String e() {
        if (a == null) {
            SharedPreferences sharedPreferences = com.xiaomi.channel.commonutils.android.j.a().getSharedPreferences("XMPushServiceConfig", 0);
            a = sharedPreferences.getString("DeviceUUID", null);
            if (a == null) {
                a = com.xiaomi.channel.commonutils.android.j.b();
                if (a != null) {
                    sharedPreferences.edit().putString("DeviceUUID", a).commit();
                }
            }
        }
        return a;
    }

    private void f() throws Throwable {
        if (this.c == null) {
            h();
        }
    }

    private void g() {
        if (this.d != null) {
            return;
        }
        this.d = new au(this);
        com.xiaomi.smack.util.e.a(this.d);
    }

    private void h() throws Throwable {
        BufferedInputStream bufferedInputStream;
        try {
            try {
                bufferedInputStream = new BufferedInputStream(com.xiaomi.channel.commonutils.android.j.a().openFileInput("XMCloudCfg"));
                try {
                    this.c = com.xiaomi.push.protobuf.a.C0011a.c(com.google.protobuf.micro.b.a(bufferedInputStream));
                    bufferedInputStream.close();
                    com.xiaomi.channel.commonutils.file.a.a(bufferedInputStream);
                } catch (Exception e2) {
                    e = e2;
                    com.xiaomi.channel.commonutils.logger.b.a("load config failure: " + e.getMessage());
                    com.xiaomi.channel.commonutils.file.a.a(bufferedInputStream);
                }
            } catch (Throwable th) {
                th = th;
                com.xiaomi.channel.commonutils.file.a.a(bufferedInputStream);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            bufferedInputStream = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedInputStream = null;
            com.xiaomi.channel.commonutils.file.a.a(bufferedInputStream);
            throw th;
        }
        if (this.c == null) {
            this.c = new com.xiaomi.push.protobuf.a.C0011a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        try {
            if (this.c != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(com.xiaomi.channel.commonutils.android.j.a().openFileOutput("XMCloudCfg", 0));
                com.google.protobuf.micro.c cVarA = com.google.protobuf.micro.c.a(bufferedOutputStream);
                this.c.a(cVarA);
                cVarA.a();
                bufferedOutputStream.close();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a("save config failure: " + e2.getMessage());
        }
    }

    void a(com.xiaomi.push.protobuf.b.C0012b c0012b) {
        a[] aVarArr;
        if (c0012b.i() && c0012b.h() > c()) {
            g();
        }
        synchronized (this) {
            aVarArr = (a[]) this.b.toArray(new a[this.b.size()]);
        }
        for (a aVar : aVarArr) {
            aVar.a(c0012b);
        }
    }

    public synchronized void a(a aVar) {
        this.b.add(aVar);
    }

    synchronized void b() {
        this.b.clear();
    }

    int c() throws Throwable {
        f();
        if (this.c != null) {
            return this.c.d();
        }
        return 0;
    }

    public com.xiaomi.push.protobuf.a.C0011a d() throws Throwable {
        f();
        return this.c;
    }
}
