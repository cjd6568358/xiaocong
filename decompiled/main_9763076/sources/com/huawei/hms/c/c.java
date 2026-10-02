package com.huawei.hms.c;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: compiled from: IOUtils.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class c {
    public static void a(InputStream inputStream) {
        a((Closeable) inputStream);
    }

    public static void a(OutputStream outputStream) {
        a((Closeable) outputStream);
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                com.huawei.hms.support.log.a.d("IOUtils", "An exception occurred while closing the 'Closeable' object.");
            }
        }
    }

    public static InputStream a(byte[] bArr) throws IOException {
        return new ByteArrayInputStream(bArr);
    }
}
