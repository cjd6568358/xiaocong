package com.baidu.mobstat;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class da {
    public static boolean a(InputStream inputStream, OutputStream outputStream) {
        if (inputStream == null || outputStream == null) {
            return false;
        }
        byte[] bArr = new byte[4048];
        while (true) {
            try {
                int i = inputStream.read(bArr);
                if (i != -1) {
                    outputStream.write(bArr, 0, i);
                } else {
                    return true;
                }
            } catch (IOException e) {
                db.b(e);
                return false;
            }
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
                db.b(th);
            }
        }
    }
}
