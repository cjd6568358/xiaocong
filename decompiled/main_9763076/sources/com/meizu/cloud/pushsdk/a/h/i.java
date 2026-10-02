package com.meizu.cloud.pushsdk.a.h;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class i {
    final byte[] a;
    int b;
    int c;
    boolean d;
    boolean e;
    i f;
    i g;

    i() {
        this.a = new byte[2048];
        this.e = true;
        this.d = false;
    }

    i(i iVar) {
        this(iVar.a, iVar.b, iVar.c);
        iVar.d = true;
    }

    i(byte[] bArr, int i, int i2) {
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.e = false;
        this.d = true;
    }

    public i a() {
        i iVar = this.f != this ? this.f : null;
        this.g.f = this.f;
        this.f.g = this.g;
        this.f = null;
        this.g = null;
        return iVar;
    }

    public i a(i iVar) {
        iVar.g = this;
        iVar.f = this.f;
        this.f.g = iVar;
        this.f = iVar;
        return iVar;
    }

    public i a(int i) {
        if (i <= 0 || i > this.c - this.b) {
            throw new IllegalArgumentException();
        }
        i iVar = new i(this);
        iVar.c = iVar.b + i;
        this.b += i;
        this.g.a(iVar);
        return iVar;
    }

    public void b() {
        if (this.g == this) {
            throw new IllegalStateException();
        }
        if (this.g.e) {
            int i = this.c - this.b;
            if (i <= (this.g.d ? 0 : this.g.b) + (2048 - this.g.c)) {
                a(this.g, i);
                a();
                j.a(this);
            }
        }
    }

    public void a(i iVar, int i) {
        if (!iVar.e) {
            throw new IllegalArgumentException();
        }
        if (iVar.c + i > 2048) {
            if (iVar.d) {
                throw new IllegalArgumentException();
            }
            if ((iVar.c + i) - iVar.b > 2048) {
                throw new IllegalArgumentException();
            }
            System.arraycopy(iVar.a, iVar.b, iVar.a, 0, iVar.c - iVar.b);
            iVar.c -= iVar.b;
            iVar.b = 0;
        }
        System.arraycopy(this.a, this.b, iVar.a, iVar.c, i);
        iVar.c += i;
        this.b += i;
    }
}
