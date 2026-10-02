package com.xiaomi.push.thrift;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.f;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.h;
import org.apache.thrift.protocol.j;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class b implements Serializable, Cloneable, org.apache.thrift.a<b, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> k;
    private static final j l = new j("StatsEvent");
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("chid", (byte) 3, 1);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("type", (byte) 8, 2);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("value", (byte) 8, 3);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("connpt", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("host", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("subvalue", (byte) 8, 6);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("annotation", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("user", (byte) 11, 8);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("time", (byte) 8, 9);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("clientIp", (byte) 8, 10);
    public byte a;
    public int b;
    public int c;
    public String d;
    public String e;
    public int f;
    public String g;
    public String h;
    public int i;
    public int j;
    private BitSet w = new BitSet(6);

    public enum a {
        CHID(1, "chid"),
        TYPE(2, "type"),
        VALUE(3, "value"),
        CONNPT(4, "connpt"),
        HOST(5, "host"),
        SUBVALUE(6, "subvalue"),
        ANNOTATION(7, "annotation"),
        USER(8, "user"),
        TIME(9, "time"),
        CLIENT_IP(10, "clientIp");

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
        enumMap.put(a.CHID, new org.apache.thrift.meta_data.b("chid", (byte) 1, new org.apache.thrift.meta_data.c((byte) 3)));
        enumMap.put(a.TYPE, new org.apache.thrift.meta_data.b("type", (byte) 1, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.VALUE, new org.apache.thrift.meta_data.b("value", (byte) 1, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.CONNPT, new org.apache.thrift.meta_data.b("connpt", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.HOST, new org.apache.thrift.meta_data.b("host", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.SUBVALUE, new org.apache.thrift.meta_data.b("subvalue", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.ANNOTATION, new org.apache.thrift.meta_data.b("annotation", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.USER, new org.apache.thrift.meta_data.b("user", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TIME, new org.apache.thrift.meta_data.b("time", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.CLIENT_IP, new org.apache.thrift.meta_data.b("clientIp", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        k = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(b.class, k);
    }

    public b a(byte b) {
        this.a = b;
        a(true);
        return this;
    }

    public b a(int i) {
        this.b = i;
        b(true);
        return this;
    }

    public b a(String str) {
        this.d = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void a(e eVar) throws f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!a()) {
                    throw new org.apache.thrift.protocol.f("Required field 'chid' was not found in serialized data! Struct: " + toString());
                }
                if (!b()) {
                    throw new org.apache.thrift.protocol.f("Required field 'type' was not found in serialized data! Struct: " + toString());
                }
                if (!c()) {
                    throw new org.apache.thrift.protocol.f("Required field 'value' was not found in serialized data! Struct: " + toString());
                }
                k();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b != 3) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.a = eVar.r();
                        a(true);
                    }
                    break;
                case 2:
                    if (bVarI.b != 8) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.b = eVar.t();
                        b(true);
                    }
                    break;
                case 3:
                    if (bVarI.b != 8) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.c = eVar.t();
                        c(true);
                    }
                    break;
                case 4:
                    if (bVarI.b != 11) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.d = eVar.w();
                    }
                    break;
                case 5:
                    if (bVarI.b != 11) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.e = eVar.w();
                    }
                    break;
                case 6:
                    if (bVarI.b != 8) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.f = eVar.t();
                        d(true);
                    }
                    break;
                case 7:
                    if (bVarI.b != 11) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.g = eVar.w();
                    }
                    break;
                case 8:
                    if (bVarI.b != 11) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.h = eVar.w();
                    }
                    break;
                case 9:
                    if (bVarI.b != 8) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.i = eVar.t();
                        e(true);
                    }
                    break;
                case 10:
                    if (bVarI.b != 8) {
                        h.a(eVar, bVarI.b);
                    } else {
                        this.j = eVar.t();
                        f(true);
                    }
                    break;
                default:
                    h.a(eVar, bVarI.b);
                    break;
            }
            eVar.j();
        }
    }

    public void a(boolean z) {
        this.w.set(0, z);
    }

    public boolean a() {
        return this.w.get(0);
    }

    public boolean a(b bVar) {
        if (bVar == null || this.a != bVar.a || this.b != bVar.b || this.c != bVar.c) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = bVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(bVar.d))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = bVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.e.equals(bVar.e))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = bVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f == bVar.f)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = bVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.g.equals(bVar.g))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = bVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.h.equals(bVar.h))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = bVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.i == bVar.i)) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = bVar.j();
        return !(zJ || zJ2) || (zJ && zJ2 && this.j == bVar.j);
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(b bVar) {
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
        if (!getClass().equals(bVar.getClass())) {
            return getClass().getName().compareTo(bVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(bVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA10 = org.apache.thrift.b.a(this.a, bVar.a)) != 0) {
            return iA10;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(bVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA9 = org.apache.thrift.b.a(this.b, bVar.b)) != 0) {
            return iA9;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(bVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA8 = org.apache.thrift.b.a(this.c, bVar.c)) != 0) {
            return iA8;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(bVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA7 = org.apache.thrift.b.a(this.d, bVar.d)) != 0) {
            return iA7;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(bVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA6 = org.apache.thrift.b.a(this.e, bVar.e)) != 0) {
            return iA6;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(bVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA5 = org.apache.thrift.b.a(this.f, bVar.f)) != 0) {
            return iA5;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(bVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA4 = org.apache.thrift.b.a(this.g, bVar.g)) != 0) {
            return iA4;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(bVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA3 = org.apache.thrift.b.a(this.h, bVar.h)) != 0) {
            return iA3;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(bVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA2 = org.apache.thrift.b.a(this.i, bVar.i)) != 0) {
            return iA2;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(bVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (!j() || (iA = org.apache.thrift.b.a(this.j, bVar.j)) == 0) {
            return 0;
        }
        return iA;
    }

    public b b(int i) {
        this.c = i;
        c(true);
        return this;
    }

    public b b(String str) {
        this.e = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void b(e eVar) {
        k();
        eVar.a(l);
        eVar.a(m);
        eVar.a(this.a);
        eVar.b();
        eVar.a(n);
        eVar.a(this.b);
        eVar.b();
        eVar.a(o);
        eVar.a(this.c);
        eVar.b();
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
        if (this.h != null && h()) {
            eVar.a(t);
            eVar.a(this.h);
            eVar.b();
        }
        if (i()) {
            eVar.a(u);
            eVar.a(this.i);
            eVar.b();
        }
        if (j()) {
            eVar.a(v);
            eVar.a(this.j);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z) {
        this.w.set(1, z);
    }

    public boolean b() {
        return this.w.get(1);
    }

    public b c(int i) {
        this.f = i;
        d(true);
        return this;
    }

    public b c(String str) {
        this.g = str;
        return this;
    }

    public void c(boolean z) {
        this.w.set(2, z);
    }

    public boolean c() {
        return this.w.get(2);
    }

    public b d(int i) {
        this.i = i;
        e(true);
        return this;
    }

    public b d(String str) {
        this.h = str;
        return this;
    }

    public void d(boolean z) {
        this.w.set(3, z);
    }

    public boolean d() {
        return this.d != null;
    }

    public b e(int i) {
        this.j = i;
        f(true);
        return this;
    }

    public void e(boolean z) {
        this.w.set(4, z);
    }

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof b)) {
            return a((b) obj);
        }
        return false;
    }

    public void f(boolean z) {
        this.w.set(5, z);
    }

    public boolean f() {
        return this.w.get(3);
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
        return this.w.get(4);
    }

    public boolean j() {
        return this.w.get(5);
    }

    public void k() throws org.apache.thrift.protocol.f {
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'connpt' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvent(");
        sb.append("chid:");
        sb.append((int) this.a);
        sb.append(", ");
        sb.append("type:");
        sb.append(this.b);
        sb.append(", ");
        sb.append("value:");
        sb.append(this.c);
        sb.append(", ");
        sb.append("connpt:");
        if (this.d == null) {
            sb.append("null");
        } else {
            sb.append(this.d);
        }
        if (e()) {
            sb.append(", ");
            sb.append("host:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("subvalue:");
            sb.append(this.f);
        }
        if (g()) {
            sb.append(", ");
            sb.append("annotation:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("user:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("time:");
            sb.append(this.i);
        }
        if (j()) {
            sb.append(", ");
            sb.append("clientIp:");
            sb.append(this.j);
        }
        sb.append(")");
        return sb.toString();
    }
}
