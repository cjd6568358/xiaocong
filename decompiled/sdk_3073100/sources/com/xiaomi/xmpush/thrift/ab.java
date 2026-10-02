package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.meta_data.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ab implements Serializable, Cloneable, org.apache.thrift.a<ab, a> {
    public static final Map<a, b> i;
    private static final org.apache.thrift.protocol.j j = new org.apache.thrift.protocol.j("XmPushActionContainer");
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("action", (byte) 8, 1);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("encryptAction", (byte) 2, 2);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("isRequest", (byte) 2, 3);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("pushAction", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("appid", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("packageName", (byte) 11, 6);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("target", (byte) 12, 7);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("metaInfo", (byte) 12, 8);
    public com.xiaomi.xmpush.thrift.a a;
    public ByteBuffer d;
    public String e;
    public String f;
    public u g;
    public r h;
    private BitSet s = new BitSet(2);
    public boolean b = true;
    public boolean c = true;

    public enum a {
        ACTION(1, "action"),
        ENCRYPT_ACTION(2, "encryptAction"),
        IS_REQUEST(3, "isRequest"),
        PUSH_ACTION(4, "pushAction"),
        APPID(5, "appid"),
        PACKAGE_NAME(6, "packageName"),
        TARGET(7, "target"),
        META_INFO(8, "metaInfo");

        private static final Map<String, a> i = new HashMap();
        private final short j;
        private final String k;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                i.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.j = s;
            this.k = str;
        }

        public String a() {
            return this.k;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.ACTION, new b("action", (byte) 1, new org.apache.thrift.meta_data.a((byte) 16, com.xiaomi.xmpush.thrift.a.class)));
        enumMap.put(a.ENCRYPT_ACTION, new b("encryptAction", (byte) 1, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.IS_REQUEST, new b("isRequest", (byte) 1, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.PUSH_ACTION, new b("pushAction", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APPID, new b("appid", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new b("target", (byte) 1, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.META_INFO, new b("metaInfo", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, r.class)));
        i = Collections.unmodifiableMap(enumMap);
        b.a(ab.class, i);
    }

    public com.xiaomi.xmpush.thrift.a a() {
        return this.a;
    }

    public ab a(com.xiaomi.xmpush.thrift.a aVar) {
        this.a = aVar;
        return this;
    }

    public ab a(r rVar) {
        this.h = rVar;
        return this;
    }

    public ab a(u uVar) {
        this.g = uVar;
        return this;
    }

    public ab a(String str) {
        this.e = str;
        return this;
    }

    public ab a(ByteBuffer byteBuffer) {
        this.d = byteBuffer;
        return this;
    }

    public ab a(boolean z) {
        this.b = z;
        b(true);
        return this;
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!d()) {
                    throw new org.apache.thrift.protocol.f("Required field 'encryptAction' was not found in serialized data! Struct: " + toString());
                }
                if (!e()) {
                    throw new org.apache.thrift.protocol.f("Required field 'isRequest' was not found in serialized data! Struct: " + toString());
                }
                o();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b != 8) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.a = com.xiaomi.xmpush.thrift.a.a(eVar.t());
                    }
                    break;
                case 2:
                    if (bVarI.b != 2) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.b = eVar.q();
                        b(true);
                    }
                    break;
                case 3:
                    if (bVarI.b != 2) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.c = eVar.q();
                        d(true);
                    }
                    break;
                case 4:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.d = eVar.x();
                    }
                    break;
                case 5:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.e = eVar.w();
                    }
                    break;
                case 6:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.f = eVar.w();
                    }
                    break;
                case 7:
                    if (bVarI.b != 12) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.g = new u();
                        this.g.a(eVar);
                    }
                    break;
                case 8:
                    if (bVarI.b != 12) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.h = new r();
                        this.h.a(eVar);
                    }
                    break;
                default:
                    org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    break;
            }
            eVar.j();
        }
    }

    public boolean a(ab abVar) {
        if (abVar == null) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = abVar.b();
        if (((zB || zB2) && (!zB || !zB2 || !this.a.equals(abVar.a))) || this.b != abVar.b || this.c != abVar.c) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = abVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.d.equals(abVar.d))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = abVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.e.equals(abVar.e))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = abVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f.equals(abVar.f))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = abVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.g.a(abVar.g))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = abVar.n();
        return !(zN || zN2) || (zN && zN2 && this.h.a(abVar.h));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(ab abVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(abVar.getClass())) {
            return getClass().getName().compareTo(abVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(abVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (b() && (iA8 = org.apache.thrift.b.a(this.a, abVar.a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(abVar.d()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (d() && (iA7 = org.apache.thrift.b.a(this.b, abVar.b)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(abVar.e()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (e() && (iA6 = org.apache.thrift.b.a(this.c, abVar.c)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(abVar.g()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (g() && (iA5 = org.apache.thrift.b.a(this.d, abVar.d)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(abVar.i()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (i() && (iA4 = org.apache.thrift.b.a(this.e, abVar.e)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(abVar.k()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (k() && (iA3 = org.apache.thrift.b.a(this.f, abVar.f)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(abVar.l()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (l() && (iA2 = org.apache.thrift.b.a(this.g, abVar.g)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(abVar.n()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!n() || (iA = org.apache.thrift.b.a(this.h, abVar.h)) == 0) {
            return 0;
        }
        return iA;
    }

    public ab b(String str) {
        this.f = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        o();
        eVar.a(j);
        if (this.a != null) {
            eVar.a(k);
            eVar.a(this.a.a());
            eVar.b();
        }
        eVar.a(l);
        eVar.a(this.b);
        eVar.b();
        eVar.a(m);
        eVar.a(this.c);
        eVar.b();
        if (this.d != null) {
            eVar.a(n);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null && i()) {
            eVar.a(o);
            eVar.a(this.e);
            eVar.b();
        }
        if (this.f != null && k()) {
            eVar.a(p);
            eVar.a(this.f);
            eVar.b();
        }
        if (this.g != null) {
            eVar.a(q);
            this.g.b(eVar);
            eVar.b();
        }
        if (this.h != null && n()) {
            eVar.a(r);
            this.h.b(eVar);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z) {
        this.s.set(0, z);
    }

    public boolean b() {
        return this.a != null;
    }

    public ab c(boolean z) {
        this.c = z;
        d(true);
        return this;
    }

    public boolean c() {
        return this.b;
    }

    public void d(boolean z) {
        this.s.set(1, z);
    }

    public boolean d() {
        return this.s.get(0);
    }

    public boolean e() {
        return this.s.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ab)) {
            return a((ab) obj);
        }
        return false;
    }

    public byte[] f() {
        a(org.apache.thrift.b.c(this.d));
        return this.d.array();
    }

    public boolean g() {
        return this.d != null;
    }

    public String h() {
        return this.e;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.e != null;
    }

    public String j() {
        return this.f;
    }

    public boolean k() {
        return this.f != null;
    }

    public boolean l() {
        return this.g != null;
    }

    public r m() {
        return this.h;
    }

    public boolean n() {
        return this.h != null;
    }

    public void o() throws org.apache.thrift.protocol.f {
        if (this.a == null) {
            throw new org.apache.thrift.protocol.f("Required field 'action' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'pushAction' was not present! Struct: " + toString());
        }
        if (this.g == null) {
            throw new org.apache.thrift.protocol.f("Required field 'target' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionContainer(");
        sb.append("action:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(", ");
        sb.append("encryptAction:");
        sb.append(this.b);
        sb.append(", ");
        sb.append("isRequest:");
        sb.append(this.c);
        sb.append(", ");
        sb.append("pushAction:");
        if (this.d == null) {
            sb.append("null");
        } else {
            org.apache.thrift.b.a(this.d, sb);
        }
        if (i()) {
            sb.append(", ");
            sb.append("appid:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("packageName:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        sb.append(", ");
        sb.append("target:");
        if (this.g == null) {
            sb.append("null");
        } else {
            sb.append(this.g);
        }
        if (n()) {
            sb.append(", ");
            sb.append("metaInfo:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
