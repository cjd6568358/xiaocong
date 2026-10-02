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
public class x implements Serializable, Cloneable, org.apache.thrift.a<x, a> {
    public static final Map<a, b> l;
    private static final org.apache.thrift.protocol.j m = new org.apache.thrift.protocol.j("XmPushActionAckNotification");
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("type", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("request", (byte) 12, 6);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("errorCode", (byte) 10, 7);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("reason", (byte) 11, 8);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("extra", (byte) 13, 9);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("packageName", (byte) 11, 10);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("category", (byte) 11, 11);
    public String a;
    public u b;
    public String c;
    public String d;
    public String e;
    public ae f;
    public long g;
    public String h;
    public Map<String, String> i;
    public String j;
    public String k;
    private BitSet y = new BitSet(1);

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        TYPE(5, "type"),
        REQUEST(6, "request"),
        ERROR_CODE(7, "errorCode"),
        REASON(8, "reason"),
        EXTRA(9, "extra"),
        PACKAGE_NAME(10, "packageName"),
        CATEGORY(11, "category");

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
        enumMap.put(a.REQUEST, new b("request", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, ae.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.REASON, new b("reason", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.EXTRA, new b("extra", (byte) 2, new org.apache.thrift.meta_data.e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        l = Collections.unmodifiableMap(enumMap);
        b.a(x.class, l);
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!h()) {
                    throw new org.apache.thrift.protocol.f("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
                }
                m();
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
                    if (bVarI.b == 12) {
                        this.f = new ae();
                        this.f.a(eVar);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 7:
                    if (bVarI.b == 10) {
                        this.g = eVar.u();
                        a(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 8:
                    if (bVarI.b == 11) {
                        this.h = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 9:
                    if (bVarI.b == 13) {
                        org.apache.thrift.protocol.d dVarK = eVar.k();
                        this.i = new HashMap(dVarK.c * 2);
                        for (int i = 0; i < dVarK.c; i++) {
                            this.i.put(eVar.w(), eVar.w());
                        }
                        eVar.l();
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
                    if (bVarI.b == 11) {
                        this.k = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
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
        this.y.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(x xVar) {
        if (xVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = xVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(xVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = xVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(xVar.b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = xVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.c.equals(xVar.c))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = xVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.d.equals(xVar.d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = xVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.e.equals(xVar.e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = xVar.g();
        if (((zG || zG2) && !(zG && zG2 && this.f.a(xVar.f))) || this.g != xVar.g) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = xVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.h.equals(xVar.h))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = xVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.i.equals(xVar.i))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = xVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.j.equals(xVar.j))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = xVar.l();
        return !(zL || zL2) || (zL && zL2 && this.k.equals(xVar.k));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(x xVar) {
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
        if (!getClass().equals(xVar.getClass())) {
            return getClass().getName().compareTo(xVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(xVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA11 = org.apache.thrift.b.a(this.a, xVar.a)) != 0) {
            return iA11;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(xVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA10 = org.apache.thrift.b.a(this.b, xVar.b)) != 0) {
            return iA10;
        }
        int iCompareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(xVar.d()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (d() && (iA9 = org.apache.thrift.b.a(this.c, xVar.c)) != 0) {
            return iA9;
        }
        int iCompareTo4 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(xVar.e()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (e() && (iA8 = org.apache.thrift.b.a(this.d, xVar.d)) != 0) {
            return iA8;
        }
        int iCompareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(xVar.f()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (f() && (iA7 = org.apache.thrift.b.a(this.e, xVar.e)) != 0) {
            return iA7;
        }
        int iCompareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(xVar.g()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (g() && (iA6 = org.apache.thrift.b.a(this.f, xVar.f)) != 0) {
            return iA6;
        }
        int iCompareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(xVar.h()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (h() && (iA5 = org.apache.thrift.b.a(this.g, xVar.g)) != 0) {
            return iA5;
        }
        int iCompareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(xVar.i()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (i() && (iA4 = org.apache.thrift.b.a(this.h, xVar.h)) != 0) {
            return iA4;
        }
        int iCompareTo9 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(xVar.j()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (j() && (iA3 = org.apache.thrift.b.a(this.i, xVar.i)) != 0) {
            return iA3;
        }
        int iCompareTo10 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(xVar.k()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (k() && (iA2 = org.apache.thrift.b.a(this.j, xVar.j)) != 0) {
            return iA2;
        }
        int iCompareTo11 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(xVar.l()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (!l() || (iA = org.apache.thrift.b.a(this.k, xVar.k)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        m();
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
        if (this.f != null && g()) {
            eVar.a(s);
            this.f.b(eVar);
            eVar.b();
        }
        eVar.a(t);
        eVar.a(this.g);
        eVar.b();
        if (this.h != null && i()) {
            eVar.a(u);
            eVar.a(this.h);
            eVar.b();
        }
        if (this.i != null && j()) {
            eVar.a(v);
            eVar.a(new org.apache.thrift.protocol.d((byte) 11, (byte) 11, this.i.size()));
            for (Map.Entry<String, String> entry : this.i.entrySet()) {
                eVar.a(entry.getKey());
                eVar.a(entry.getValue());
            }
            eVar.d();
            eVar.b();
        }
        if (this.j != null && k()) {
            eVar.a(w);
            eVar.a(this.j);
            eVar.b();
        }
        if (this.k != null && l()) {
            eVar.a(x);
            eVar.a(this.k);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public String c() {
        return this.c;
    }

    public boolean d() {
        return this.c != null;
    }

    public boolean e() {
        return this.d != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof x)) {
            return a((x) obj);
        }
        return false;
    }

    public boolean f() {
        return this.e != null;
    }

    public boolean g() {
        return this.f != null;
    }

    public boolean h() {
        return this.y.get(0);
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.h != null;
    }

    public boolean j() {
        return this.i != null;
    }

    public boolean k() {
        return this.j != null;
    }

    public boolean l() {
        return this.k != null;
    }

    public void m() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("XmPushActionAckNotification(");
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
        if (g()) {
            sb.append(", ");
            sb.append("request:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.g);
        if (i()) {
            sb.append(", ");
            sb.append("reason:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("extra:");
            if (this.i == null) {
                sb.append("null");
            } else {
                sb.append(this.i);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("packageName:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("category:");
            if (this.k == null) {
                sb.append("null");
            } else {
                sb.append(this.k);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
