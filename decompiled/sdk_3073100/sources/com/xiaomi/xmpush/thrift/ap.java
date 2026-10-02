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
public class ap implements Serializable, Cloneable, org.apache.thrift.a<ap, a> {
    public static final Map<a, b> k;
    private static final org.apache.thrift.protocol.j l = new org.apache.thrift.protocol.j("XmPushActionUnSubscriptionResult");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("request", (byte) 12, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("errorCode", (byte) 10, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("reason", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("topic", (byte) 11, 8);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("packageName", (byte) 11, 9);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("category", (byte) 11, 10);
    public String a;
    public u b;
    public String c;
    public String d;
    public ao e;
    public long f;
    public String g;
    public String h;
    public String i;
    public String j;
    private BitSet w = new BitSet(1);

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        REQUEST(5, "request"),
        ERROR_CODE(6, "errorCode"),
        REASON(7, "reason"),
        TOPIC(8, "topic"),
        PACKAGE_NAME(9, "packageName"),
        CATEGORY(10, "category");

        private static final Map<String, a> k = new HashMap();
        private final short l;
        private final String m;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                k.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.l = s;
            this.m = str;
        }

        public String a() {
            return this.m;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new b("target", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.REQUEST, new b("request", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, ao.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.REASON, new b("reason", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TOPIC, new b("topic", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        k = Collections.unmodifiableMap(enumMap);
        b.a(ap.class, k);
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
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.a = eVar.w();
                    }
                    break;
                case 2:
                    if (bVarI.b != 12) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.b = new u();
                        this.b.a(eVar);
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
                    if (bVarI.b != 12) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.e = new ao();
                        this.e.a(eVar);
                    }
                    break;
                case 6:
                    if (bVarI.b != 10) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.f = eVar.u();
                        a(true);
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
                case 9:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.i = eVar.w();
                    }
                    break;
                case 10:
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.j = eVar.w();
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
        this.w.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(ap apVar) {
        if (apVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = apVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(apVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = apVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(apVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = apVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(apVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = apVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(apVar.d))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = apVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.e.a(apVar.e))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = apVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f == apVar.f)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = apVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.g.equals(apVar.g))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = apVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.h.equals(apVar.h))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = apVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.i.equals(apVar.i))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = apVar.l();
        return !(zL || zL2) || (zL && zL2 && this.j.equals(apVar.j));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(ap apVar) {
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
        if (!getClass().equals(apVar.getClass())) {
            return getClass().getName().compareTo(apVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(apVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA10 = org.apache.thrift.b.a(this.a, apVar.a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(apVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA9 = org.apache.thrift.b.a(this.b, apVar.b)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(apVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = org.apache.thrift.b.a(this.c, apVar.c)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(apVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = org.apache.thrift.b.a(this.d, apVar.d)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(apVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = org.apache.thrift.b.a(this.e, apVar.e)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(apVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = org.apache.thrift.b.a(this.f, apVar.f)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(apVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = org.apache.thrift.b.a(this.g, apVar.g)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(apVar.i()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (i() && (iA3 = org.apache.thrift.b.a(this.h, apVar.h)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(apVar.j()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (j() && (iA2 = org.apache.thrift.b.a(this.i, apVar.i)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(apVar.l()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!l() || (iA = org.apache.thrift.b.a(this.j, apVar.j)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        m();
        eVar.a(l);
        if (this.a != null && a()) {
            eVar.a(m);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(n);
            this.b.b(eVar);
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(o);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null && d()) {
            eVar.a(p);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null && e()) {
            eVar.a(q);
            this.e.b(eVar);
            eVar.b();
        }
        if (f()) {
            eVar.a(r);
            eVar.a(this.f);
            eVar.b();
        }
        if (this.g != null && g()) {
            eVar.a(s);
            eVar.a(this.g);
            eVar.b();
        }
        if (this.h != null && i()) {
            eVar.a(t);
            eVar.a(this.h);
            eVar.b();
        }
        if (this.i != null && j()) {
            eVar.a(u);
            eVar.a(this.i);
            eVar.b();
        }
        if (this.j != null && l()) {
            eVar.a(v);
            eVar.a(this.j);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public boolean c() {
        return this.c != null;
    }

    public boolean d() {
        return this.d != null;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ap)) {
            return a((ap) obj);
        }
        return false;
    }

    public boolean f() {
        return this.w.get(0);
    }

    public boolean g() {
        return this.g != null;
    }

    public String h() {
        return this.h;
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

    public String k() {
        return this.j;
    }

    public boolean l() {
        return this.j != null;
    }

    public void m() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("XmPushActionUnSubscriptionResult(");
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
        if (d()) {
            sb.append(", ");
            sb.append("appId:");
            if (this.d == null) {
                sb.append("null");
            } else {
                sb.append(this.d);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("request:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("errorCode:");
            sb.append(this.f);
        }
        if (g()) {
            sb.append(", ");
            sb.append("reason:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("topic:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (j()) {
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
        sb.append(")");
        return sb.toString();
    }
}
