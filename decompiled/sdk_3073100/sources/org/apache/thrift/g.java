package org.apache.thrift;

import java.io.ByteArrayOutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class g {
    private final ByteArrayOutputStream a;
    private final org.apache.thrift.transport.a b;
    private org.apache.thrift.protocol.e c;

    public g() {
        this(new org.apache.thrift.protocol.a.C0021a());
    }

    public g(org.apache.thrift.protocol.g gVar) {
        this.a = new ByteArrayOutputStream();
        this.b = new org.apache.thrift.transport.a(this.a);
        this.c = gVar.a(this.b);
    }

    public byte[] a(a aVar) {
        this.a.reset();
        aVar.b(this.c);
        return this.a.toByteArray();
    }
}
