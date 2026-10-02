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
public class d implements Serializable, Cloneable, org.apache.thrift.a<d, a> {
    public static final Map<a, b> h;
    private static final org.apache.thrift.protocol.j i = new org.apache.thrift.protocol.j("ClientUploadDataItem");
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("channel", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("data", (byte) 11, 2);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("name", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("counter", (byte) 10, 4);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("timestamp", (byte) 10, 5);
    private static final org.apache.thrift.protocol.b o = new org.apache.thrift.protocol.b("fromSdk", (byte) 2, 6);
    private static final org.apache.thrift.protocol.b p = new org.apache.thrift.protocol.b("category", (byte) 11, 7);
    public String a;
    public String b;
    public String c;
    public long d;
    public long e;
    public boolean f;
    public String g;
    private BitSet q = new BitSet(3);

    public enum a {
        CHANNEL(1, "channel"),
        DATA(2, "data"),
        NAME(3, "name"),
        COUNTER(4, "counter"),
        TIMESTAMP(5, "timestamp"),
        FROM_SDK(6, "fromSdk"),
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
        enumMap.put(a.CHANNEL, new b("channel", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.DATA, new b("data", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.NAME, new b("name", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.COUNTER, new b("counter", (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.TIMESTAMP, new b("timestamp", (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.FROM_SDK, new b("fromSdk", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        h = Collections.unmodifiableMap(enumMap);
        b.a(d.class, h);
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
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.b = eVar.w();
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
                    if (bVarI.b != 10) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.d = eVar.u();
                        a(true);
                    }
                    break;
                case 5:
                    if (bVarI.b != 10) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.e = eVar.u();
                        b(true);
                    }
                    break;
                case 6:
                    if (bVarI.b != 2) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.f = eVar.q();
                        c(true);
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

    public void a(boolean z) {
        this.q.set(0, z);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(d dVar) {
        if (dVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = dVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(dVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = dVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.equals(dVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = dVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(dVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = dVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d == dVar.d)) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = dVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.e == dVar.e)) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = dVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f == dVar.f)) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = dVar.g();
        return !(zG || zG2) || (zG && zG2 && this.g.equals(dVar.g));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(d dVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        if (!getClass().equals(dVar.getClass())) {
            return getClass().getName().compareTo(dVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(dVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA7 = org.apache.thrift.b.a(this.a, dVar.a)) != 0) {
            return iA7;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(dVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA6 = org.apache.thrift.b.a(this.b, dVar.b)) != 0) {
            return iA6;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(dVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA5 = org.apache.thrift.b.a(this.c, dVar.c)) != 0) {
            return iA5;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(dVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA4 = org.apache.thrift.b.a(this.d, dVar.d)) != 0) {
            return iA4;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(dVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA3 = org.apache.thrift.b.a(this.e, dVar.e)) != 0) {
            return iA3;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(dVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA2 = org.apache.thrift.b.a(this.f, dVar.f)) != 0) {
            return iA2;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(dVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (!g() || (iA = org.apache.thrift.b.a(this.g, dVar.g)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) {
        h();
        eVar.a(i);
        if (this.a != null && a()) {
            eVar.a(j);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(k);
            eVar.a(this.b);
            eVar.b();
        }
        if (this.c != null && c()) {
            eVar.a(l);
            eVar.a(this.c);
            eVar.b();
        }
        if (d()) {
            eVar.a(m);
            eVar.a(this.d);
            eVar.b();
        }
        if (e()) {
            eVar.a(n);
            eVar.a(this.e);
            eVar.b();
        }
        if (f()) {
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

    public void b(boolean z) {
        this.q.set(1, z);
    }

    public boolean b() {
        return this.b != null;
    }

    public void c(boolean z) {
        this.q.set(2, z);
    }

    public boolean c() {
        return this.c != null;
    }

    public boolean d() {
        return this.q.get(0);
    }

    public boolean e() {
        return this.q.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof d)) {
            return a((d) obj);
        }
        return false;
    }

    public boolean f() {
        return this.q.get(2);
    }

    public boolean g() {
        return this.g != null;
    }

    public void h() {
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("ClientUploadDataItem(");
        boolean z2 = true;
        if (a()) {
            sb.append("channel:");
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
            sb.append("data:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
            z2 = false;
        }
        if (c()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("name:");
            if (this.c == null) {
                sb.append("null");
            } else {
                sb.append(this.c);
            }
            z2 = false;
        }
        if (d()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("counter:");
            sb.append(this.d);
            z2 = false;
        }
        if (e()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("timestamp:");
            sb.append(this.e);
            z2 = false;
        }
        if (f()) {
            if (!z2) {
                sb.append(", ");
            }
            sb.append("fromSdk:");
            sb.append(this.f);
        } else {
            z = z2;
        }
        if (g()) {
            if (!z) {
                sb.append(", ");
            }
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
