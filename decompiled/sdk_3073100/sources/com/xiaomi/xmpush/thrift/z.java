package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.thrift.meta_data.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class z implements Serializable, Cloneable, org.apache.thrift.a<z, a> {
    public static final Map<a, b> i;
    private static final org.apache.thrift.protocol.j j = new org.apache.thrift.protocol.j("XmPushActionCommand");
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("cmdName", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("cmdArgs", (byte) 15, 6);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("packageName", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("category", (byte) 11, 9);
    public String a;
    public u b;
    public String c;
    public String d;
    public String e;
    public List<String> f;
    public String g;
    public String h;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        CMD_NAME(5, "cmdName"),
        CMD_ARGS(6, "cmdArgs"),
        PACKAGE_NAME(7, "packageName"),
        CATEGORY(9, "category");

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
        enumMap.put(a.CMD_NAME, new b("cmdName", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CMD_ARGS, new b("cmdArgs", (byte) 2, new org.apache.thrift.meta_data.d((byte) 15, new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        i = Collections.unmodifiableMap(enumMap);
        b.a(z.class, i);
    }

    public z a(String str) {
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
                i();
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
                    if (bVarI.b == 15) {
                        org.apache.thrift.protocol.c cVarM = eVar.m();
                        this.f = new ArrayList(cVarM.b);
                        for (int i2 = 0; i2 < cVarM.b; i2++) {
                            this.f.add(eVar.w());
                        }
                        eVar.n();
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
                default:
                    org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    break;
                case 9:
                    if (bVarI.b == 11) {
                        this.h = eVar.w();
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

    public boolean a(z zVar) {
        if (zVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = zVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(zVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = zVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(zVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = zVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(zVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = zVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(zVar.d))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = zVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.e.equals(zVar.e))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = zVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f.equals(zVar.f))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = zVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.g.equals(zVar.g))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = zVar.h();
        return !(zH || zH2) || (zH && zH2 && this.h.equals(zVar.h));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(z zVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        if (!getClass().equals(zVar.getClass())) {
            return getClass().getName().compareTo(zVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(zVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA8 = org.apache.thrift.b.a(this.a, zVar.a)) != 0) {
            return iA8;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(zVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA7 = org.apache.thrift.b.a(this.b, zVar.b)) != 0) {
            return iA7;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(zVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA6 = org.apache.thrift.b.a(this.c, zVar.c)) != 0) {
            return iA6;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(zVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA5 = org.apache.thrift.b.a(this.d, zVar.d)) != 0) {
            return iA5;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(zVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA4 = org.apache.thrift.b.a(this.e, zVar.e)) != 0) {
            return iA4;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(zVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA3 = org.apache.thrift.b.a(this.f, zVar.f)) != 0) {
            return iA3;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(zVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA2 = org.apache.thrift.b.a(this.g, zVar.g)) != 0) {
            return iA2;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(zVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (!h() || (iA = org.apache.thrift.b.a(this.h, zVar.h)) == 0) {
            return 0;
        }
        return iA;
    }

    public z b(String str) {
        this.d = str;
        return this;
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
        if (this.e != null) {
            eVar.a(o);
            eVar.a(this.e);
            eVar.b();
        }
        if (this.f != null && f()) {
            eVar.a(p);
            eVar.a(new org.apache.thrift.protocol.c((byte) 11, this.f.size()));
            Iterator<String> it = this.f.iterator();
            while (it.hasNext()) {
                eVar.a(it.next());
            }
            eVar.e();
            eVar.b();
        }
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

    public z c(String str) {
        this.e = str;
        return this;
    }

    public boolean c() {
        return this.c != null;
    }

    public void d(String str) {
        if (this.f == null) {
            this.f = new ArrayList();
        }
        this.f.add(str);
    }

    public boolean d() {
        return this.d != null;
    }

    public z e(String str) {
        this.g = str;
        return this;
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof z)) {
            return a((z) obj);
        }
        return false;
    }

    public z f(String str) {
        this.h = str;
        return this;
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

    public void i() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'appId' was not present! Struct: " + toString());
        }
        if (this.e == null) {
            throw new org.apache.thrift.protocol.f("Required field 'cmdName' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("XmPushActionCommand(");
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
        sb.append("cmdName:");
        if (this.e == null) {
            sb.append("null");
        } else {
            sb.append(this.e);
        }
        if (f()) {
            sb.append(", ");
            sb.append("cmdArgs:");
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
            sb.append("category:");
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
