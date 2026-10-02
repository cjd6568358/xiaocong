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
public class aa implements Serializable, Cloneable, org.apache.thrift.a<aa, a> {
    public static final Map<a, b> l;
    private static final org.apache.thrift.protocol.j m = new org.apache.thrift.protocol.j("XmPushActionCommandResult");
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("cmdName", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("request", (byte) 12, 6);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("errorCode", (byte) 10, 7);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("reason", (byte) 11, 8);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("packageName", (byte) 11, 9);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("cmdArgs", (byte) 15, 10);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("category", (byte) 11, 12);
    public String a;
    public u b;
    public String c;
    public String d;
    public String e;
    public z f;
    public long g;
    public String h;
    public String i;
    public List<String> j;
    public String k;
    private BitSet y = new BitSet(1);

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        CMD_NAME(5, "cmdName"),
        REQUEST(6, "request"),
        ERROR_CODE(7, "errorCode"),
        REASON(8, "reason"),
        PACKAGE_NAME(9, "packageName"),
        CMD_ARGS(10, "cmdArgs"),
        CATEGORY(12, "category");

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
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CMD_NAME, new b("cmdName", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.REQUEST, new b("request", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, z.class)));
        enumMap.put(a.ERROR_CODE, new b("errorCode", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.REASON, new b("reason", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CMD_ARGS, new b("cmdArgs", (byte) 2, new org.apache.thrift.meta_data.d((byte) 15, new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        l = Collections.unmodifiableMap(enumMap);
        b.a(aa.class, l);
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
                    if (bVarI.b == 12) {
                        this.f = new z();
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
                    if (bVarI.b == 11) {
                        this.i = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 10:
                    if (bVarI.b == 15) {
                        org.apache.thrift.protocol.c cVarM = eVar.m();
                        this.j = new ArrayList(cVarM.b);
                        for (int i = 0; i < cVarM.b; i++) {
                            this.j.add(eVar.w());
                        }
                        eVar.n();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 11:
                default:
                    org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    break;
                case 12:
                    if (bVarI.b == 11) {
                        this.k = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
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

    public boolean a(aa aaVar) {
        if (aaVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = aaVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(aaVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = aaVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(aaVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = aaVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(aaVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = aaVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(aaVar.d))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = aaVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.e.equals(aaVar.e))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = aaVar.g();
        if (((zG || zG2) && !(zG && zG2 && this.f.a(aaVar.f))) || this.g != aaVar.g) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = aaVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.h.equals(aaVar.h))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = aaVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.i.equals(aaVar.i))) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = aaVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.j.equals(aaVar.j))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = aaVar.n();
        return !(zN || zN2) || (zN && zN2 && this.k.equals(aaVar.k));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(aa aaVar) {
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
        if (!getClass().equals(aaVar.getClass())) {
            return getClass().getName().compareTo(aaVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(aaVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA11 = org.apache.thrift.b.a(this.a, aaVar.a)) != 0) {
            return iA11;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(aaVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA10 = org.apache.thrift.b.a(this.b, aaVar.b)) != 0) {
            return iA10;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(aaVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA9 = org.apache.thrift.b.a(this.c, aaVar.c)) != 0) {
            return iA9;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(aaVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA8 = org.apache.thrift.b.a(this.d, aaVar.d)) != 0) {
            return iA8;
        }
        int iCompareTo5 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(aaVar.f()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (f() && (iA7 = org.apache.thrift.b.a(this.e, aaVar.e)) != 0) {
            return iA7;
        }
        int iCompareTo6 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(aaVar.g()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (g() && (iA6 = org.apache.thrift.b.a(this.f, aaVar.f)) != 0) {
            return iA6;
        }
        int iCompareTo7 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(aaVar.h()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (h() && (iA5 = org.apache.thrift.b.a(this.g, aaVar.g)) != 0) {
            return iA5;
        }
        int iCompareTo8 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(aaVar.i()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (i() && (iA4 = org.apache.thrift.b.a(this.h, aaVar.h)) != 0) {
            return iA4;
        }
        int iCompareTo9 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(aaVar.j()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (j() && (iA3 = org.apache.thrift.b.a(this.i, aaVar.i)) != 0) {
            return iA3;
        }
        int iCompareTo10 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(aaVar.l()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (l() && (iA2 = org.apache.thrift.b.a(this.j, aaVar.j)) != 0) {
            return iA2;
        }
        int iCompareTo11 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(aaVar.n()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (!n() || (iA = org.apache.thrift.b.a(this.k, aaVar.k)) == 0) {
            return 0;
        }
        return iA;
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
        if (this.d != null) {
            eVar.a(q);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null) {
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
            eVar.a(this.i);
            eVar.b();
        }
        if (this.j != null && l()) {
            eVar.a(w);
            eVar.a(new org.apache.thrift.protocol.c((byte) 11, this.j.size()));
            Iterator<String> it = this.j.iterator();
            while (it.hasNext()) {
                eVar.a(it.next());
            }
            eVar.e();
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

    public boolean b() {
        return this.b != null;
    }

    public boolean c() {
        return this.c != null;
    }

    public boolean d() {
        return this.d != null;
    }

    public String e() {
        return this.e;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof aa)) {
            return a((aa) obj);
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

    public List<String> k() {
        return this.j;
    }

    public boolean l() {
        return this.j != null;
    }

    public String m() {
        return this.k;
    }

    public boolean n() {
        return this.k != null;
    }

    public void o() throws org.apache.thrift.protocol.f {
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
        StringBuilder sb = new StringBuilder("XmPushActionCommandResult(");
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
            sb.append("packageName:");
            if (this.i == null) {
                sb.append("null");
            } else {
                sb.append(this.i);
            }
        }
        if (l()) {
            sb.append(", ");
            sb.append("cmdArgs:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (n()) {
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
