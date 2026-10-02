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
public class an implements Serializable, Cloneable, org.apache.thrift.a<an, a> {
    public static final Map<a, b> i;
    private static final org.apache.thrift.protocol.j j = new org.apache.thrift.protocol.j("XmPushActionUnRegistrationResult");
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("request", (byte) 12, 5);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("errorCode", (byte) 10, 6);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("reason", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("packageName", (byte) 11, 8);
    public String a;
    public u b;
    public String c;
    public String d;
    public am e;
    public long f;
    public String g;
    public String h;
    private BitSet s = new BitSet(1);

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        REQUEST(5, "request"),
        ERROR_CODE(6, "errorCode"),
        REASON(7, "reason"),
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
        enumMap.put(a.DEBUG, new b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new b("target", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.REQUEST, new b("request", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, am.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.REASON, new b("reason", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        i = Collections.unmodifiableMap(enumMap);
        b.a(an.class, i);
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!f()) {
                    throw new org.apache.thrift.protocol.f("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
                }
                i();
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
                        this.e = new am();
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

    public boolean a(an anVar) {
        if (anVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = anVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(anVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = anVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(anVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = anVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(anVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = anVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(anVar.d))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = anVar.e();
        if (((zE || zE2) && !(zE && zE2 && this.e.a(anVar.e))) || this.f != anVar.f) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = anVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.g.equals(anVar.g))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = anVar.h();
        return !(zH || zH2) || (zH && zH2 && this.h.equals(anVar.h));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(an anVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(anVar.getClass())) {
            return getClass().getName().compareTo(anVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(anVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA8 = org.apache.thrift.b.a(this.a, anVar.a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(anVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA7 = org.apache.thrift.b.a(this.b, anVar.b)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(anVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA6 = org.apache.thrift.b.a(this.c, anVar.c)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(anVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA5 = org.apache.thrift.b.a(this.d, anVar.d)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(anVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA4 = org.apache.thrift.b.a(this.e, anVar.e)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(anVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA3 = org.apache.thrift.b.a(this.f, anVar.f)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(anVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA2 = org.apache.thrift.b.a(this.g, anVar.g)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(anVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!h() || (iA = org.apache.thrift.b.a(this.h, anVar.h)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        i();
        eVar.a(j);
        if (this.a != null && a()) {
            eVar.a(k);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(l);
            this.b.b(eVar);
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
        if (this.e != null && e()) {
            eVar.a(o);
            this.e.b(eVar);
            eVar.b();
        }
        eVar.a(p);
        eVar.a(this.f);
        eVar.b();
        if (this.g != null && g()) {
            eVar.a(q);
            eVar.a(this.g);
            eVar.b();
        }
        if (this.h != null && h()) {
            eVar.a(r);
            eVar.a(this.h);
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
        if (obj != null && (obj instanceof an)) {
            return a((an) obj);
        }
        return false;
    }

    public boolean f() {
        return this.s.get(0);
    }

    public boolean g() {
        return this.g != null;
    }

    public boolean h() {
        return this.h != null;
    }

    public int hashCode() {
        return 0;
    }

    public void i() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'appId' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistrationResult(");
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
        sb.append(", ");
        sb.append("appId:");
        if (this.d == null) {
            sb.append("null");
        } else {
            sb.append(this.d);
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
        sb.append(", ");
        sb.append("errorCode:");
        sb.append(this.f);
        if (g()) {
            sb.append(", ");
            sb.append("reason:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (h()) {
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
