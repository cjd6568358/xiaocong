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
public class r implements Serializable, Cloneable, org.apache.thrift.a<r, a> {
    public static final Map<a, b> m;
    private static final org.apache.thrift.protocol.j n = new org.apache.thrift.protocol.j("PushMetaInfo");
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("id", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("messageTs", (byte) 10, 2);
    private static final org.apache.thrift.protocol.b q = new org.apache.thrift.protocol.b("topic", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b r = new org.apache.thrift.protocol.b("title", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b s = new org.apache.thrift.protocol.b("description", (byte) 11, 5);
    private static final org.apache.thrift.protocol.b t = new org.apache.thrift.protocol.b("notifyType", (byte) 8, 6);
    private static final org.apache.thrift.protocol.b u = new org.apache.thrift.protocol.b("url", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b v = new org.apache.thrift.protocol.b("passThrough", (byte) 8, 8);
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("notifyId", (byte) 8, 9);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("extra", (byte) 13, 10);
    private static final org.apache.thrift.protocol.b y = new org.apache.thrift.protocol.b("internal", (byte) 13, 11);
    private static final org.apache.thrift.protocol.b z = new org.apache.thrift.protocol.b("ignoreRegInfo", (byte) 2, 12);
    private BitSet A;
    public String a;
    public long b;
    public String c;
    public String d;
    public String e;
    public int f;
    public String g;
    public int h;
    public int i;
    public Map<String, String> j;
    public Map<String, String> k;
    public boolean l;

    public enum a {
        ID(1, "id"),
        MESSAGE_TS(2, "messageTs"),
        TOPIC(3, "topic"),
        TITLE(4, "title"),
        DESCRIPTION(5, "description"),
        NOTIFY_TYPE(6, "notifyType"),
        URL(7, "url"),
        PASS_THROUGH(8, "passThrough"),
        NOTIFY_ID(9, "notifyId"),
        EXTRA(10, "extra"),
        INTERNAL(11, "internal"),
        IGNORE_REG_INFO(12, "ignoreRegInfo");

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
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.MESSAGE_TS, new b("messageTs", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.TOPIC, new b("topic", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TITLE, new b("title", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.DESCRIPTION, new b("description", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.NOTIFY_TYPE, new b("notifyType", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.URL, new b("url", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PASS_THROUGH, new b("passThrough", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.NOTIFY_ID, new b("notifyId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.EXTRA, new b("extra", (byte) 2, new org.apache.thrift.meta_data.e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.INTERNAL, new b("internal", (byte) 2, new org.apache.thrift.meta_data.e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.IGNORE_REG_INFO, new b("ignoreRegInfo", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        m = Collections.unmodifiableMap(enumMap);
        b.a(r.class, m);
    }

    public r() {
        this.A = new BitSet(5);
        this.l = false;
    }

    public r(r rVar) {
        this.A = new BitSet(5);
        this.A.clear();
        this.A.or(rVar.A);
        if (rVar.c()) {
            this.a = rVar.a;
        }
        this.b = rVar.b;
        if (rVar.g()) {
            this.c = rVar.c;
        }
        if (rVar.i()) {
            this.d = rVar.d;
        }
        if (rVar.k()) {
            this.e = rVar.e;
        }
        this.f = rVar.f;
        if (rVar.n()) {
            this.g = rVar.g;
        }
        this.h = rVar.h;
        this.i = rVar.i;
        if (rVar.t()) {
            HashMap map = new HashMap();
            for (Map.Entry<String, String> entry : rVar.j.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
            this.j = map;
        }
        if (rVar.u()) {
            HashMap map2 = new HashMap();
            for (Map.Entry<String, String> entry2 : rVar.k.entrySet()) {
                map2.put(entry2.getKey(), entry2.getValue());
            }
            this.k = map2;
        }
        this.l = rVar.l;
    }

    public r a() {
        return new r(this);
    }

    public r a(int i) {
        this.f = i;
        b(true);
        return this;
    }

    public r a(String str) {
        this.a = str;
        return this;
    }

    public r a(Map<String, String> map) {
        this.j = map;
        return this;
    }

    public void a(String str, String str2) {
        if (this.j == null) {
            this.j = new HashMap();
        }
        this.j.put(str, str2);
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!e()) {
                    throw new org.apache.thrift.protocol.f("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
                }
                x();
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
                    if (bVarI.b == 10) {
                        this.b = eVar.u();
                        a(true);
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
                    if (bVarI.b == 8) {
                        this.f = eVar.t();
                        b(true);
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
                    if (bVarI.b == 8) {
                        this.h = eVar.t();
                        c(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 9:
                    if (bVarI.b == 8) {
                        this.i = eVar.t();
                        d(true);
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
                    if (bVarI.b == 13) {
                        org.apache.thrift.protocol.d dVarK2 = eVar.k();
                        this.k = new HashMap(dVarK2.c * 2);
                        for (int i2 = 0; i2 < dVarK2.c; i2++) {
                            this.k.put(eVar.w(), eVar.w());
                        }
                        eVar.l();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 12:
                    if (bVarI.b == 2) {
                        this.l = eVar.q();
                        e(true);
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

    public boolean a(r rVar) {
        if (rVar == null) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = rVar.c();
        if (((zC || zC2) && !(zC && zC2 && this.a.equals(rVar.a))) || this.b != rVar.b) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = rVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.c.equals(rVar.c))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = rVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.d.equals(rVar.d))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = rVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.e.equals(rVar.e))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = rVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.f == rVar.f)) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = rVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.g.equals(rVar.g))) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = rVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.h == rVar.h)) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = rVar.r();
        if ((zR || zR2) && !(zR && zR2 && this.i == rVar.i)) {
            return false;
        }
        boolean zT = t();
        boolean zT2 = rVar.t();
        if ((zT || zT2) && !(zT && zT2 && this.j.equals(rVar.j))) {
            return false;
        }
        boolean zU = u();
        boolean zU2 = rVar.u();
        if ((zU || zU2) && !(zU && zU2 && this.k.equals(rVar.k))) {
            return false;
        }
        boolean zW = w();
        boolean zW2 = rVar.w();
        return !(zW || zW2) || (zW && zW2 && this.l == rVar.l);
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(r rVar) {
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
        if (!getClass().equals(rVar.getClass())) {
            return getClass().getName().compareTo(rVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(c()).compareTo(Boolean.valueOf(rVar.c()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (c() && (iA12 = org.apache.thrift.b.a(this.a, rVar.a)) != 0) {
            return iA12;
        }
        int iCompareTo2 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(rVar.e()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (e() && (iA11 = org.apache.thrift.b.a(this.b, rVar.b)) != 0) {
            return iA11;
        }
        int iCompareTo3 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(rVar.g()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (g() && (iA10 = org.apache.thrift.b.a(this.c, rVar.c)) != 0) {
            return iA10;
        }
        int iCompareTo4 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(rVar.i()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (i() && (iA9 = org.apache.thrift.b.a(this.d, rVar.d)) != 0) {
            return iA9;
        }
        int iCompareTo5 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(rVar.k()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (k() && (iA8 = org.apache.thrift.b.a(this.e, rVar.e)) != 0) {
            return iA8;
        }
        int iCompareTo6 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(rVar.m()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (m() && (iA7 = org.apache.thrift.b.a(this.f, rVar.f)) != 0) {
            return iA7;
        }
        int iCompareTo7 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(rVar.n()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (n() && (iA6 = org.apache.thrift.b.a(this.g, rVar.g)) != 0) {
            return iA6;
        }
        int iCompareTo8 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(rVar.p()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (p() && (iA5 = org.apache.thrift.b.a(this.h, rVar.h)) != 0) {
            return iA5;
        }
        int iCompareTo9 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(rVar.r()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (r() && (iA4 = org.apache.thrift.b.a(this.i, rVar.i)) != 0) {
            return iA4;
        }
        int iCompareTo10 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(rVar.t()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (t() && (iA3 = org.apache.thrift.b.a(this.j, rVar.j)) != 0) {
            return iA3;
        }
        int iCompareTo11 = Boolean.valueOf(u()).compareTo(Boolean.valueOf(rVar.u()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (u() && (iA2 = org.apache.thrift.b.a(this.k, rVar.k)) != 0) {
            return iA2;
        }
        int iCompareTo12 = Boolean.valueOf(w()).compareTo(Boolean.valueOf(rVar.w()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (!w() || (iA = org.apache.thrift.b.a(this.l, rVar.l)) == 0) {
            return 0;
        }
        return iA;
    }

    public r b(int i) {
        this.h = i;
        c(true);
        return this;
    }

    public r b(String str) {
        this.c = str;
        return this;
    }

    public String b() {
        return this.a;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) {
        x();
        eVar.a(n);
        if (this.a != null) {
            eVar.a(o);
            eVar.a(this.a);
            eVar.b();
        }
        eVar.a(p);
        eVar.a(this.b);
        eVar.b();
        if (this.c != null && g()) {
            eVar.a(q);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null && i()) {
            eVar.a(r);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null && k()) {
            eVar.a(s);
            eVar.a(this.e);
            eVar.b();
        }
        if (m()) {
            eVar.a(t);
            eVar.a(this.f);
            eVar.b();
        }
        if (this.g != null && n()) {
            eVar.a(u);
            eVar.a(this.g);
            eVar.b();
        }
        if (p()) {
            eVar.a(v);
            eVar.a(this.h);
            eVar.b();
        }
        if (r()) {
            eVar.a(w);
            eVar.a(this.i);
            eVar.b();
        }
        if (this.j != null && t()) {
            eVar.a(x);
            eVar.a(new org.apache.thrift.protocol.d((byte) 11, (byte) 11, this.j.size()));
            for (Map.Entry<String, String> entry : this.j.entrySet()) {
                eVar.a(entry.getKey());
                eVar.a(entry.getValue());
            }
            eVar.d();
            eVar.b();
        }
        if (this.k != null && u()) {
            eVar.a(y);
            eVar.a(new org.apache.thrift.protocol.d((byte) 11, (byte) 11, this.k.size()));
            for (Map.Entry<String, String> entry2 : this.k.entrySet()) {
                eVar.a(entry2.getKey());
                eVar.a(entry2.getValue());
            }
            eVar.d();
            eVar.b();
        }
        if (w()) {
            eVar.a(z);
            eVar.a(this.l);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z2) {
        this.A.set(1, z2);
    }

    public r c(int i) {
        this.i = i;
        d(true);
        return this;
    }

    public r c(String str) {
        this.d = str;
        return this;
    }

    public void c(boolean z2) {
        this.A.set(2, z2);
    }

    public boolean c() {
        return this.a != null;
    }

    public long d() {
        return this.b;
    }

    public r d(String str) {
        this.e = str;
        return this;
    }

    public void d(boolean z2) {
        this.A.set(3, z2);
    }

    public void e(boolean z2) {
        this.A.set(4, z2);
    }

    public boolean e() {
        return this.A.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof r)) {
            return a((r) obj);
        }
        return false;
    }

    public String f() {
        return this.c;
    }

    public boolean g() {
        return this.c != null;
    }

    public String h() {
        return this.d;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.d != null;
    }

    public String j() {
        return this.e;
    }

    public boolean k() {
        return this.e != null;
    }

    public int l() {
        return this.f;
    }

    public boolean m() {
        return this.A.get(1);
    }

    public boolean n() {
        return this.g != null;
    }

    public int o() {
        return this.h;
    }

    public boolean p() {
        return this.A.get(2);
    }

    public int q() {
        return this.i;
    }

    public boolean r() {
        return this.A.get(3);
    }

    public Map<String, String> s() {
        return this.j;
    }

    public boolean t() {
        return this.j != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PushMetaInfo(");
        sb.append("id:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.b);
        if (g()) {
            sb.append(", ");
            sb.append("topic:");
            if (this.c == null) {
                sb.append("null");
            } else {
                sb.append(this.c);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("title:");
            if (this.d == null) {
                sb.append("null");
            } else {
                sb.append(this.d);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("description:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("notifyType:");
            sb.append(this.f);
        }
        if (n()) {
            sb.append(", ");
            sb.append("url:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (p()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.h);
        }
        if (r()) {
            sb.append(", ");
            sb.append("notifyId:");
            sb.append(this.i);
        }
        if (t()) {
            sb.append(", ");
            sb.append("extra:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (u()) {
            sb.append(", ");
            sb.append("internal:");
            if (this.k == null) {
                sb.append("null");
            } else {
                sb.append(this.k);
            }
        }
        if (w()) {
            sb.append(", ");
            sb.append("ignoreRegInfo:");
            sb.append(this.l);
        }
        sb.append(")");
        return sb.toString();
    }

    public boolean u() {
        return this.k != null;
    }

    public boolean v() {
        return this.l;
    }

    public boolean w() {
        return this.A.get(4);
    }

    public void x() throws org.apache.thrift.protocol.f {
        if (this.a == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
    }
}
