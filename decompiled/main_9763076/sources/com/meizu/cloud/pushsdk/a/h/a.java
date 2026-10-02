package com.meizu.cloud.pushsdk.a.h;

import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a implements b, c, Cloneable {
    private static final byte[] c = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    i a;
    long b;

    public long a() {
        return this.b;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.b
    public a b() {
        return this;
    }

    public boolean c() {
        return this.b == 0;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.c
    public InputStream d() {
        return new InputStream() { // from class: com.meizu.cloud.pushsdk.a.h.a.1
            @Override // java.io.InputStream
            public int read() {
                if (a.this.b > 0) {
                    return a.this.f() & Constants.NETWORK_TYPE_UNCONNECTED;
                }
                return -1;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) {
                return a.this.a(bArr, i, i2);
            }

            @Override // java.io.InputStream
            public int available() {
                return (int) Math.min(a.this.b, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            public String toString() {
                return a.this + ".inputStream()";
            }
        };
    }

    public long e() {
        long j = this.b;
        if (j == 0) {
            return 0L;
        }
        i iVar = this.a.g;
        if (iVar.c < 2048 && iVar.e) {
            return j - ((long) (iVar.c - iVar.b));
        }
        return j;
    }

    public byte f() {
        if (this.b == 0) {
            throw new IllegalStateException("size == 0");
        }
        i iVar = this.a;
        int i = iVar.b;
        int i2 = iVar.c;
        int i3 = i + 1;
        byte b = iVar.a[i];
        this.b--;
        if (i3 == i2) {
            this.a = iVar.a();
            j.a(iVar);
        } else {
            iVar.b = i3;
        }
        return b;
    }

    public d g() {
        return new d(i());
    }

    @Override // com.meizu.cloud.pushsdk.a.h.c
    public String h() {
        try {
            return a(this.b, n.a);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public String a(long j, Charset charset) throws EOFException {
        n.a(this.b, 0L, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        }
        if (j == 0) {
            return Constants.MAIN_VERSION_TAG;
        }
        i iVar = this.a;
        if (((long) iVar.b) + j > iVar.c) {
            return new String(a(j), charset);
        }
        String str = new String(iVar.a, iVar.b, (int) j, charset);
        iVar.b = (int) (((long) iVar.b) + j);
        this.b -= j;
        if (iVar.b == iVar.c) {
            this.a = iVar.a();
            j.a(iVar);
            return str;
        }
        return str;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.c
    public byte[] i() {
        try {
            return a(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public byte[] a(long j) throws EOFException {
        n.a(this.b, 0L, j);
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        }
        byte[] bArr = new byte[(int) j];
        a(bArr);
        return bArr;
    }

    public void a(byte[] bArr) throws EOFException {
        int i = 0;
        while (i < bArr.length) {
            int iA = a(bArr, i, bArr.length - i);
            if (iA == -1) {
                throw new EOFException();
            }
            i += iA;
        }
    }

    public int a(byte[] bArr, int i, int i2) {
        n.a(bArr.length, i, i2);
        i iVar = this.a;
        if (iVar == null) {
            return -1;
        }
        int iMin = Math.min(i2, iVar.c - iVar.b);
        System.arraycopy(iVar.a, iVar.b, bArr, i, iMin);
        iVar.b += iMin;
        this.b -= (long) iMin;
        if (iVar.b == iVar.c) {
            this.a = iVar.a();
            j.a(iVar);
            return iMin;
        }
        return iMin;
    }

    public void j() {
        try {
            b(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public void b(long j) throws EOFException {
        while (j > 0) {
            if (this.a == null) {
                throw new EOFException();
            }
            int iMin = (int) Math.min(j, this.a.c - this.a.b);
            this.b -= (long) iMin;
            j -= (long) iMin;
            i iVar = this.a;
            iVar.b = iMin + iVar.b;
            if (this.a.b == this.a.c) {
                i iVar2 = this.a;
                this.a = iVar2.a();
                j.a(iVar2);
            }
        }
    }

    @Override // com.meizu.cloud.pushsdk.a.h.b
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public a b(d dVar) {
        if (dVar == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        dVar.a(this);
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.b
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public a b(String str) {
        return a(str, 0, str.length());
    }

    public a a(String str, int i, int i2) {
        int i3;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i);
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        }
        if (i2 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i2 + " > " + str.length());
        }
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt < 128) {
                i iVarC = c(1);
                byte[] bArr = iVarC.a;
                int i4 = iVarC.c - i;
                int iMin = Math.min(i2, 2048 - i4);
                i3 = i + 1;
                bArr[i4 + i] = (byte) cCharAt;
                while (i3 < iMin) {
                    char cCharAt2 = str.charAt(i3);
                    if (cCharAt2 >= 128) {
                        break;
                    }
                    bArr[i3 + i4] = (byte) cCharAt2;
                    i3++;
                }
                int i5 = (i3 + i4) - iVarC.c;
                iVarC.c += i5;
                this.b += (long) i5;
            } else if (cCharAt < 2048) {
                b((cCharAt >> 6) | 192);
                b((cCharAt & '?') | ParserConstants.LSHIFTASSIGN);
                i3 = i + 1;
            } else if (cCharAt < 55296 || cCharAt > 57343) {
                b((cCharAt >> '\f') | 224);
                b(((cCharAt >> 6) & 63) | ParserConstants.LSHIFTASSIGN);
                b((cCharAt & '?') | ParserConstants.LSHIFTASSIGN);
                i3 = i + 1;
            } else {
                char cCharAt3 = i + 1 < i2 ? str.charAt(i + 1) : (char) 0;
                if (cCharAt > 56319 || cCharAt3 < 56320 || cCharAt3 > 57343) {
                    b(63);
                    i++;
                } else {
                    int i6 = ((cCharAt3 & 9215) | ((cCharAt & 10239) << 10)) + 65536;
                    b((i6 >> 18) | 240);
                    b(((i6 >> 12) & 63) | ParserConstants.LSHIFTASSIGN);
                    b(((i6 >> 6) & 63) | ParserConstants.LSHIFTASSIGN);
                    b((i6 & 63) | ParserConstants.LSHIFTASSIGN);
                    i3 = i + 2;
                }
            }
            i = i3;
        }
        return this;
    }

    public a a(int i) {
        if (i < 128) {
            b(i);
        } else if (i < 2048) {
            b((i >> 6) | 192);
            b((i & 63) | ParserConstants.LSHIFTASSIGN);
        } else if (i < 65536) {
            if (i >= 55296 && i <= 57343) {
                throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
            }
            b((i >> 12) | 224);
            b(((i >> 6) & 63) | ParserConstants.LSHIFTASSIGN);
            b((i & 63) | ParserConstants.LSHIFTASSIGN);
        } else if (i <= 1114111) {
            b((i >> 18) | 240);
            b(((i >> 12) & 63) | ParserConstants.LSHIFTASSIGN);
            b(((i >> 6) & 63) | ParserConstants.LSHIFTASSIGN);
            b((i & 63) | ParserConstants.LSHIFTASSIGN);
        } else {
            throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
        }
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.b
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public a c(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        return c(bArr, 0, bArr.length);
    }

    @Override // com.meizu.cloud.pushsdk.a.h.b
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public a c(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        n.a(bArr.length, i, i2);
        int i3 = i + i2;
        while (i < i3) {
            i iVarC = c(1);
            int iMin = Math.min(i3 - i, 2048 - iVarC.c);
            System.arraycopy(bArr, i, iVarC.a, iVarC.c, iMin);
            i += iMin;
            iVarC.c = iMin + iVarC.c;
        }
        this.b += (long) i2;
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.b
    public long a(l lVar) throws IOException {
        if (lVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long jB = lVar.b(this, ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLSX);
            if (jB != -1) {
                j += jB;
            } else {
                return j;
            }
        }
    }

    public a b(int i) {
        i iVarC = c(1);
        byte[] bArr = iVarC.a;
        int i2 = iVarC.c;
        iVarC.c = i2 + 1;
        bArr[i2] = (byte) i;
        this.b++;
        return this;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.b
    /* JADX INFO: renamed from: c, reason: merged with bridge method [inline-methods] */
    public a e(long j) {
        boolean z;
        long j2;
        int i;
        if (j == 0) {
            return b(48);
        }
        if (j >= 0) {
            z = false;
            j2 = j;
        } else {
            j2 = -j;
            if (j2 < 0) {
                return b("-9223372036854775808");
            }
            z = true;
        }
        if (j2 < 100000000) {
            if (j2 < 10000) {
                if (j2 < 100) {
                    i = j2 < 10 ? 1 : 2;
                } else {
                    i = j2 < 1000 ? 3 : 4;
                }
            } else if (j2 < 1000000) {
                i = j2 < 100000 ? 5 : 6;
            } else {
                i = j2 < 10000000 ? 7 : 8;
            }
        } else if (j2 < 1000000000000L) {
            if (j2 < 10000000000L) {
                i = j2 < 1000000000 ? 9 : 10;
            } else {
                i = j2 < 100000000000L ? 11 : 12;
            }
        } else if (j2 < 1000000000000000L) {
            if (j2 < 10000000000000L) {
                i = 13;
            } else {
                i = j2 < 100000000000000L ? 14 : 15;
            }
        } else if (j2 < 100000000000000000L) {
            i = j2 < 10000000000000000L ? 16 : 17;
        } else {
            i = j2 < 1000000000000000000L ? 18 : 19;
        }
        if (z) {
            i++;
        }
        i iVarC = c(i);
        byte[] bArr = iVarC.a;
        int i2 = iVarC.c + i;
        while (j2 != 0) {
            i2--;
            bArr[i2] = c[(int) (j2 % 10)];
            j2 /= 10;
        }
        if (z) {
            bArr[i2 - 1] = 45;
        }
        iVarC.c += i;
        this.b = ((long) i) + this.b;
        return this;
    }

    public a d(long j) {
        if (j == 0) {
            return b(48);
        }
        int iNumberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        i iVarC = c(iNumberOfTrailingZeros);
        byte[] bArr = iVarC.a;
        int i = iVarC.c;
        for (int i2 = (iVarC.c + iNumberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = c[(int) (15 & j)];
            j >>>= 4;
        }
        iVarC.c += iNumberOfTrailingZeros;
        this.b = ((long) iNumberOfTrailingZeros) + this.b;
        return this;
    }

    i c(int i) {
        if (i < 1 || i > 2048) {
            throw new IllegalArgumentException();
        }
        if (this.a == null) {
            this.a = j.a();
            i iVar = this.a;
            i iVar2 = this.a;
            i iVar3 = this.a;
            iVar2.g = iVar3;
            iVar.f = iVar3;
            return iVar3;
        }
        i iVar4 = this.a.g;
        if (iVar4.c + i > 2048 || !iVar4.e) {
            return iVar4.a(j.a());
        }
        return iVar4;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.k
    public void a(a aVar, long j) {
        if (aVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (aVar == this) {
            throw new IllegalArgumentException("source == this");
        }
        n.a(aVar.b, 0L, j);
        while (j > 0) {
            if (j < aVar.a.c - aVar.a.b) {
                i iVar = this.a != null ? this.a.g : null;
                if (iVar != null && iVar.e) {
                    if ((((long) iVar.c) + j) - ((long) (iVar.d ? 0 : iVar.b)) <= ConstantsAPI.AppSupportContentFlag.MMAPP_SUPPORT_XLSX) {
                        aVar.a.a(iVar, (int) j);
                        aVar.b -= j;
                        this.b += j;
                        return;
                    }
                }
                aVar.a = aVar.a.a((int) j);
            }
            i iVar2 = aVar.a;
            long j2 = iVar2.c - iVar2.b;
            aVar.a = iVar2.a();
            if (this.a == null) {
                this.a = iVar2;
                i iVar3 = this.a;
                i iVar4 = this.a;
                i iVar5 = this.a;
                iVar4.g = iVar5;
                iVar3.f = iVar5;
            } else {
                this.a.g.a(iVar2).b();
            }
            aVar.b -= j2;
            this.b += j2;
            j -= j2;
        }
    }

    @Override // com.meizu.cloud.pushsdk.a.h.l
    public long b(a aVar, long j) {
        if (aVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.b == 0) {
            return -1L;
        }
        if (j > this.b) {
            j = this.b;
        }
        aVar.a(this, j);
        return j;
    }

    @Override // com.meizu.cloud.pushsdk.a.h.k, java.io.Flushable
    public void flush() {
    }

    @Override // com.meizu.cloud.pushsdk.a.h.k, java.io.Closeable, java.lang.AutoCloseable, com.meizu.cloud.pushsdk.a.h.l
    public void close() {
    }

    public boolean equals(Object obj) {
        long j = 0;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof a)) {
            return false;
        }
        a aVar = (a) obj;
        if (this.b != aVar.b) {
            return false;
        }
        if (this.b == 0) {
            return true;
        }
        i iVar = this.a;
        i iVar2 = aVar.a;
        int i = iVar.b;
        int i2 = iVar2.b;
        while (j < this.b) {
            long jMin = Math.min(iVar.c - i, iVar2.c - i2);
            int i3 = 0;
            while (i3 < jMin) {
                int i4 = i + 1;
                byte b = iVar.a[i];
                int i5 = i2 + 1;
                if (b != iVar2.a[i2]) {
                    return false;
                }
                i3++;
                i2 = i5;
                i = i4;
            }
            if (i == iVar.c) {
                iVar = iVar.f;
                i = iVar.b;
            }
            if (i2 == iVar2.c) {
                iVar2 = iVar2.f;
                i2 = iVar2.b;
            }
            j += jMin;
        }
        return true;
    }

    public int hashCode() {
        i iVar = this.a;
        if (iVar == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = iVar.b;
            int i3 = iVar.c;
            while (i2 < i3) {
                int i4 = iVar.a[i2] + (i * 31);
                i2++;
                i = i4;
            }
            iVar = iVar.f;
        } while (iVar != this.a);
        return i;
    }

    public String toString() {
        if (this.b == 0) {
            return "Buffer[size=0]";
        }
        if (this.b <= 16) {
            return String.format("Buffer[size=%s data=%s]", Long.valueOf(this.b), clone().g().c());
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(this.a.a, this.a.b, this.a.c - this.a.b);
            for (i iVar = this.a.f; iVar != this.a; iVar = iVar.f) {
                messageDigest.update(iVar.a, iVar.b, iVar.c - iVar.b);
            }
            return String.format("Buffer[size=%s md5=%s]", Long.valueOf(this.b), d.a(messageDigest.digest()).c());
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: renamed from: k, reason: merged with bridge method [inline-methods] */
    public a clone() {
        a aVar = new a();
        if (this.b == 0) {
            return aVar;
        }
        aVar.a = new i(this.a);
        i iVar = aVar.a;
        i iVar2 = aVar.a;
        i iVar3 = aVar.a;
        iVar2.g = iVar3;
        iVar.f = iVar3;
        for (i iVar4 = this.a.f; iVar4 != this.a; iVar4 = iVar4.f) {
            aVar.a.g.a(new i(iVar4));
        }
        aVar.b = this.b;
        return aVar;
    }
}
