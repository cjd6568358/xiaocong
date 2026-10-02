package com.tencent.android.tpush.service.channel.c;

import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    protected byte[] a;
    protected volatile int b;
    protected volatile int c;
    protected volatile int d;
    protected volatile int e;
    protected volatile boolean f;
    protected boolean g;
    protected InputStream h;
    protected boolean i;
    protected OutputStream j;
    protected boolean k;

    public OutputStream a() {
        return this.j;
    }

    public InputStream b() {
        return this.h;
    }

    public int c() {
        int iG;
        synchronized (this) {
            iG = g();
        }
        return iG;
    }

    public int d() {
        int iF;
        synchronized (this) {
            iF = f();
        }
        return iF;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        byte[] bArr = new byte[this.a.length * 2];
        int iH = h();
        int iG = g();
        if (this.d <= this.c) {
            System.arraycopy(this.a, this.d, bArr, 0, this.c - this.d);
        } else {
            int length = this.a.length - this.d;
            System.arraycopy(this.a, this.d, bArr, 0, length);
            System.arraycopy(this.a, 0, bArr, length, this.c);
        }
        this.a = bArr;
        this.d = 0;
        this.b = iH;
        this.c = iH + iG;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int f() {
        return this.c < this.d ? (this.d - this.c) - 1 : (this.a.length - 1) - (this.c - this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int g() {
        return this.b <= this.c ? this.c - this.b : this.a.length - (this.b - this.c);
    }

    private int h() {
        return this.d <= this.b ? this.b - this.d : this.a.length - (this.d - this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        if (h() >= this.e) {
            this.d = this.b;
            this.e = 0;
        }
    }

    public a() {
        this(4096, true);
    }

    public a(int i, boolean z) {
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = false;
        this.g = true;
        this.h = new b(this);
        this.i = false;
        this.j = new c(this);
        this.k = false;
        if (i == -1) {
            this.a = new byte[4096];
            this.f = true;
        } else {
            this.a = new byte[i];
            this.f = false;
        }
        this.g = z;
    }
}
