package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.meta_data.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class am implements Serializable, Cloneable, org.apache.thrift.a<am, a> {
    public static final Map<a, b> k;
    private static final org.apache.thrift.protocol.j l = new org.apache.thrift.protocol.j("XmPushActionUnRegistration");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("regId", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("appVersion", (byte) 11, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("packageName", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("token", (byte) 11, 8);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("deviceId", (byte) 11, 9);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("aliasName", (byte) 11, 10);
    public String a;
    public u b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public String h;
    public String i;
    public String j;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        REG_ID(5, "regId"),
        APP_VERSION(6, "appVersion"),
        PACKAGE_NAME(7, "packageName"),
        TOKEN(8, "token"),
        DEVICE_ID(9, "deviceId"),
        ALIAS_NAME(10, "aliasName");

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
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.REG_ID, new b("regId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_VERSION, new b("appVersion", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TOKEN, new b("token", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.DEVICE_ID, new b("deviceId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.ALIAS_NAME, new b("aliasName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        k = Collections.unmodifiableMap(enumMap);
        b.a(am.class, k);
    }

    public am a(String str) {
        this.c = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                k();
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

    public boolean a() {
        return this.a != null;
    }

    public boolean a(am amVar) {
        if (amVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = amVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(amVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = amVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(amVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = amVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(amVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = amVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(amVar.d))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = amVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.e.equals(amVar.e))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = amVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f.equals(amVar.f))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = amVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.g.equals(amVar.g))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = amVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.h.equals(amVar.h))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = amVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.i.equals(amVar.i))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = amVar.j();
        return !(zJ || zJ2) || (zJ && zJ2 && this.j.equals(amVar.j));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(am amVar) {
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
        if (!getClass().equals(amVar.getClass())) {
            return getClass().getName().compareTo(amVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(amVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA10 = org.apache.thrift.b.a(this.a, amVar.a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(amVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA9 = org.apache.thrift.b.a(this.b, amVar.b)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(amVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = org.apache.thrift.b.a(this.c, amVar.c)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(amVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = org.apache.thrift.b.a(this.d, amVar.d)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(amVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = org.apache.thrift.b.a(this.e, amVar.e)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(amVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = org.apache.thrift.b.a(this.f, amVar.f)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(amVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = org.apache.thrift.b.a(this.g, amVar.g)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(amVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = org.apache.thrift.b.a(this.h, amVar.h)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(amVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = org.apache.thrift.b.a(this.i, amVar.i)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(amVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = org.apache.thrift.b.a(this.j, amVar.j)) == 0) {
            return 0;
        }
        return iA;
    }

    public am b(String str) {
        this.d = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        k();
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
        if (this.d != null) {
            eVar.a(p);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null && e()) {
            eVar.a(q);
            eVar.a(this.e);
            eVar.b();
        }
        if (this.f != null && f()) {
            eVar.a(r);
            eVar.a(this.f);
            eVar.b();
        }
        if (this.g != null && g()) {
            eVar.a(s);
            eVar.a(this.g);
            eVar.b();
        }
        if (this.h != null && h()) {
            eVar.a(t);
            eVar.a(this.h);
            eVar.b();
        }
        if (this.i != null && i()) {
            eVar.a(u);
            eVar.a(this.i);
            eVar.b();
        }
        if (this.j != null && j()) {
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

    public am c(String str) {
        this.e = str;
        return this;
    }

    public boolean c() {
        return this.c != null;
    }

    public am d(String str) {
        this.g = str;
        return this;
    }

    public boolean d() {
        return this.d != null;
    }

    public am e(String str) {
        this.h = str;
        return this;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof am)) {
            return a((am) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f != null;
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

    public boolean i() {
        return this.i != null;
    }

    public boolean j() {
        return this.j != null;
    }

    public void k() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'appId' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("XmPushActionUnRegistration(");
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
            sb.append("regId:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("appVersion:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("token:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("deviceId:");
            if (this.i == null) {
                sb.append("null");
            } else {
                sb.append(this.i);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("aliasName:");
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
