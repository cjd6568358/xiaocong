package com.huawei.hms.update.a;

import android.content.Context;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: OtaUpdateCheck.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e implements com.huawei.hms.update.a.a.a {
    private final Context a;
    private final com.huawei.hms.update.b.d b = new com.huawei.hms.update.b.b();
    private com.huawei.hms.update.a.a.b c;
    private String d;
    private com.huawei.hms.update.a.a.c e;

    public e(Context context) {
        this.a = context.getApplicationContext();
    }

    private synchronized void b(com.huawei.hms.update.a.a.b bVar) {
        this.c = bVar;
    }

    private synchronized void a(int i) {
        if (this.c != null) {
            this.c.a(i, this.e);
        }
    }

    @Override // com.huawei.hms.update.a.a.a
    public Context a() {
        return this.a;
    }

    @Override // com.huawei.hms.update.a.a.a
    public void b() {
        com.huawei.hms.support.log.a.b("OtaUpdateCheck", "Enter cancel.");
        b(null);
        this.b.b();
    }

    @Override // com.huawei.hms.update.a.a.a
    public void a(com.huawei.hms.update.a.a.b bVar) throws Throwable {
        com.huawei.hms.c.a.a(bVar, "callback must not be null.");
        com.huawei.hms.support.log.a.b("OtaUpdateCheck", "Enter checkUpdate.");
        b(bVar);
        this.e = new com.huawei.hms.update.a.a.c();
        this.e.a(this.a);
        if (this.e.a() && this.e.a >= 20502300) {
            a(1000);
            return;
        }
        try {
            c();
        } catch (com.huawei.hms.update.b.a e) {
            com.huawei.hms.support.log.a.c("OtaUpdateCheck", "In checkUpdate, Canceled to download the update file.");
            a(1101);
        }
    }

    @Override // com.huawei.hms.update.a.a.a
    public void a(com.huawei.hms.update.a.a.b bVar, com.huawei.hms.update.a.a.c cVar) {
        throw new IllegalStateException("Not supported.");
    }

    private void c() throws Throwable {
        com.huawei.hms.support.log.a.b("OtaUpdateCheck", "Enter checkUpdate.");
        try {
            int iD = d();
            if (iD != 200) {
                com.huawei.hms.support.log.a.d("OtaUpdateCheck", "In CheckUpdateHelper.checkUpdate, Check whether has a new version, HTTP code: " + iD);
                a(1201);
            } else if (this.d == null) {
                a(1202);
            } else {
                int iE = e();
                if (iE != 200) {
                    com.huawei.hms.support.log.a.d("OtaUpdateCheck", "In CheckUpdateHelper.checkUpdate, Request the update-info of the new version, HTTP code: " + iE);
                    a(1201);
                } else if (this.e == null || this.e.a < 20502300) {
                    a(1203);
                } else {
                    this.e.b(this.a);
                    a(1000);
                }
            }
        } catch (IOException e) {
            com.huawei.hms.support.log.a.d("OtaUpdateCheck", "In CheckUpdateHelper.checkUpdate, Failed to check update." + e.getMessage());
            a(1201);
        }
    }

    private int d() throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayInputStream byteArrayInputStream = null;
        a aVar = new a(this.a);
        if (com.huawei.hms.support.log.a.a()) {
            com.huawei.hms.support.log.a.a("OtaUpdateCheck", "In doCheckUpdate, Check update params: " + aVar.toString());
        }
        try {
            ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(aVar.a().toString().getBytes(Charset.defaultCharset()));
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    int iA = this.b.a("https://query.hicloud.com/hwid/v2/CheckEx.action", byteArrayInputStream2, byteArrayOutputStream);
                    if (iA != 200) {
                        com.huawei.hms.c.c.a((OutputStream) byteArrayOutputStream);
                        com.huawei.hms.c.c.a((InputStream) byteArrayInputStream2);
                        this.b.a();
                    } else {
                        String str = new String(byteArrayOutputStream.toByteArray(), HTTP.UTF_8);
                        if (com.huawei.hms.support.log.a.a()) {
                            com.huawei.hms.support.log.a.a("OtaUpdateCheck", "In CheckUpdateHelper.doCheckUpdate, Check update response: " + str);
                        }
                        this.d = b.a(str).a();
                        com.huawei.hms.c.c.a((OutputStream) byteArrayOutputStream);
                        com.huawei.hms.c.c.a((InputStream) byteArrayInputStream2);
                        this.b.a();
                    }
                    return iA;
                } catch (Throwable th) {
                    th = th;
                    byteArrayInputStream = byteArrayInputStream2;
                    com.huawei.hms.c.c.a((OutputStream) byteArrayOutputStream);
                    com.huawei.hms.c.c.a((InputStream) byteArrayInputStream);
                    this.b.a();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                byteArrayOutputStream = null;
                byteArrayInputStream = byteArrayInputStream2;
            }
        } catch (Throwable th3) {
            th = th3;
            byteArrayOutputStream = null;
        }
    }

    private int e() throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                int iA = this.b.a(this.d + "full/filelist.xml", byteArrayOutputStream);
                if (iA != 200) {
                    com.huawei.hms.c.c.a((OutputStream) byteArrayOutputStream);
                    this.b.a();
                } else {
                    String str = new String(byteArrayOutputStream.toByteArray(), HTTP.UTF_8);
                    if (com.huawei.hms.support.log.a.a()) {
                        com.huawei.hms.support.log.a.a("OtaUpdateCheck", "In doGetFilelist, Check update response: " + str);
                    }
                    d dVarA = d.a(str);
                    this.e = new com.huawei.hms.update.a.a.c(dVarA.d(), this.d + "full/" + dVarA.a(), dVarA.b(), dVarA.c());
                    com.huawei.hms.c.c.a((OutputStream) byteArrayOutputStream);
                    this.b.a();
                }
                return iA;
            } catch (Throwable th) {
                th = th;
                com.huawei.hms.c.c.a((OutputStream) byteArrayOutputStream);
                this.b.a();
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
        }
    }
}
