package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.thrift.meta_data.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class j implements Serializable, Cloneable, org.apache.thrift.a<j, a> {
    public static final Map<a, b> k;
    private static final org.apache.thrift.protocol.j l = new org.apache.thrift.protocol.j("GeoFencing");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("id", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("name", (byte) 11, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("appId", (byte) 10, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("packageName", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("createTime", (byte) 10, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("type", (byte) 8, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("circleCenter", (byte) 12, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("circleRadius", (byte) 4, 9);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("polygonPoints", (byte) 15, 10);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("coordinateProvider", (byte) 8, 11);
    public String a;
    public String b;
    public long c;
    public String d;
    public long e;
    public k f;
    public l g;
    public double h;
    public List<l> i;
    public h j;
    private BitSet w = new BitSet(3);

    public enum a {
        ID(1, "id"),
        NAME(2, "name"),
        APP_ID(3, "appId"),
        PACKAGE_NAME(4, "packageName"),
        CREATE_TIME(5, "createTime"),
        TYPE(6, "type"),
        CIRCLE_CENTER(7, "circleCenter"),
        CIRCLE_RADIUS(9, "circleRadius"),
        POLYGON_POINTS(10, "polygonPoints"),
        COORDINATE_PROVIDER(11, "coordinateProvider");

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
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.NAME, new b("name", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CREATE_TIME, new b("createTime", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.TYPE, new b("type", (byte) 1, new org.apache.thrift.meta_data.a((byte) 16, k.class)));
        enumMap.put(a.CIRCLE_CENTER, new b("circleCenter", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, l.class)));
        enumMap.put(a.CIRCLE_RADIUS, new b("circleRadius", (byte) 2, new org.apache.thrift.meta_data.c((byte) 4)));
        enumMap.put(a.POLYGON_POINTS, new b("polygonPoints", (byte) 2, new org.apache.thrift.meta_data.d((byte) 15, new org.apache.thrift.meta_data.g((byte) 12, l.class))));
        enumMap.put(a.COORDINATE_PROVIDER, new b("coordinateProvider", (byte) 1, new org.apache.thrift.meta_data.a((byte) 16, h.class)));
        k = Collections.unmodifiableMap(enumMap);
        b.a(j.class, k);
    }

    public j a(double d) {
        this.h = d;
        c(true);
        return this;
    }

    public j a(long j) {
        this.c = j;
        a(true);
        return this;
    }

    public j a(h hVar) {
        this.j = hVar;
        return this;
    }

    public j a(k kVar) {
        this.f = kVar;
        return this;
    }

    public j a(l lVar) {
        this.g = lVar;
        return this;
    }

    public j a(String str) {
        this.a = str;
        return this;
    }

    public j a(List<l> list) {
        this.i = list;
        return this;
    }

    public String a() {
        return this.a;
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!f()) {
                    throw new org.apache.thrift.protocol.f("Required field 'appId' was not found in serialized data! Struct: " + toString());
                }
                if (!j()) {
                    throw new org.apache.thrift.protocol.f("Required field 'createTime' was not found in serialized data! Struct: " + toString());
                }
                u();
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
                    if (bVarI.b == 11) {
                        this.b = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 3:
                    if (bVarI.b == 10) {
                        this.c = eVar.u();
                        a(true);
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
                    if (bVarI.b == 10) {
                        this.e = eVar.u();
                        b(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 6:
                    if (bVarI.b == 8) {
                        this.f = k.a(eVar.t());
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 7:
                    if (bVarI.b == 12) {
                        this.g = new l();
                        this.g.a(eVar);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 8:
                default:
                    org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    break;
                case 9:
                    if (bVarI.b == 4) {
                        this.h = eVar.v();
                        c(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 10:
                    if (bVarI.b == 15) {
                        org.apache.thrift.protocol.c cVarM = eVar.m();
                        this.i = new ArrayList(cVarM.b);
                        for (int i = 0; i < cVarM.b; i++) {
                            l lVar = new l();
                            lVar.a(eVar);
                            this.i.add(lVar);
                        }
                        eVar.n();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 11:
                    if (bVarI.b == 8) {
                        this.j = h.a(eVar.t());
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
            }
            eVar.j();
        }
    }

    public void a(boolean z) {
        this.w.set(0, z);
    }

    public boolean a(j jVar) {
        if (jVar == null) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = jVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.a.equals(jVar.a))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = jVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.b.equals(jVar.b))) || this.c != jVar.c) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = jVar.h();
        if (((zH || zH2) && !(zH && zH2 && this.d.equals(jVar.d))) || this.e != jVar.e) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = jVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.f.equals(jVar.f))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = jVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.g.a(jVar.g))) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = jVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.h == jVar.h)) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = jVar.r();
        if ((zR || zR2) && !(zR && zR2 && this.i.equals(jVar.i))) {
            return false;
        }
        boolean zT = t();
        boolean zT2 = jVar.t();
        return !(zT || zT2) || (zT && zT2 && this.j.equals(jVar.j));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(j jVar) {
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
        if (!getClass().equals(jVar.getClass())) {
            return getClass().getName().compareTo(jVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(jVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (b() && (iA10 = org.apache.thrift.b.a(this.a, jVar.a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(jVar.d()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (d() && (iA9 = org.apache.thrift.b.a(this.b, jVar.b)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(jVar.f()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (f() && (iA8 = org.apache.thrift.b.a(this.c, jVar.c)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(jVar.h()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (h() && (iA7 = org.apache.thrift.b.a(this.d, jVar.d)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(jVar.j()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (j() && (iA6 = org.apache.thrift.b.a(this.e, jVar.e)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(jVar.l()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (l() && (iA5 = org.apache.thrift.b.a(this.f, jVar.f)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(jVar.n()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (n() && (iA4 = org.apache.thrift.b.a(this.g, jVar.g)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(jVar.p()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (p() && (iA3 = org.apache.thrift.b.a(this.h, jVar.h)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(jVar.r()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (r() && (iA2 = org.apache.thrift.b.a(this.i, jVar.i)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(jVar.t()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!t() || (iA = org.apache.thrift.b.a(this.j, jVar.j)) == 0) {
            return 0;
        }
        return iA;
    }

    public j b(long j) {
        this.e = j;
        b(true);
        return this;
    }

    public j b(String str) {
        this.b = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        u();
        eVar.a(l);
        if (this.a != null) {
            eVar.a(m);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null) {
            eVar.a(n);
            eVar.a(this.b);
            eVar.b();
        }
        eVar.a(o);
        eVar.a(this.c);
        eVar.b();
        if (this.d != null) {
            eVar.a(p);
            eVar.a(this.d);
            eVar.b();
        }
        eVar.a(q);
        eVar.a(this.e);
        eVar.b();
        if (this.f != null) {
            eVar.a(r);
            eVar.a(this.f.a());
            eVar.b();
        }
        if (this.g != null && n()) {
            eVar.a(s);
            this.g.b(eVar);
            eVar.b();
        }
        if (p()) {
            eVar.a(t);
            eVar.a(this.h);
            eVar.b();
        }
        if (this.i != null && r()) {
            eVar.a(u);
            eVar.a(new org.apache.thrift.protocol.c((byte) 12, this.i.size()));
            Iterator<l> it = this.i.iterator();
            while (it.hasNext()) {
                it.next().b(eVar);
            }
            eVar.e();
            eVar.b();
        }
        if (this.j != null) {
            eVar.a(v);
            eVar.a(this.j.a());
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z) {
        this.w.set(1, z);
    }

    public boolean b() {
        return this.a != null;
    }

    public j c(String str) {
        this.d = str;
        return this;
    }

    public String c() {
        return this.b;
    }

    public void c(boolean z) {
        this.w.set(2, z);
    }

    public boolean d() {
        return this.b != null;
    }

    public long e() {
        return this.c;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof j)) {
            return a((j) obj);
        }
        return false;
    }

    public boolean f() {
        return this.w.get(0);
    }

    public String g() {
        return this.d;
    }

    public boolean h() {
        return this.d != null;
    }

    public int hashCode() {
        return 0;
    }

    public long i() {
        return this.e;
    }

    public boolean j() {
        return this.w.get(1);
    }

    public k k() {
        return this.f;
    }

    public boolean l() {
        return this.f != null;
    }

    public l m() {
        return this.g;
    }

    public boolean n() {
        return this.g != null;
    }

    public double o() {
        return this.h;
    }

    public boolean p() {
        return this.w.get(2);
    }

    public List<l> q() {
        return this.i;
    }

    public boolean r() {
        return this.i != null;
    }

    public h s() {
        return this.j;
    }

    public boolean t() {
        return this.j != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GeoFencing(");
        sb.append("id:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(", ");
        sb.append("name:");
        if (this.b == null) {
            sb.append("null");
        } else {
            sb.append(this.b);
        }
        sb.append(", ");
        sb.append("appId:");
        sb.append(this.c);
        sb.append(", ");
        sb.append("packageName:");
        if (this.d == null) {
            sb.append("null");
        } else {
            sb.append(this.d);
        }
        sb.append(", ");
        sb.append("createTime:");
        sb.append(this.e);
        sb.append(", ");
        sb.append("type:");
        if (this.f == null) {
            sb.append("null");
        } else {
            sb.append(this.f);
        }
        if (n()) {
            sb.append(", ");
            sb.append("circleCenter:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (p()) {
            sb.append(", ");
            sb.append("circleRadius:");
            sb.append(this.h);
        }
        if (r()) {
            sb.append(", ");
            sb.append("polygonPoints:");
            if (this.i == null) {
                sb.append("null");
            } else {
                sb.append(this.i);
            }
        }
        sb.append(", ");
        sb.append("coordinateProvider:");
        if (this.j == null) {
            sb.append("null");
        } else {
            sb.append(this.j);
        }
        sb.append(")");
        return sb.toString();
    }

    public void u() throws org.apache.thrift.protocol.f {
        if (this.a == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.b == null) {
            throw new org.apache.thrift.protocol.f("Required field 'name' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'packageName' was not present! Struct: " + toString());
        }
        if (this.f == null) {
            throw new org.apache.thrift.protocol.f("Required field 'type' was not present! Struct: " + toString());
        }
        if (this.j == null) {
            throw new org.apache.thrift.protocol.f("Required field 'coordinateProvider' was not present! Struct: " + toString());
        }
    }
}
