package com.tencent.android.tpush.service.channel.security;

import bsh.ParserConstants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.protocol.HTTP;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d extends FilterInputStream {
    private static final char[] a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final int[] b = new int[ParserConstants.LSHIFTASSIGN];
    private int c;
    private int d;

    static {
        for (int i = 0; i < 64; i++) {
            b[a[i]] = i;
        }
    }

    public d(InputStream inputStream) {
        super(inputStream);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i;
        do {
            i = this.in.read();
            if (i == -1) {
                return -1;
            }
        } while (Character.isWhitespace((char) i));
        this.c++;
        if (i == 61) {
            return -1;
        }
        int i2 = b[i];
        int i3 = (this.c - 1) % 4;
        if (i3 == 0) {
            this.d = i2 & 63;
            return read();
        }
        if (i3 == 1) {
            int i4 = ((this.d << 2) + (i2 >> 4)) & 255;
            this.d = i2 & 15;
            return i4;
        }
        if (i3 == 2) {
            int i5 = ((this.d << 4) + (i2 >> 2)) & 255;
            this.d = i2 & 3;
            return i5;
        }
        if (i3 == 3) {
            return ((this.d << 6) + i2) & 255;
        }
        return -1;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (bArr.length < (i2 + i) - 1) {
            throw new IOException("The input buffer is too small: " + i2 + " bytes requested starting at offset " + i + " while the buffer  is only " + bArr.length + " bytes long.");
        }
        int i3 = 0;
        while (i3 < i2) {
            int i4 = read();
            if (i4 == -1 && i3 == 0) {
                return -1;
            }
            if (i4 == -1) {
                break;
            }
            bArr[i + i3] = (byte) i4;
            i3++;
        }
        return i3;
    }

    public static byte[] a(String str) {
        byte[] bytes = new byte[0];
        try {
            bytes = str.getBytes(HTTP.UTF_8);
        } catch (Exception e) {
        }
        d dVar = new d(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) (((double) bytes.length) * 0.67d));
        try {
            byte[] bArr = new byte[4096];
            while (true) {
                int i = dVar.read(bArr);
                if (i != -1) {
                    byteArrayOutputStream.write(bArr, 0, i);
                } else {
                    dVar.close();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e2) {
            return null;
        }
    }
}
