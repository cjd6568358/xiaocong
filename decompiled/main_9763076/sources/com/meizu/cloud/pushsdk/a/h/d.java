package com.meizu.cloud.pushsdk.a.h;

import com.tencent.android.tpush.common.Constants;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d implements Serializable, Comparable<d> {
    static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final d b = a(new byte[0]);
    private static final long serialVersionUID = 1;
    final byte[] c;
    transient int d;
    transient String e;

    d(byte[] bArr) {
        this.c = bArr;
    }

    public static d a(byte... bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("data == null");
        }
        return new d((byte[]) bArr.clone());
    }

    public static d a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        d dVar = new d(str.getBytes(n.a));
        dVar.e = str;
        return dVar;
    }

    public String a() {
        String str = this.e;
        if (str != null) {
            return str;
        }
        String str2 = new String(this.c, n.a);
        this.e = str2;
        return str2;
    }

    public d b() {
        return b("MD5");
    }

    private d b(String str) {
        try {
            return a(MessageDigest.getInstance(str).digest(this.c));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public String c() {
        char[] cArr = new char[this.c.length * 2];
        int i = 0;
        for (byte b2 : this.c) {
            int i2 = i + 1;
            cArr[i] = a[(b2 >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = a[b2 & 15];
        }
        return new String(cArr);
    }

    public static d a(InputStream inputStream, int i) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + i);
        }
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int i3 = inputStream.read(bArr, i2, i - i2);
            if (i3 == -1) {
                throw new EOFException();
            }
            i2 += i3;
        }
        return new d(bArr);
    }

    public byte a(int i) {
        return this.c[i];
    }

    public int d() {
        return this.c.length;
    }

    void a(a aVar) {
        aVar.c(this.c, 0, this.c.length);
    }

    public boolean a(int i, byte[] bArr, int i2, int i3) {
        return i <= this.c.length - i3 && i2 <= bArr.length - i3 && n.a(this.c, i, bArr, i2, i3);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof d) && ((d) obj).d() == this.c.length && ((d) obj).a(0, this.c, 0, this.c.length);
    }

    public int hashCode() {
        int i = this.d;
        if (i != 0) {
            return i;
        }
        int iHashCode = Arrays.hashCode(this.c);
        this.d = iHashCode;
        return iHashCode;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(d dVar) {
        int iD = d();
        int iD2 = dVar.d();
        int iMin = Math.min(iD, iD2);
        for (int i = 0; i < iMin; i++) {
            int iA = a(i) & Constants.NETWORK_TYPE_UNCONNECTED;
            int iA2 = dVar.a(i) & Constants.NETWORK_TYPE_UNCONNECTED;
            if (iA != iA2) {
                return iA < iA2 ? -1 : 1;
            }
        }
        if (iD == iD2) {
            return 0;
        }
        return iD >= iD2 ? 1 : -1;
    }

    public String toString() {
        if (this.c.length == 0) {
            return "ByteString[size=0]";
        }
        if (this.c.length <= 16) {
            return String.format("ByteString[size=%s data=%s]", Integer.valueOf(this.c.length), c());
        }
        return String.format("ByteString[size=%s md5=%s]", Integer.valueOf(this.c.length), b().c());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        d dVarA = a(objectInputStream, objectInputStream.readInt());
        try {
            Field declaredField = d.class.getDeclaredField("c");
            declaredField.setAccessible(true);
            declaredField.set(this, dVarA.c);
        } catch (IllegalAccessException e) {
            throw new AssertionError();
        } catch (NoSuchFieldException e2) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.c.length);
        objectOutputStream.write(this.c);
    }
}
