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
public class aj implements Serializable, Cloneable, org.apache.thrift.a<aj, a> {
    public static final Map<a, b> m;
    private static final org.apache.thrift.protocol.j n = new org.apache.thrift.protocol.j("XmPushActionSendMessage");
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("packageName", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("topic", (byte) 11, 6);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("aliasName", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("message", (byte) 12, 8);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("needAck", (byte) 2, 9);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("params", (byte) 13, 10);
    private static final org.apache.thrift.protocol.b y = new org.apache.thrift.protocol.b("category", (byte) 11, 11);
    private static final org.apache.thrift.protocol.b z = new org.apache.thrift.protocol.b("userAccount", (byte) 11, 12);
    public String a;
    public u b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public q h;
    public Map<String, String> j;
    public String k;
    public String l;
    private BitSet A = new BitSet(1);
    public boolean i = true;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        PACKAGE_NAME(5, "packageName"),
        TOPIC(6, "topic"),
        ALIAS_NAME(7, "aliasName"),
        MESSAGE(8, "message"),
        NEED_ACK(9, "needAck"),
        PARAMS(10, "params"),
        CATEGORY(11, "category"),
        USER_ACCOUNT(12, "userAccount");

        private static final Map<String, a> m = new HashMap();
        private final short n;
        private final String o;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                m.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.n = s;
            this.o = str;
        }

        public String a() {
            return this.o;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new b("target", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TOPIC, new b("topic", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.ALIAS_NAME, new b("aliasName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.MESSAGE, new b("message", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, q.class)));
        enumMap.put(a.NEED_ACK, new b("needAck", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.PARAMS, new b("params", (byte) 2, new org.apache.thrift.meta_data.e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.USER_ACCOUNT, new b("userAccount", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        m = Collections.unmodifiableMap(enumMap);
        b.a(aj.class, m);
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                t();
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
                    if (bVarI.b == 11) {
                        this.f = eVar.w();
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
                    if (bVarI.b == 12) {
                        this.h = new q();
                        this.h.a(eVar);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 9:
                    if (bVarI.b == 2) {
                        this.i = eVar.q();
                        a(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 10:
                    if (bVarI.b == 13) {
                        org.apache.thrift.protocol.d dVarK = eVar.k();
                        this.j = new HashMap(dVarK.c * 2);
                        for (int i = 0; i < dVarK.c; i++) {
                            this.j.put(eVar.w(), eVar.w());
                        }
                        eVar.l();
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
                case 12:
                    if (bVarI.b == 11) {
                        this.l = eVar.w();
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

    public void a(boolean z2) {
        this.A.set(0, z2);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(aj ajVar) {
        if (ajVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = ajVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(ajVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = ajVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(ajVar.b))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = ajVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.c.equals(ajVar.c))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = ajVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.d.equals(ajVar.d))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = ajVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.e.equals(ajVar.e))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = ajVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.f.equals(ajVar.f))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = ajVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.g.equals(ajVar.g))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = ajVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.h.a(ajVar.h))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = ajVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.i == ajVar.i)) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = ajVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.j.equals(ajVar.j))) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = ajVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.k.equals(ajVar.k))) {
            return false;
        }
        boolean zS = s();
        boolean zS2 = ajVar.s();
        return !(zS || zS2) || (zS && zS2 && this.l.equals(ajVar.l));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(aj ajVar) {
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
        int iA12;
        if (!getClass().equals(ajVar.getClass())) {
            return getClass().getName().compareTo(ajVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(ajVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA12 = org.apache.thrift.b.a(this.a, ajVar.a)) != 0) {
            return iA12;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ajVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA11 = org.apache.thrift.b.a(this.b, ajVar.b)) != 0) {
            return iA11;
        }
        int iCompareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ajVar.d()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (d() && (iA10 = org.apache.thrift.b.a(this.c, ajVar.c)) != 0) {
            return iA10;
        }
        int iCompareTo4 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ajVar.f()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (f() && (iA9 = org.apache.thrift.b.a(this.d, ajVar.d)) != 0) {
            return iA9;
        }
        int iCompareTo5 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(ajVar.g()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (g() && (iA8 = org.apache.thrift.b.a(this.e, ajVar.e)) != 0) {
            return iA8;
        }
        int iCompareTo6 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(ajVar.i()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (i() && (iA7 = org.apache.thrift.b.a(this.f, ajVar.f)) != 0) {
            return iA7;
        }
        int iCompareTo7 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(ajVar.k()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (k() && (iA6 = org.apache.thrift.b.a(this.g, ajVar.g)) != 0) {
            return iA6;
        }
        int iCompareTo8 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(ajVar.m()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (m() && (iA5 = org.apache.thrift.b.a(this.h, ajVar.h)) != 0) {
            return iA5;
        }
        int iCompareTo9 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(ajVar.n()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (n() && (iA4 = org.apache.thrift.b.a(this.i, ajVar.i)) != 0) {
            return iA4;
        }
        int iCompareTo10 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(ajVar.o()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (o() && (iA3 = org.apache.thrift.b.a(this.j, ajVar.j)) != 0) {
            return iA3;
        }
        int iCompareTo11 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(ajVar.q()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (q() && (iA2 = org.apache.thrift.b.a(this.k, ajVar.k)) != 0) {
            return iA2;
        }
        int iCompareTo12 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(ajVar.s()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (!s() || (iA = org.apache.thrift.b.a(this.l, ajVar.l)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        t();
        eVar.a(n);
        if (this.a != null && a()) {
            eVar.a(o);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(p);
            this.b.b(eVar);
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(q);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null) {
            eVar.a(r);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null && g()) {
            eVar.a(s);
            eVar.a(this.e);
            eVar.b();
        }
        if (this.f != null && i()) {
            eVar.a(t);
            eVar.a(this.f);
            eVar.b();
        }
        if (this.g != null && k()) {
            eVar.a(u);
            eVar.a(this.g);
            eVar.b();
        }
        if (this.h != null && m()) {
            eVar.a(v);
            this.h.b(eVar);
            eVar.b();
        }
        if (n()) {
            eVar.a(w);
            eVar.a(this.i);
            eVar.b();
        }
        if (this.j != null && o()) {
            eVar.a(x);
            eVar.a(new org.apache.thrift.protocol.d((byte) 11, (byte) 11, this.j.size()));
            for (Map.Entry<String, String> entry : this.j.entrySet()) {
                eVar.a(entry.getKey());
                eVar.a(entry.getValue());
            }
            eVar.d();
            eVar.b();
        }
        if (this.k != null && q()) {
            eVar.a(y);
            eVar.a(this.k);
            eVar.b();
        }
        if (this.l != null && s()) {
            eVar.a(z);
            eVar.a(this.l);
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

    public String e() {
        return this.d;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof aj)) {
            return a((aj) obj);
        }
        return false;
    }

    public boolean f() {
        return this.d != null;
    }

    public boolean g() {
        return this.e != null;
    }

    public String h() {
        return this.f;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.f != null;
    }

    public String j() {
        return this.g;
    }

    public boolean k() {
        return this.g != null;
    }

    public q l() {
        return this.h;
    }

    public boolean m() {
        return this.h != null;
    }

    public boolean n() {
        return this.A.get(0);
    }

    public boolean o() {
        return this.j != null;
    }

    public String p() {
        return this.k;
    }

    public boolean q() {
        return this.k != null;
    }

    public String r() {
        return this.l;
    }

    public boolean s() {
        return this.l != null;
    }

    public void t() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'appId' was not present! Struct: " + toString());
        }
    }

    public String toString() {
        boolean z2 = false;
        StringBuilder sb = new StringBuilder("XmPushActionSendMessage(");
        boolean z3 = true;
        if (a()) {
            sb.append("debug:");
            if (this.a == null) {
                sb.append("null");
            } else {
                sb.append(this.a);
            }
            z3 = false;
        }
        if (b()) {
            if (!z3) {
                sb.append(", ");
            }
            sb.append("target:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
        } else {
            z2 = z3;
        }
        if (!z2) {
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
        if (g()) {
            sb.append(", ");
            sb.append("packageName:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("topic:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("aliasName:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("message:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("needAck:");
            sb.append(this.i);
        }
        if (o()) {
            sb.append(", ");
            sb.append("params:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (q()) {
            sb.append(", ");
            sb.append("category:");
            if (this.k == null) {
                sb.append("null");
            } else {
                sb.append(this.k);
            }
        }
        if (s()) {
            sb.append(", ");
            sb.append("userAccount:");
            if (this.l == null) {
                sb.append("null");
            } else {
                sb.append(this.l);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
