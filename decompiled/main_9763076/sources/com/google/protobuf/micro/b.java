package com.google.protobuf.micro;

import com.tencent.android.tpush.common.Constants;
import java.io.InputStream;
import java.util.Vector;
import org.apache.http.protocol.HTTP;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class b {
    private final byte[] a;
    private int b;
    private int c;
    private int d;
    private final InputStream e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;

    private b(InputStream inputStream) {
        this.h = Integer.MAX_VALUE;
        this.j = 64;
        this.k = 67108864;
        this.a = new byte[4096];
        this.b = 0;
        this.d = 0;
        this.e = inputStream;
    }

    private b(byte[] bArr, int i, int i2) {
        this.h = Integer.MAX_VALUE;
        this.j = 64;
        this.k = 67108864;
        this.a = bArr;
        this.b = i + i2;
        this.d = i;
        this.e = null;
    }

    public static b a(InputStream inputStream) {
        return new b(inputStream);
    }

    public static b a(byte[] bArr, int i, int i2) {
        return new b(bArr, i, i2);
    }

    private boolean a(boolean z) throws d {
        if (this.d < this.b) {
            throw new IllegalStateException("refillBuffer() called when buffer wasn't empty.");
        }
        if (this.g + this.b == this.h) {
            if (z) {
                throw d.a();
            }
            return false;
        }
        this.g += this.b;
        this.d = 0;
        this.b = this.e == null ? -1 : this.e.read(this.a);
        if (this.b == 0 || this.b < -1) {
            throw new IllegalStateException("InputStream#read(byte[]) returned invalid result: " + this.b + "\nThe InputStream implementation is buggy.");
        }
        if (this.b == -1) {
            this.b = 0;
            if (z) {
                throw d.a();
            }
            return false;
        }
        p();
        int i = this.g + this.b + this.c;
        if (i > this.k || i < 0) {
            throw d.h();
        }
        return true;
    }

    private void p() {
        this.b += this.c;
        int i = this.g + this.b;
        if (i <= this.h) {
            this.c = 0;
        } else {
            this.c = i - this.h;
            this.b -= this.c;
        }
    }

    public int a() throws d {
        if (n()) {
            this.f = 0;
            return 0;
        }
        this.f = j();
        if (this.f == 0) {
            throw d.d();
        }
        return this.f;
    }

    public void a(int i) throws d {
        if (this.f != i) {
            throw d.e();
        }
    }

    public void a(e eVar) throws d {
        int iJ = j();
        if (this.i >= this.j) {
            throw d.g();
        }
        int iC = c(iJ);
        this.i++;
        eVar.a(this);
        a(0);
        this.i--;
        d(iC);
    }

    public void b() throws d {
        int iA;
        do {
            iA = a();
            if (iA == 0) {
                return;
            }
        } while (b(iA));
    }

    public boolean b(int i) throws d {
        switch (f.a(i)) {
            case 0:
                e();
                return true;
            case 1:
                m();
                return true;
            case 2:
                f(j());
                return true;
            case 3:
                b();
                a(f.a(f.b(i), 4));
                return true;
            case 4:
                return false;
            case 5:
                l();
                return true;
            default:
                throw d.f();
        }
    }

    public int c(int i) throws d {
        if (i < 0) {
            throw d.b();
        }
        int i2 = this.g + this.d + i;
        int i3 = this.h;
        if (i2 > i3) {
            throw d.a();
        }
        this.h = i2;
        p();
        return i3;
    }

    public long c() {
        return k();
    }

    public long d() {
        return k();
    }

    public void d(int i) {
        this.h = i;
        p();
    }

    public int e() {
        return j();
    }

    public byte[] e(int i) throws d {
        if (i < 0) {
            throw d.b();
        }
        if (this.g + this.d + i > this.h) {
            f((this.h - this.g) - this.d);
            throw d.a();
        }
        if (i <= this.b - this.d) {
            byte[] bArr = new byte[i];
            System.arraycopy(this.a, this.d, bArr, 0, i);
            this.d += i;
            return bArr;
        }
        if (i < 4096) {
            byte[] bArr2 = new byte[i];
            int i2 = this.b - this.d;
            System.arraycopy(this.a, this.d, bArr2, 0, i2);
            this.d = this.b;
            a(true);
            while (i - i2 > this.b) {
                System.arraycopy(this.a, 0, bArr2, i2, this.b);
                i2 += this.b;
                this.d = this.b;
                a(true);
            }
            System.arraycopy(this.a, 0, bArr2, i2, i - i2);
            this.d = i - i2;
            return bArr2;
        }
        int i3 = this.d;
        int i4 = this.b;
        this.g += this.b;
        this.d = 0;
        this.b = 0;
        Vector vector = new Vector();
        int i5 = i - (i4 - i3);
        while (i5 > 0) {
            byte[] bArr3 = new byte[Math.min(i5, 4096)];
            int i6 = 0;
            while (i6 < bArr3.length) {
                int i7 = this.e == null ? -1 : this.e.read(bArr3, i6, bArr3.length - i6);
                if (i7 == -1) {
                    throw d.a();
                }
                this.g += i7;
                i6 += i7;
            }
            int length = i5 - bArr3.length;
            vector.addElement(bArr3);
            i5 = length;
        }
        byte[] bArr4 = new byte[i];
        int i8 = i4 - i3;
        System.arraycopy(this.a, i3, bArr4, 0, i8);
        int length2 = i8;
        for (int i9 = 0; i9 < vector.size(); i9++) {
            byte[] bArr5 = (byte[]) vector.elementAt(i9);
            System.arraycopy(bArr5, 0, bArr4, length2, bArr5.length);
            length2 += bArr5.length;
        }
        return bArr4;
    }

    public void f(int i) throws d {
        if (i < 0) {
            throw d.b();
        }
        if (this.g + this.d + i > this.h) {
            f((this.h - this.g) - this.d);
            throw d.a();
        }
        if (i <= this.b - this.d) {
            this.d += i;
            return;
        }
        int i2 = this.b - this.d;
        this.g += this.b;
        this.d = 0;
        this.b = 0;
        int i3 = i2;
        while (i3 < i) {
            int iSkip = this.e == null ? -1 : (int) this.e.skip(i - i3);
            if (iSkip <= 0) {
                throw d.a();
            }
            i3 += iSkip;
            this.g = iSkip + this.g;
        }
    }

    public boolean f() {
        return j() != 0;
    }

    public String g() throws d {
        int iJ = j();
        if (iJ > this.b - this.d || iJ <= 0) {
            return new String(e(iJ), HTTP.UTF_8);
        }
        String str = new String(this.a, this.d, iJ, HTTP.UTF_8);
        this.d = iJ + this.d;
        return str;
    }

    public a h() throws d {
        int iJ = j();
        if (iJ > this.b - this.d || iJ <= 0) {
            return a.a(e(iJ));
        }
        a aVarA = a.a(this.a, this.d, iJ);
        this.d = iJ + this.d;
        return aVarA;
    }

    public int i() {
        return j();
    }

    public int j() throws d {
        byte bO = o();
        if (bO >= 0) {
            return bO;
        }
        int i = bO & 127;
        byte bO2 = o();
        if (bO2 >= 0) {
            return i | (bO2 << 7);
        }
        int i2 = i | ((bO2 & 127) << 7);
        byte bO3 = o();
        if (bO3 >= 0) {
            return i2 | (bO3 << 14);
        }
        int i3 = i2 | ((bO3 & 127) << 14);
        byte bO4 = o();
        if (bO4 >= 0) {
            return i3 | (bO4 << 21);
        }
        int i4 = i3 | ((bO4 & 127) << 21);
        byte bO5 = o();
        int i5 = i4 | (bO5 << 28);
        if (bO5 >= 0) {
            return i5;
        }
        for (int i6 = 0; i6 < 5; i6++) {
            if (o() >= 0) {
                return i5;
            }
        }
        throw d.c();
    }

    public long k() throws d {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bO = o();
            j |= ((long) (bO & 127)) << i;
            if ((bO & 128) == 0) {
                return j;
            }
        }
        throw d.c();
    }

    public int l() throws d {
        return (o() & Constants.NETWORK_TYPE_UNCONNECTED) | ((o() & Constants.NETWORK_TYPE_UNCONNECTED) << 8) | ((o() & Constants.NETWORK_TYPE_UNCONNECTED) << 16) | ((o() & Constants.NETWORK_TYPE_UNCONNECTED) << 24);
    }

    public long m() throws d {
        byte bO = o();
        return ((((long) o()) & 255) << 8) | (((long) bO) & 255) | ((((long) o()) & 255) << 16) | ((((long) o()) & 255) << 24) | ((((long) o()) & 255) << 32) | ((((long) o()) & 255) << 40) | ((((long) o()) & 255) << 48) | ((((long) o()) & 255) << 56);
    }

    public boolean n() {
        return this.d == this.b && !a(false);
    }

    public byte o() throws d {
        if (this.d == this.b) {
            a(true);
        }
        byte[] bArr = this.a;
        int i = this.d;
        this.d = i + 1;
        return bArr[i];
    }
}
