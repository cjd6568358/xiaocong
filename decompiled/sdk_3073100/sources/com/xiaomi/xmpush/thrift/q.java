package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.meta_data.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class q implements Serializable, Cloneable, org.apache.thrift.a<q, a> {
    public static final Map<a, b> i;
    private static final org.apache.thrift.protocol.j j = new org.apache.thrift.protocol.j("PushMessage");
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("to", (byte) 12, 1);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("id", (byte) 11, 2);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("appId", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("payload", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("createAt", (byte) 10, 5);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("ttl", (byte) 10, 6);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("collapseKey", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("packageName", (byte) 11, 8);
    public u a;
    public String b;
    public String c;
    public String d;
    public long e;
    public long f;
    public String g;
    public String h;
    private BitSet s = new BitSet(2);

    public enum a {
        TO(1, "to"),
        ID(2, "id"),
        APP_ID(3, "appId"),
        PAYLOAD(4, "payload"),
        CREATE_AT(5, "createAt"),
        TTL(6, "ttl"),
        COLLAPSE_KEY(7, "collapseKey"),
        PACKAGE_NAME(8, "packageName");

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
        enumMap.put(a.TO, new b("to", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PAYLOAD, new b("payload", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CREATE_AT, new b("createAt", (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.TTL, new b("ttl", (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.COLLAPSE_KEY, new b("collapseKey", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        i = Collections.unmodifiableMap(enumMap);
        b.a(q.class, i);
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                m();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b != 12) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.a = new u();
                        this.a.a(eVar);
                    }
                    break;
                case 2:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.b = eVar.w();
                    }
                    break;
                case 3:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.c = eVar.w();
                    }
                    break;
                case 4:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.d = eVar.w();
                    }
                    break;
                case 5:
                    if (bVarI.b != 10) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.e = eVar.u();
                        a(true);
                    }
                    break;
                case 6:
                    if (bVarI.b != 10) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.f = eVar.u();
                        b(true);
                    }
                    break;
                case 7:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.g = eVar.w();
                    }
                    break;
                case 8:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.h = eVar.w();
                    }
                    break;
                default:
                    org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    break;
            }
            eVar.j();
        }
    }

    public void a(boolean z) {
        this.s.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(q qVar) {
        if (qVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = qVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.a(qVar.a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = qVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.b.equals(qVar.b))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = qVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.c.equals(qVar.c))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = qVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.d.equals(qVar.d))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = qVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.e == qVar.e)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = qVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.f == qVar.f)) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = qVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.g.equals(qVar.g))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = qVar.l();
        return !(zL || zL2) || (zL && zL2 && this.h.equals(qVar.h));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(q qVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(qVar.getClass())) {
            return getClass().getName().compareTo(qVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(qVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA8 = org.apache.thrift.b.a(this.a, qVar.a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(qVar.c()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (c() && (iA7 = org.apache.thrift.b.a(this.b, qVar.b)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(qVar.e()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (e() && (iA6 = org.apache.thrift.b.a(this.c, qVar.c)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(qVar.g()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (g() && (iA5 = org.apache.thrift.b.a(this.d, qVar.d)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(qVar.i()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (i() && (iA4 = org.apache.thrift.b.a(this.e, qVar.e)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(qVar.j()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (j() && (iA3 = org.apache.thrift.b.a(this.f, qVar.f)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(qVar.k()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (k() && (iA2 = org.apache.thrift.b.a(this.g, qVar.g)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(qVar.l()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!l() || (iA = org.apache.thrift.b.a(this.h, qVar.h)) == 0) {
            return 0;
        }
        return iA;
    }

    public String b() {
        return this.b;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        m();
        eVar.a(j);
        if (this.a != null && a()) {
            eVar.a(k);
            this.a.b(eVar);
            eVar.b();
        }
        if (this.b != null) {
            eVar.a(l);
            eVar.a(this.b);
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(m);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null) {
            eVar.a(n);
            eVar.a(this.d);
            eVar.b();
        }
        if (i()) {
            eVar.a(o);
            eVar.a(this.e);
            eVar.b();
        }
        if (j()) {
            eVar.a(p);
            eVar.a(this.f);
            eVar.b();
        }
        if (this.g != null && k()) {
            eVar.a(q);
            eVar.a(this.g);
            eVar.b();
        }
        if (this.h != null && l()) {
            eVar.a(r);
            eVar.a(this.h);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z) {
        this.s.set(1, z);
    }

    public boolean c() {
        return this.b != null;
    }

    public String d() {
        return this.c;
    }

    public boolean e() {
        return this.c != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof q)) {
            return a((q) obj);
        }
        return false;
    }

    public String f() {
        return this.d;
    }

    public boolean g() {
        return this.d != null;
    }

    public long h() {
        return this.e;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.s.get(0);
    }

    public boolean j() {
        return this.s.get(1);
    }

    public boolean k() {
        return this.g != null;
    }

    public boolean l() {
        return this.h != null;
    }

    public void m() throws org.apache.thrift.protocol.f {
        if (this.b == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'payload' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PushMessage(");
        boolean z = true;
        if (a()) {
            sb.append("to:");
            if (this.a == null) {
                sb.append("null");
            } else {
                sb.append(this.a);
            }
            z = false;
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append("id:");
        if (this.b == null) {
            sb.append("null");
        } else {
            sb.append(this.b);
        }
        sb.append(", ");
        sb.append("appId:");
        if (this.c == null) {
            sb.append("null");
        } else {
            sb.append(this.c);
        }
        sb.append(", ");
        sb.append("payload:");
        if (this.d == null) {
            sb.append("null");
        } else {
            sb.append(this.d);
        }
        if (i()) {
            sb.append(", ");
            sb.append("createAt:");
            sb.append(this.e);
        }
        if (j()) {
            sb.append(", ");
            sb.append("ttl:");
            sb.append(this.f);
        }
        if (k()) {
            sb.append(", ");
            sb.append("collapseKey:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("packageName:");
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
