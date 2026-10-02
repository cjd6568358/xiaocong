package com.google.protobuf.micro;

import bsh.ParserConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class c {
    private final byte[] a;
    private final int b;
    private int c;
    private final OutputStream d;

    public static class a extends IOException {
        a() {
            super("CodedOutputStream was writing to a flat byte array and ran out of space.");
        }
    }

    private c(OutputStream outputStream, byte[] bArr) {
        this.d = outputStream;
        this.a = bArr;
        this.c = 0;
        this.b = bArr.length;
    }

    private c(byte[] bArr, int i, int i2) {
        this.d = null;
        this.a = bArr;
        this.c = i;
        this.b = i + i2;
    }

    public static c a(OutputStream outputStream) {
        return a(outputStream, 4096);
    }

    public static c a(OutputStream outputStream, int i) {
        return new c(outputStream, new byte[i]);
    }

    public static c a(byte[] bArr, int i, int i2) {
        return new c(bArr, i, i2);
    }

    public static int b(int i, com.google.protobuf.micro.a aVar) {
        return f(i) + b(aVar);
    }

    public static int b(int i, e eVar) {
        return f(i) + b(eVar);
    }

    public static int b(int i, String str) {
        return f(i) + b(str);
    }

    public static int b(int i, boolean z) {
        return f(i) + b(z);
    }

    public static int b(com.google.protobuf.micro.a aVar) {
        return h(aVar.a()) + aVar.a();
    }

    public static int b(e eVar) {
        int iB = eVar.b();
        return iB + h(iB);
    }

    public static int b(String str) {
        try {
            byte[] bytes = str.getBytes(HTTP.UTF_8);
            return bytes.length + h(bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.");
        }
    }

    public static int b(boolean z) {
        return 1;
    }

    public static int c(int i) {
        if (i >= 0) {
            return h(i);
        }
        return 10;
    }

    public static int c(int i, int i2) {
        return f(i) + c(i2);
    }

    public static int c(int i, long j) {
        return f(i) + c(j);
    }

    public static int c(long j) {
        return f(j);
    }

    public static int d(int i) {
        return h(i);
    }

    public static int d(int i, int i2) {
        return f(i) + d(i2);
    }

    public static int d(int i, long j) {
        return f(i) + d(j);
    }

    public static int d(long j) {
        return f(j);
    }

    private void d() throws IOException {
        if (this.d == null) {
            throw new a();
        }
        this.d.write(this.a, 0, this.c);
        this.c = 0;
    }

    public static int f(int i) {
        return h(f.a(i, 0));
    }

    public static int f(long j) {
        if (((-128) & j) == 0) {
            return 1;
        }
        if (((-16384) & j) == 0) {
            return 2;
        }
        if (((-2097152) & j) == 0) {
            return 3;
        }
        if (((-268435456) & j) == 0) {
            return 4;
        }
        if (((-34359738368L) & j) == 0) {
            return 5;
        }
        if (((-4398046511104L) & j) == 0) {
            return 6;
        }
        if (((-562949953421312L) & j) == 0) {
            return 7;
        }
        if (((-72057594037927936L) & j) == 0) {
            return 8;
        }
        return (Long.MIN_VALUE & j) == 0 ? 9 : 10;
    }

    public static int h(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & (-16384)) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return ((-268435456) & i) == 0 ? 4 : 5;
    }

    public void a() throws IOException {
        if (this.d != null) {
            d();
        }
    }

    public void a(byte b) throws IOException {
        if (this.c == this.b) {
            d();
        }
        byte[] bArr = this.a;
        int i = this.c;
        this.c = i + 1;
        bArr[i] = b;
    }

    public void a(int i) throws IOException {
        if (i >= 0) {
            g(i);
        } else {
            e(i);
        }
    }

    public void a(int i, int i2) throws IOException {
        e(i, 0);
        a(i2);
    }

    public void a(int i, long j) throws IOException {
        e(i, 0);
        a(j);
    }

    public void a(int i, com.google.protobuf.micro.a aVar) throws IOException {
        e(i, 2);
        a(aVar);
    }

    public void a(int i, e eVar) throws IOException {
        e(i, 2);
        a(eVar);
    }

    public void a(int i, String str) throws IOException {
        e(i, 2);
        a(str);
    }

    public void a(int i, boolean z) throws IOException {
        e(i, 0);
        a(z);
    }

    public void a(long j) throws IOException {
        e(j);
    }

    public void a(com.google.protobuf.micro.a aVar) throws IOException {
        byte[] bArrB = aVar.b();
        g(bArrB.length);
        a(bArrB);
    }

    public void a(e eVar) throws IOException {
        g(eVar.a());
        eVar.a(this);
    }

    public void a(String str) throws IOException {
        byte[] bytes = str.getBytes(HTTP.UTF_8);
        g(bytes.length);
        a(bytes);
    }

    public void a(boolean z) throws IOException {
        e(z ? 1 : 0);
    }

    public void a(byte[] bArr) throws IOException {
        b(bArr, 0, bArr.length);
    }

    public int b() {
        if (this.d == null) {
            return this.b - this.c;
        }
        throw new UnsupportedOperationException("spaceLeft() can only be called on CodedOutputStreams that are writing to a flat array.");
    }

    public void b(int i) throws IOException {
        g(i);
    }

    public void b(int i, int i2) throws IOException {
        e(i, 0);
        b(i2);
    }

    public void b(int i, long j) throws IOException {
        e(i, 0);
        b(j);
    }

    public void b(long j) throws IOException {
        e(j);
    }

    public void b(byte[] bArr, int i, int i2) throws IOException {
        if (this.b - this.c >= i2) {
            System.arraycopy(bArr, i, this.a, this.c, i2);
            this.c += i2;
            return;
        }
        int i3 = this.b - this.c;
        System.arraycopy(bArr, i, this.a, this.c, i3);
        int i4 = i + i3;
        int i5 = i2 - i3;
        this.c = this.b;
        d();
        if (i5 > this.b) {
            this.d.write(bArr, i4, i5);
        } else {
            System.arraycopy(bArr, i4, this.a, 0, i5);
            this.c = i5;
        }
    }

    public void c() {
        if (b() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
    }

    public void e(int i) throws IOException {
        a((byte) i);
    }

    public void e(int i, int i2) throws IOException {
        g(f.a(i, i2));
    }

    public void e(long j) throws IOException {
        while (((-128) & j) != 0) {
            e((((int) j) & ParserConstants.MODASSIGN) | ParserConstants.LSHIFTASSIGN);
            j >>>= 7;
        }
        e((int) j);
    }

    public void g(int i) throws IOException {
        while ((i & (-128)) != 0) {
            e((i & ParserConstants.MODASSIGN) | ParserConstants.LSHIFTASSIGN);
            i >>>= 7;
        }
        e(i);
    }
}
