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
public class p implements Serializable, Cloneable, org.apache.thrift.a<p, a> {
    public static final Map<a, b> h;
    private static final org.apache.thrift.protocol.j i = new org.apache.thrift.protocol.j("OnlineConfigItem");
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("key", (byte) 8, 1);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("type", (byte) 8, 2);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("clear", (byte) 2, 3);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("intValue", (byte) 8, 4);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("longValue", (byte) 10, 5);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("stringValue", (byte) 11, 6);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("boolValue", (byte) 2, 7);
    public int a;
    public int b;
    public boolean c;
    public int d;
    public long e;
    public String f;
    public boolean g;
    private BitSet q = new BitSet(6);

    public enum a {
        KEY(1, "key"),
        TYPE(2, "type"),
        CLEAR(3, "clear"),
        INT_VALUE(4, "intValue"),
        LONG_VALUE(5, "longValue"),
        STRING_VALUE(6, "stringValue"),
        BOOL_VALUE(7, "boolValue");

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
        enumMap.put(a.KEY, new b("key", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.TYPE, new b("type", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.CLEAR, new b("clear", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.INT_VALUE, new b("intValue", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.LONG_VALUE, new b("longValue", (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.STRING_VALUE, new b("stringValue", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.BOOL_VALUE, new b("boolValue", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        h = Collections.unmodifiableMap(enumMap);
        b.a(p.class, h);
    }

    public int a() {
        return this.a;
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                n();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b != 8) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.a = eVar.t();
                        a(true);
                    }
                    break;
                case 2:
                    if (bVarI.b != 8) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.b = eVar.t();
                        b(true);
                    }
                    break;
                case 3:
                    if (bVarI.b != 2) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.c = eVar.q();
                        c(true);
                    }
                    break;
                case 4:
                    if (bVarI.b != 8) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.d = eVar.t();
                        d(true);
                    }
                    break;
                case 5:
                    if (bVarI.b != 10) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.e = eVar.u();
                        e(true);
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
                    if (bVarI.b != 2) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.g = eVar.q();
                        f(true);
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
        this.q.set(0, z);
    }

    public boolean a(p pVar) {
        if (pVar == null) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = pVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.a == pVar.a)) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = pVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.b == pVar.b)) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = pVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.c == pVar.c)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = pVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.d == pVar.d)) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = pVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.e == pVar.e)) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = pVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.f.equals(pVar.f))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = pVar.m();
        return !(zM || zM2) || (zM && zM2 && this.g == pVar.g);
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(p pVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        if (!getClass().equals(pVar.getClass())) {
            return getClass().getName().compareTo(pVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(pVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (b() && (iA7 = org.apache.thrift.b.a(this.a, pVar.a)) != 0) {
            return iA7;
        }
        int iCompareTo2 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(pVar.d()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (d() && (iA6 = org.apache.thrift.b.a(this.b, pVar.b)) != 0) {
            return iA6;
        }
        int iCompareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(pVar.e()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (e() && (iA5 = org.apache.thrift.b.a(this.c, pVar.c)) != 0) {
            return iA5;
        }
        int iCompareTo4 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(pVar.g()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (g() && (iA4 = org.apache.thrift.b.a(this.d, pVar.d)) != 0) {
            return iA4;
        }
        int iCompareTo5 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(pVar.i()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (i() && (iA3 = org.apache.thrift.b.a(this.e, pVar.e)) != 0) {
            return iA3;
        }
        int iCompareTo6 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(pVar.k()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (k() && (iA2 = org.apache.thrift.b.a(this.f, pVar.f)) != 0) {
            return iA2;
        }
        int iCompareTo7 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(pVar.m()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (!m() || (iA = org.apache.thrift.b.a(this.g, pVar.g)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) {
        n();
        eVar.a(i);
        if (b()) {
            eVar.a(j);
            eVar.a(this.a);
            eVar.b();
        }
        if (d()) {
            eVar.a(k);
            eVar.a(this.b);
            eVar.b();
        }
        if (e()) {
            eVar.a(l);
            eVar.a(this.c);
            eVar.b();
        }
        if (g()) {
            eVar.a(m);
            eVar.a(this.d);
            eVar.b();
        }
        if (i()) {
            eVar.a(n);
            eVar.a(this.e);
            eVar.b();
        }
        if (this.f != null && k()) {
            eVar.a(o);
            eVar.a(this.f);
            eVar.b();
        }
        if (m()) {
            eVar.a(p);
            eVar.a(this.g);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z) {
        this.q.set(1, z);
    }

    public boolean b() {
        return this.q.get(0);
    }

    public int c() {
        return this.b;
    }

    public void c(boolean z) {
        this.q.set(2, z);
    }

    public void d(boolean z) {
        this.q.set(3, z);
    }

    public boolean d() {
        return this.q.get(1);
    }

    public void e(boolean z) {
        this.q.set(4, z);
    }

    public boolean e() {
        return this.q.get(2);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof p)) {
            return a((p) obj);
        }
        return false;
    }

    public int f() {
        return this.d;
    }

    public void f(boolean z) {
        this.q.set(5, z);
    }

    public boolean g() {
        return this.q.get(3);
    }

    public long h() {
        return this.e;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.q.get(4);
    }

    public String j() {
        return this.f;
    }

    public boolean k() {
        return this.f != null;
    }

    public boolean l() {
        return this.g;
    }

    public boolean m() {
        return this.q.get(5);
    }

    public void n() {
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("OnlineConfigItem(");
        boolean z2 = true;
        if (b()) {
            sb.append("key:");
            sb.append(this.a);
            z2 = false;
        }
        if (d()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("type:");
            sb.append(this.b);
            z2 = false;
        }
        if (e()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("clear:");
            sb.append(this.c);
            z2 = false;
        }
        if (g()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("intValue:");
            sb.append(this.d);
            z2 = false;
        }
        if (i()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("longValue:");
            sb.append(this.e);
            z2 = false;
        }
        if (k()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("stringValue:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        } else {
            z = z2;
        }
        if (m()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append("boolValue:");
            sb.append(this.g);
        }
        sb.append(")");
        return sb.toString();
    }
}
