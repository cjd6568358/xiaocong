package com.tencent.android.tpush.service.channel.c;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.exception.IORefusedException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    static final /* synthetic */ boolean a;

    static {
        a = !e.class.desiredAssertionStatus();
    }

    public static boolean a(InputStream inputStream, int i) {
        return inputStream.available() >= i;
    }

    public static short a(InputStream inputStream) throws IOException {
        if (!a(inputStream, 1)) {
            throw new IORefusedException("inputstream cannot read 1 byte");
        }
        byte[] bArr = new byte[1];
        if (inputStream.read(bArr) == -1) {
            throw new IOException("the end of stream has been reached!");
        }
        return (short) (bArr[0] & Constants.NETWORK_TYPE_UNCONNECTED);
    }

    public static long b(InputStream inputStream) throws IOException {
        if (!a(inputStream, 4)) {
            throw new IORefusedException("inputstream cannot read 4 byte");
        }
        byte[] bArr = new byte[4];
        if (inputStream.read(bArr) == -1) {
            throw new IOException("the end of stream has been reached!");
        }
        return ((bArr[0] & Constants.NETWORK_TYPE_UNCONNECTED) << 24) | (bArr[3] & Constants.NETWORK_TYPE_UNCONNECTED) | ((bArr[2] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | ((bArr[1] & Constants.NETWORK_TYPE_UNCONNECTED) << 16);
    }

    public static int c(InputStream inputStream) throws IOException {
        if (!a(inputStream, 4)) {
            throw new IORefusedException("inputstream cannot read 4 byte");
        }
        byte[] bArr = new byte[4];
        if (inputStream.read(bArr) == -1) {
            throw new IOException("the end of stream has been reached!");
        }
        return ((bArr[0] & Constants.NETWORK_TYPE_UNCONNECTED) << 24) | (bArr[3] & Constants.NETWORK_TYPE_UNCONNECTED) | ((bArr[2] & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | ((bArr[1] & Constants.NETWORK_TYPE_UNCONNECTED) << 16);
    }

    public static int a(InputStream inputStream, byte[] bArr, int i) throws IOException {
        if (inputStream.available() == 0 && bArr.length - i > 0) {
            return 0;
        }
        int length = bArr.length - i < inputStream.available() ? bArr.length - i : inputStream.available();
        if (length > 0) {
            int i2 = inputStream.read(bArr, i, length);
            if (i2 == -1) {
                throw new IOException("the end of stream has been reached!");
            }
            return i2;
        }
        return length;
    }

    public static int a(OutputStream outputStream, int i) throws IOException {
        if (!a && (i < 0 || i > 255)) {
            throw new AssertionError();
        }
        outputStream.write((byte) (i & 255));
        return 1;
    }

    public static int a(OutputStream outputStream, long j) throws IOException {
        if (!a && (j < 0 || j > 4294967295L)) {
            throw new AssertionError();
        }
        outputStream.write(new byte[]{(byte) ((j >> 24) & 255), (byte) ((j >> 16) & 255), (byte) ((j >> 8) & 255), (byte) (j & 255)});
        return 4;
    }

    public static int b(OutputStream outputStream, int i) throws IOException {
        outputStream.write(new byte[]{(byte) ((i >> 24) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 8) & 255), (byte) (i & 255)});
        return 4;
    }

    public static int a(OutputStream outputStream, byte[] bArr) throws IOException {
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            outputStream.write(bArr, i2, 1);
            i++;
        }
        return i;
    }
}
