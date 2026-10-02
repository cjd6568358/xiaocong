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
public class ae implements Serializable, Cloneable, org.apache.thrift.a<ae, a> {
    public static final Map<a, b> l;
    private static final org.apache.thrift.protocol.j m = new org.apache.thrift.protocol.j("XmPushActionNotification");
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("type", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("requireAck", (byte) 2, 6);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("payload", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("extra", (byte) 13, 8);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("packageName", (byte) 11, 9);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("category", (byte) 11, 10);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("binaryExtra", (byte) 11, 14);
    public String a;
    public u b;
    public String c;
    public String d;
    public String e;
    public boolean f;
    public String g;
    public Map<String, String> h;
    public String i;
    public String j;
    public ByteBuffer k;
    private BitSet y;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        TYPE(5, "type"),
        REQUIRE_ACK(6, "requireAck"),
        PAYLOAD(7, "payload"),
        EXTRA(8, "extra"),
        PACKAGE_NAME(9, "packageName"),
        CATEGORY(10, "category"),
        BINARY_EXTRA(14, "binaryExtra");

        private static final Map<String, a> l = new HashMap();
        private final short m;
        private final String n;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                l.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.m = s;
            this.n = str;
        }

        public String a() {
            return this.n;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new b("target", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TYPE, new b("type", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.REQUIRE_ACK, new b("requireAck", (byte) 1, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.PAYLOAD, new b("payload", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.EXTRA, new b("extra", (byte) 2, new org.apache.thrift.meta_data.e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.BINARY_EXTRA, new b("binaryExtra", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        l = Collections.unmodifiableMap(enumMap);
        b.a(ae.class, l);
    }

    public ae() {
        this.y = new BitSet(1);
        this.f = true;
    }

    public ae(String str, boolean z) {
        this();
        this.c = str;
        this.f = z;
        b(true);
    }

    public ae a(String str) {
        this.c = str;
        return this;
    }

    public ae a(ByteBuffer byteBuffer) {
        this.k = byteBuffer;
        return this;
    }

    public ae a(Map<String, String> map) {
        this.h = map;
        return this;
    }

    public ae a(boolean z) {
        this.f = z;
        b(true);
        return this;
    }

    public ae a(byte[] bArr) {
        a(ByteBuffer.wrap(bArr));
        return this;
    }

    public void a(String str, String str2) {
        if (this.h == null) {
            this.h = new HashMap();
        }
        this.h.put(str, str2);
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!g()) {
                    throw new org.apache.thrift.protocol.f("Required field 'requireAck' was not found in serialized data! Struct: " + toString());
                }
                o();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b == 11) {
                        this.a = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 2:
                    if (bVarI.b == 12) {
                        this.b = new u();
                        this.b.a(eVar);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 3:
                    if (bVarI.b == 11) {
                        this.c = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 4:
                    if (bVarI.b == 11) {
                        this.d = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 5:
                    if (bVarI.b == 11) {
                        this.e = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 6:
                    if (bVarI.b == 2) {
                        this.f = eVar.q();
                        b(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 7:
                    if (bVarI.b == 11) {
                        this.g = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 8:
                    if (bVarI.b == 13) {
                        org.apache.thrift.protocol.d dVarK = eVar.k();
                        this.h = new HashMap(dVarK.c * 2);
                        for (int i = 0; i < dVarK.c; i++) {
                            this.h.put(eVar.w(), eVar.w());
                        }
                        eVar.l();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 9:
                    if (bVarI.b == 11) {
                        this.i = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 10:
                    if (bVarI.b == 11) {
                        this.j = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 11:
                case 12:
                case 13:
                default:
                    org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    break;
                case 14:
                    if (bVarI.b == 11) {
                        this.k = eVar.x();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
            }
            eVar.j();
        }
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ae aeVar) {
        if (aeVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = aeVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(aeVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = aeVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(aeVar.b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = aeVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.c.equals(aeVar.c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = aeVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.d.equals(aeVar.d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = aeVar.f();
        if (((zF || zF2) && !(zF && zF2 && this.e.equals(aeVar.e))) || this.f != aeVar.f) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = aeVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.g.equals(aeVar.g))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = aeVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.h.equals(aeVar.h))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = aeVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.i.equals(aeVar.i))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = aeVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.j.equals(aeVar.j))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = aeVar.n();
        return !(zN || zN2) || (zN && zN2 && this.k.equals(aeVar.k));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(ae aeVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        int iA10;
        int iA11;
        if (!getClass().equals(aeVar.getClass())) {
            return getClass().getName().compareTo(aeVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(aeVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA11 = org.apache.thrift.b.a(this.a, aeVar.a)) != 0) {
            return iA11;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(aeVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA10 = org.apache.thrift.b.a(this.b, aeVar.b)) != 0) {
            return iA10;
        }
        int iCompareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(aeVar.d()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (d() && (iA9 = org.apache.thrift.b.a(this.c, aeVar.c)) != 0) {
            return iA9;
        }
        int iCompareTo4 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(aeVar.e()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (e() && (iA8 = org.apache.thrift.b.a(this.d, aeVar.d)) != 0) {
            return iA8;
        }
        int iCompareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(aeVar.f()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (f() && (iA7 = org.apache.thrift.b.a(this.e, aeVar.e)) != 0) {
            return iA7;
        }
        int iCompareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(aeVar.g()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (g() && (iA6 = org.apache.thrift.b.a(this.f, aeVar.f)) != 0) {
            return iA6;
        }
        int iCompareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(aeVar.h()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (h() && (iA5 = org.apache.thrift.b.a(this.g, aeVar.g)) != 0) {
            return iA5;
        }
        int iCompareTo8 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(aeVar.j()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (j() && (iA4 = org.apache.thrift.b.a(this.h, aeVar.h)) != 0) {
            return iA4;
        }
        int iCompareTo9 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(aeVar.k()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (k() && (iA3 = org.apache.thrift.b.a(this.i, aeVar.i)) != 0) {
            return iA3;
        }
        int iCompareTo10 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(aeVar.l()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (l() && (iA2 = org.apache.thrift.b.a(this.j, aeVar.j)) != 0) {
            return iA2;
        }
        int iCompareTo11 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(aeVar.n()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (!n() || (iA = org.apache.thrift.b.a(this.k, aeVar.k)) == 0) {
            return 0;
        }
        return iA;
    }

    public ae b(String str) {
        this.d = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        o();
        eVar.a(m);
        if (this.a != null && a()) {
            eVar.a(n);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(o);
            this.b.b(eVar);
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(p);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null && e()) {
            eVar.a(q);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null && f()) {
            eVar.a(r);
            eVar.a(this.e);
            eVar.b();
        }
        eVar.a(s);
        eVar.a(this.f);
        eVar.b();
        if (this.g != null && h()) {
            eVar.a(t);
            eVar.a(this.g);
            eVar.b();
        }
        if (this.h != null && j()) {
            eVar.a(u);
            eVar.a(new org.apache.thrift.protocol.d((byte) 11, (byte) 11, this.h.size()));
            for (Map.Entry<String, String> entry : this.h.entrySet()) {
                eVar.a(entry.getKey());
                eVar.a(entry.getValue());
            }
            eVar.d();
            eVar.b();
        }
        if (this.i != null && k()) {
            eVar.a(v);
            eVar.a(this.i);
            eVar.b();
        }
        if (this.j != null && l()) {
            eVar.a(w);
            eVar.a(this.j);
            eVar.b();
        }
        if (this.k != null && n()) {
            eVar.a(x);
            eVar.a(this.k);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z) {
        this.y.set(0, z);
    }

    public boolean b() {
        return this.b != null;
    }

    public ae c(String str) {
        this.e = str;
        return this;
    }

    public String c() {
        return this.c;
    }

    public ae d(String str) {
        this.i = str;
        return this;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ae)) {
            return a((ae) obj);
        }
        return false;
    }

    public boolean f() {
        return this.e != null;
    }

    public boolean g() {
        return this.y.get(0);
    }

    public boolean h() {
        return this.g != null;
    }

    public int hashCode() {
        return 0;
    }

    public Map<String, String> i() {
        return this.h;
    }

    public boolean j() {
        return this.h != null;
    }

    public boolean k() {
        return this.i != null;
    }

    public boolean l() {
        return this.j != null;
    }

    public byte[] m() {
        a(org.apache.thrift.b.c(this.k));
        return this.k.array();
    }

    public boolean n() {
        return this.k != null;
    }

    public void o() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("XmPushActionNotification(");
        boolean z2 = true;
        if (a()) {
            sb.append("debug:");
            if (this.a == null) {
                sb.append("null");
            } else {
                sb.append(this.a);
            }
            z2 = false;
        }
        if (b()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("target:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
        } else {
            z = z2;
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append("id:");
        if (this.c == null) {
            sb.append("null");
        } else {
            sb.append(this.c);
        }
        if (e()) {
            sb.append(", ");
            sb.append("appId:");
            if (this.d == null) {
                sb.append("null");
            } else {
                sb.append(this.d);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("type:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        sb.append(", ");
        sb.append("requireAck:");
        sb.append(this.f);
        if (h()) {
            sb.append(", ");
            sb.append("payload:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("extra:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("packageName:");
            if (this.i == null) {
                sb.append("null");
            } else {
                sb.append(this.i);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("category:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("binaryExtra:");
            if (this.k == null) {
                sb.append("null");
            } else {
                org.apache.thrift.b.a(this.k, sb);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
