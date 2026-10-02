package org.apache.thrift;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class e {
    private final org.apache.thrift.protocol.e a;
    private final org.apache.thrift.transport.c b;

    public e() {
        this(new org.apache.thrift.protocol.a.C0021a());
    }

    public e(org.apache.thrift.protocol.g gVar) {
        this.b = new org.apache.thrift.transport.c();
        this.a = gVar.a(this.b);
    }

    public void a(a aVar, byte[] bArr) {
        try {
            this.b.a(bArr);
            aVar.a(this.a);
        } finally {
            this.a.y();
        }
    }
}
