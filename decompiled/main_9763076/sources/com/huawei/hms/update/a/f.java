package com.huawei.hms.update.a;

import android.content.Context;
import android.os.Environment;
import com.fasterxml.jackson.core.util.BufferRecycler;
import com.huawei.hms.update.provider.UpdateProvider;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: compiled from: OtaUpdateDownload.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f implements com.huawei.hms.update.a.a.a {
    private final Context a;
    private com.huawei.hms.update.a.a.b c;
    private File d;
    private final com.huawei.hms.update.b.d b = new com.huawei.hms.update.b.b();
    private final c e = new c();

    public f(Context context) {
        this.a = context.getApplicationContext();
    }

    private synchronized void b(com.huawei.hms.update.a.a.b bVar) {
        this.c = bVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(int i, int i2, int i3) {
        if (this.c != null) {
            this.c.a(i, i2, i3, this.d);
        }
    }

    @Override // com.huawei.hms.update.a.a.a
    public Context a() {
        return this.a;
    }

    @Override // com.huawei.hms.update.a.a.a
    public void b() {
        com.huawei.hms.support.log.a.b("OtaUpdateDownload", "Enter cancel.");
        b(null);
        this.b.b();
    }

    @Override // com.huawei.hms.update.a.a.a
    public void a(com.huawei.hms.update.a.a.b bVar) {
        throw new IllegalStateException("Not supported.");
    }

    @Override // com.huawei.hms.update.a.a.a
    public void a(com.huawei.hms.update.a.a.b bVar, com.huawei.hms.update.a.a.c cVar) {
        com.huawei.hms.c.a.a(bVar, "callback must not be null.");
        com.huawei.hms.support.log.a.b("OtaUpdateDownload", "Enter downloadPackage.");
        b(bVar);
        if (cVar == null || !cVar.a()) {
            com.huawei.hms.support.log.a.d("OtaUpdateDownload", "In downloadPackage, Invalid update info.");
            a(PushConstants.ONTIME_NOTIFICATION, 0, 0);
            return;
        }
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            com.huawei.hms.support.log.a.d("OtaUpdateDownload", "In downloadPackage, Invalid external storage for downloading file.");
            a(2204, 0, 0);
            return;
        }
        this.d = UpdateProvider.getLocalFile(this.a, "hms/HwMobileService.apk");
        if (this.d == null) {
            com.huawei.hms.support.log.a.d("OtaUpdateDownload", "In downloadPackage, Failed to get local file for downloading.");
            a(2204, 0, 0);
            return;
        }
        File parentFile = this.d.getParentFile();
        if (parentFile == null || !(parentFile.mkdirs() || parentFile.isDirectory())) {
            com.huawei.hms.support.log.a.d("OtaUpdateDownload", "In downloadPackage, Failed to create directory for downloading file.");
            a(PushConstants.ONTIME_NOTIFICATION, 0, 0);
        } else if (parentFile.getUsableSpace() < cVar.c * 3) {
            com.huawei.hms.support.log.a.d("OtaUpdateDownload", "In downloadPackage, No space for downloading file.");
            a(2203, 0, 0);
        } else {
            try {
                a(cVar);
            } catch (com.huawei.hms.update.b.a e) {
                com.huawei.hms.support.log.a.c("OtaUpdateDownload", "In downloadPackage, Canceled to download the update file.");
                a(2101, 0, 0);
            }
        }
    }

    private static boolean a(String str, File file) throws Throwable {
        byte[] bArrA = com.huawei.hms.c.f.a(file);
        if (bArrA != null) {
            return com.huawei.hms.c.b.b(bArrA, true).equalsIgnoreCase(str);
        }
        return false;
    }

    void a(com.huawei.hms.update.a.a.c cVar) throws com.huawei.hms.update.b.a {
        h hVarA;
        com.huawei.hms.support.log.a.b("OtaUpdateDownload", "Enter downloadPackage.");
        try {
            try {
                this.e.a(a());
                if (!this.e.b(cVar.b, cVar.c, cVar.d)) {
                    this.e.a(cVar.b, cVar.c, cVar.d);
                    hVarA = a(this.d, cVar.c);
                } else if (this.e.b() != this.e.a()) {
                    hVarA = a(this.d, cVar.c);
                    hVarA.a(this.e.b());
                } else {
                    if (a(cVar.d, this.d)) {
                        a(BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN, 0, 0);
                        this.b.a();
                        com.huawei.hms.c.c.a((OutputStream) null);
                        return;
                    }
                    this.e.a(cVar.b, cVar.c, cVar.d);
                    hVarA = a(this.d, cVar.c);
                }
                int iA = this.b.a(cVar.b, hVarA, this.e.b(), this.e.a());
                if (iA != 200 && iA != 206) {
                    com.huawei.hms.support.log.a.d("OtaUpdateDownload", "In DownloadHelper.downloadPackage, Download the package, HTTP code: " + iA);
                    a(PushConstants.ONTIME_NOTIFICATION, 0, 0);
                    this.b.a();
                    com.huawei.hms.c.c.a((OutputStream) hVarA);
                    return;
                }
                if (a(cVar.d, this.d)) {
                    a(BufferRecycler.DEFAULT_WRITE_CONCAT_BUFFER_LEN, 0, 0);
                    this.b.a();
                    com.huawei.hms.c.c.a((OutputStream) hVarA);
                } else {
                    a(PushConstants.DELAY_NOTIFICATION, 0, 0);
                    this.b.a();
                    com.huawei.hms.c.c.a((OutputStream) hVarA);
                }
            } catch (IOException e) {
                com.huawei.hms.support.log.a.d("OtaUpdateDownload", "In DownloadHelper.downloadPackage, Failed to download." + e.getMessage());
                a(PushConstants.ONTIME_NOTIFICATION, 0, 0);
                this.b.a();
                com.huawei.hms.c.c.a((OutputStream) null);
            }
        } catch (Throwable th) {
            this.b.a();
            com.huawei.hms.c.c.a((OutputStream) null);
            throw th;
        }
    }

    private h a(File file, int i) throws IOException {
        return new g(this, file, i, i);
    }
}
