package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.meta_data.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ak implements Serializable, Cloneable, org.apache.thrift.a<ak, a> {
    public static final Map<a, b> h;
    private static final org.apache.thrift.protocol.j i = new org.apache.thrift.protocol.j("XmPushActionSubscription");
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("topic", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("packageName", (byte) 11, 6);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("category", (byte) 11, 7);
    public String a;
    public u b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        TOPIC(5, "topic"),
        PACKAGE_NAME(6, "packageName"),
        CATEGORY(7, "category");

        private static final Map<String, a> h = new HashMap();
        private final short i;
        private final String j;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                h.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.i = s;
            this.j = str;
        }

        public String a() {
            return this.j;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new b("target", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TOPIC, new b("topic", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        h = Collections.unmodifiableMap(enumMap);
        b.a(ak.class, h);
    }

    public ak a(String str) {
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
                h();
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

    public boolean a(ak akVar) {
        if (akVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = akVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(akVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = akVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(akVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = akVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(akVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = akVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(akVar.d))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = akVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.e.equals(akVar.e))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = akVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f.equals(akVar.f))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = akVar.g();
        return !(zG || zG2) || (zG && zG2 && this.g.equals(akVar.g));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(ak akVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        if (!getClass().equals(akVar.getClass())) {
            return getClass().getName().compareTo(akVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(akVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA7 = org.apache.thrift.b.a(this.a, akVar.a)) != 0) {
            return iA7;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(akVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA6 = org.apache.thrift.b.a(this.b, akVar.b)) != 0) {
            return iA6;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(akVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA5 = org.apache.thrift.b.a(this.c, akVar.c)) != 0) {
            return iA5;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(akVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA4 = org.apache.thrift.b.a(this.d, akVar.d)) != 0) {
            return iA4;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(akVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA3 = org.apache.thrift.b.a(this.e, akVar.e)) != 0) {
            return iA3;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(akVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA2 = org.apache.thrift.b.a(this.f, akVar.f)) != 0) {
            return iA2;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(akVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (!g() || (iA = org.apache.thrift.b.a(this.g, akVar.g)) == 0) {
            return 0;
        }
        return iA;
    }

    public ak b(String str) {
        this.d = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        h();
        eVar.a(i);
        if (this.a != null && a()) {
            eVar.a(j);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(k);
            this.b.b(eVar);
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(l);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null) {
            eVar.a(m);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null) {
            eVar.a(n);
            eVar.a(this.e);
            eVar.b();
        }
        if (this.f != null && f()) {
            eVar.a(o);
            eVar.a(this.f);
            eVar.b();
        }
        if (this.g != null && g()) {
            eVar.a(p);
            eVar.a(this.g);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public boolean b() {
        return this.b != null;
    }

    public ak c(String str) {
        this.e = str;
        return this;
    }

    public boolean c() {
        return this.c != null;
    }

    public ak d(String str) {
        this.f = str;
        return this;
    }

    public boolean d() {
        return this.d != null;
    }

    public ak e(String str) {
        this.g = str;
        return this;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ak)) {
            return a((ak) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f != null;
    }

    public boolean g() {
        return this.g != null;
    }

    public void h() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.e == null) {
            throw new org.apache.thrift.protocol.f("Required field 'topic' was not present! Struct: " + toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("XmPushActionSubscription(");
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
        sb.append(", ");
        sb.append("topic:");
        if (this.e == null) {
            sb.append("null");
        } else {
            sb.append(this.e);
        }
        if (f()) {
            sb.append(", ");
            sb.append("packageName:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("category:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
