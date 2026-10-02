package com.tencent.android.tpush.service.channel.security;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e extends FilterOutputStream {
    private static final char[] a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private int b;
    private int c;

    public e(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i) throws IOException {
        if (i < 0) {
            i += 256;
        }
        if (this.b % 3 == 0) {
            this.c = i & 3;
            this.out.write(a[i >> 2]);
        } else if (this.b % 3 == 1) {
            int i2 = ((this.c << 4) + (i >> 4)) & 63;
            this.c = i & 15;
            this.out.write(a[i2]);
        } else if (this.b % 3 == 2) {
            this.out.write(a[((this.c << 2) + (i >> 6)) & 63]);
            this.out.write(a[i & 63]);
            this.c = 0;
        }
        this.b++;
        if (this.b % 57 == 0) {
            this.out.write(10);
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        for (int i3 = 0; i3 < i2; i3++) {
            write(bArr[i + i3]);
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.b % 3 == 1) {
            this.out.write(a[(this.c << 4) & 63]);
            this.out.write(61);
            this.out.write(61);
        } else if (this.b % 3 == 2) {
            this.out.write(a[(this.c << 2) & 63]);
            this.out.write(61);
        }
        super.close();
    }

    public static String a(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) (((double) bArr.length) * 1.37d));
        e eVar = new e(byteArrayOutputStream);
        try {
            eVar.write(bArr);
            eVar.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toString(HTTP.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }
}
