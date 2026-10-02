package org.apache.thrift.protocol;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b {
    public final String a;
    public final byte b;
    public final short c;

    public b() {
        this("", (byte) 0, (short) 0);
    }

    public b(String str, byte b, short s) {
        this.a = str;
        this.b = b;
        this.c = s;
    }

    public String toString() {
        return "<TField name:'" + this.a + "' type:" + ((int) this.b) + " field-id:" + ((int) this.c) + ">";
    }
}
