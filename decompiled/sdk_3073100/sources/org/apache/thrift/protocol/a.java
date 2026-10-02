package org.apache.thrift.protocol;

import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class a extends e {
    private static final j f = new j();
    protected boolean a;
    protected boolean b;
    protected int c;
    protected boolean d;
    private byte[] g;
    private byte[] h;
    private byte[] i;
    private byte[] j;
    private byte[] k;
    private byte[] l;
    private byte[] m;
    private byte[] n;

    /* JADX INFO: renamed from: org.apache.thrift.protocol.a$a, reason: collision with other inner class name */
    public static class C0021a implements g {
        protected boolean a;
        protected boolean b;
        protected int c;

        public C0021a() {
            this(false, true);
        }

        public C0021a(boolean z, boolean z2) {
            this(z, z2, 0);
        }

        public C0021a(boolean z, boolean z2, int i) {
            this.a = false;
            this.b = true;
            this.a = z;
            this.b = z2;
            this.c = i;
        }

        @Override // org.apache.thrift.protocol.g
        public e a(org.apache.thrift.transport.d dVar) {
            a aVar = new a(dVar, this.a, this.b);
            if (this.c != 0) {
                aVar.c(this.c);
            }
            return aVar;
        }
    }

    public a(org.apache.thrift.transport.d dVar, boolean z, boolean z2) {
        super(dVar);
        this.a = false;
        this.b = true;
        this.d = false;
        this.g = new byte[1];
        this.h = new byte[2];
        this.i = new byte[4];
        this.j = new byte[8];
        this.k = new byte[1];
        this.l = new byte[2];
        this.m = new byte[4];
        this.n = new byte[8];
        this.a = z;
        this.b = z2;
    }

    private int a(byte[] bArr, int i, int i2) throws org.apache.thrift.f {
        d(i2);
        return this.e.d(bArr, i, i2);
    }

    @Override // org.apache.thrift.protocol.e
    public void a() {
    }

    @Override // org.apache.thrift.protocol.e
    public void a(byte b) {
        this.g[0] = b;
        this.e.b(this.g, 0, 1);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(double d) {
        a(Double.doubleToLongBits(d));
    }

    @Override // org.apache.thrift.protocol.e
    public void a(int i) {
        this.i[0] = (byte) ((i >> 24) & 255);
        this.i[1] = (byte) ((i >> 16) & 255);
        this.i[2] = (byte) ((i >> 8) & 255);
        this.i[3] = (byte) (i & 255);
        this.e.b(this.i, 0, 4);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(long j) {
        this.j[0] = (byte) ((j >> 56) & 255);
        this.j[1] = (byte) ((j >> 48) & 255);
        this.j[2] = (byte) ((j >> 40) & 255);
        this.j[3] = (byte) ((j >> 32) & 255);
        this.j[4] = (byte) ((j >> 24) & 255);
        this.j[5] = (byte) ((j >> 16) & 255);
        this.j[6] = (byte) ((j >> 8) & 255);
        this.j[7] = (byte) (255 & j);
        this.e.b(this.j, 0, 8);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(String str) throws org.apache.thrift.f {
        try {
            byte[] bytes = str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET);
            a(bytes.length);
            this.e.b(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new org.apache.thrift.f("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // org.apache.thrift.protocol.e
    public void a(ByteBuffer byteBuffer) {
        int iLimit = (byteBuffer.limit() - byteBuffer.position()) - byteBuffer.arrayOffset();
        a(iLimit);
        this.e.b(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), iLimit);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(b bVar) {
        a(bVar.b);
        a(bVar.c);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(c cVar) {
        a(cVar.a);
        a(cVar.b);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(d dVar) {
        a(dVar.a);
        a(dVar.b);
        a(dVar.c);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(i iVar) {
        a(iVar.a);
        a(iVar.b);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(j jVar) {
    }

    @Override // org.apache.thrift.protocol.e
    public void a(short s) {
        this.h[0] = (byte) ((s >> 8) & 255);
        this.h[1] = (byte) (s & 255);
        this.e.b(this.h, 0, 2);
    }

    @Override // org.apache.thrift.protocol.e
    public void a(boolean z) {
        a(z ? (byte) 1 : (byte) 0);
    }

    public String b(int i) throws org.apache.thrift.f {
        try {
            d(i);
            byte[] bArr = new byte[i];
            this.e.d(bArr, 0, i);
            return new String(bArr, AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new org.apache.thrift.f("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // org.apache.thrift.protocol.e
    public void b() {
    }

    @Override // org.apache.thrift.protocol.e
    public void c() {
        a((byte) 0);
    }

    public void c(int i) {
        this.c = i;
        this.d = true;
    }

    @Override // org.apache.thrift.protocol.e
    public void d() {
    }

    protected void d(int i) throws org.apache.thrift.f {
        if (i < 0) {
            throw new org.apache.thrift.f("Negative length: " + i);
        }
        if (this.d) {
            this.c -= i;
            if (this.c < 0) {
                throw new org.apache.thrift.f("Message length exceeded: " + i);
            }
        }
    }

    @Override // org.apache.thrift.protocol.e
    public void e() {
    }

    @Override // org.apache.thrift.protocol.e
    public void f() {
    }

    @Override // org.apache.thrift.protocol.e
    public j g() {
        return f;
    }

    @Override // org.apache.thrift.protocol.e
    public void h() {
    }

    @Override // org.apache.thrift.protocol.e
    public b i() throws org.apache.thrift.f {
        byte bR = r();
        return new b("", bR, bR == 0 ? (short) 0 : s());
    }

    @Override // org.apache.thrift.protocol.e
    public void j() {
    }

    @Override // org.apache.thrift.protocol.e
    public d k() {
        return new d(r(), r(), t());
    }

    @Override // org.apache.thrift.protocol.e
    public void l() {
    }

    @Override // org.apache.thrift.protocol.e
    public c m() {
        return new c(r(), t());
    }

    @Override // org.apache.thrift.protocol.e
    public void n() {
    }

    @Override // org.apache.thrift.protocol.e
    public i o() {
        return new i(r(), t());
    }

    @Override // org.apache.thrift.protocol.e
    public void p() {
    }

    @Override // org.apache.thrift.protocol.e
    public boolean q() {
        return r() == 1;
    }

    @Override // org.apache.thrift.protocol.e
    public byte r() throws org.apache.thrift.f {
        if (this.e.c() < 1) {
            a(this.k, 0, 1);
            return this.k[0];
        }
        byte b = this.e.a()[this.e.b()];
        this.e.a(1);
        return b;
    }

    @Override // org.apache.thrift.protocol.e
    public short s() throws org.apache.thrift.f {
        int iB = 0;
        byte[] bArrA = this.l;
        if (this.e.c() >= 2) {
            bArrA = this.e.a();
            iB = this.e.b();
            this.e.a(2);
        } else {
            a(this.l, 0, 2);
        }
        return (short) ((bArrA[iB + 1] & 255) | ((bArrA[iB] & 255) << 8));
    }

    @Override // org.apache.thrift.protocol.e
    public int t() throws org.apache.thrift.f {
        int iB = 0;
        byte[] bArrA = this.m;
        if (this.e.c() >= 4) {
            bArrA = this.e.a();
            iB = this.e.b();
            this.e.a(4);
        } else {
            a(this.m, 0, 4);
        }
        return (bArrA[iB + 3] & 255) | ((bArrA[iB] & 255) << 24) | ((bArrA[iB + 1] & 255) << 16) | ((bArrA[iB + 2] & 255) << 8);
    }

    @Override // org.apache.thrift.protocol.e
    public long u() throws org.apache.thrift.f {
        int iB = 0;
        byte[] bArrA = this.n;
        if (this.e.c() >= 8) {
            bArrA = this.e.a();
            iB = this.e.b();
            this.e.a(8);
        } else {
            a(this.n, 0, 8);
        }
        return ((long) (bArrA[iB + 7] & 255)) | (((long) (bArrA[iB] & 255)) << 56) | (((long) (bArrA[iB + 1] & 255)) << 48) | (((long) (bArrA[iB + 2] & 255)) << 40) | (((long) (bArrA[iB + 3] & 255)) << 32) | (((long) (bArrA[iB + 4] & 255)) << 24) | (((long) (bArrA[iB + 5] & 255)) << 16) | (((long) (bArrA[iB + 6] & 255)) << 8);
    }

    @Override // org.apache.thrift.protocol.e
    public double v() {
        return Double.longBitsToDouble(u());
    }

    @Override // org.apache.thrift.protocol.e
    public String w() throws org.apache.thrift.f {
        int iT = t();
        if (this.e.c() < iT) {
            return b(iT);
        }
        try {
            String str = new String(this.e.a(), this.e.b(), iT, AsyncHttpResponseHandler.DEFAULT_CHARSET);
            this.e.a(iT);
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new org.apache.thrift.f("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    @Override // org.apache.thrift.protocol.e
    public ByteBuffer x() throws org.apache.thrift.f {
        int iT = t();
        d(iT);
        if (this.e.c() >= iT) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(this.e.a(), this.e.b(), iT);
            this.e.a(iT);
            return byteBufferWrap;
        }
        byte[] bArr = new byte[iT];
        this.e.d(bArr, 0, iT);
        return ByteBuffer.wrap(bArr);
    }
}
