package org.apache.thrift.protocol;

import com.xiaocong.smarthome.network.constant.NetworkConstant;
import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class k extends org.apache.thrift.protocol.a {
    private static int f = NetworkConstant.HTTP_TIMEOUT;
    private static int g = NetworkConstant.HTTP_TIMEOUT;
    private static int h = NetworkConstant.HTTP_TIMEOUT;
    private static int i = 10485760;
    private static int j = 104857600;

    public static class a extends org.apache.thrift.protocol.a.C0021a {
        public a() {
            super(false, true);
        }

        public a(boolean z, boolean z2, int i) {
            super(z, z2, i);
        }

        @Override // org.apache.thrift.protocol.a.C0021a, org.apache.thrift.protocol.g
        public e a(org.apache.thrift.transport.d dVar) {
            k kVar = new k(dVar, this.a, this.b);
            if (this.c != 0) {
                kVar.c(this.c);
            }
            return kVar;
        }
    }

    public k(org.apache.thrift.transport.d dVar, boolean z, boolean z2) {
        super(dVar, z, z2);
    }

    @Override // org.apache.thrift.protocol.a, org.apache.thrift.protocol.e
    public d k() throws f {
        byte bR = r();
        byte bR2 = r();
        int iT = t();
        if (iT > f) {
            throw new f(3, "Thrift map size " + iT + " out of range!");
        }
        return new d(bR, bR2, iT);
    }

    @Override // org.apache.thrift.protocol.a, org.apache.thrift.protocol.e
    public c m() throws f {
        byte bR = r();
        int iT = t();
        if (iT > g) {
            throw new f(3, "Thrift list size " + iT + " out of range!");
        }
        return new c(bR, iT);
    }

    @Override // org.apache.thrift.protocol.a, org.apache.thrift.protocol.e
    public i o() throws f {
        byte bR = r();
        int iT = t();
        if (iT > h) {
            throw new f(3, "Thrift set size " + iT + " out of range!");
        }
        return new i(bR, iT);
    }

    @Override // org.apache.thrift.protocol.a, org.apache.thrift.protocol.e
    public String w() throws org.apache.thrift.f {
        int iT = t();
        if (iT > i) {
            throw new f(3, "Thrift string size " + iT + " out of range!");
        }
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

    @Override // org.apache.thrift.protocol.a, org.apache.thrift.protocol.e
    public ByteBuffer x() throws f, org.apache.thrift.transport.e {
        int iT = t();
        if (iT > j) {
            throw new f(3, "Thrift binary size " + iT + " out of range!");
        }
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
