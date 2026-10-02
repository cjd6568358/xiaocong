package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.meta_data.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ah implements Serializable, Cloneable, org.apache.thrift.a<ah, a> {
    public static final Map<a, b> g;
    private static final org.apache.thrift.protocol.j h = new org.apache.thrift.protocol.j("XmPushActionSendFeedback");
    private static final org.apache.thrift.protocol.b i = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b m = new org.apache.thrift.protocol.b("feedbacks", (byte) 13, 5);
    private static final org.apache.thrift.protocol.b n = new org.apache.thrift.protocol.b("category", (byte) 11, 6);
    public String a;
    public u b;
    public String c;
    public String d;
    public Map<String, String> e;
    public String f;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        FEEDBACKS(5, "feedbacks"),
        CATEGORY(6, "category");

        private static final Map<String, a> g = new HashMap();
        private final short h;
        private final String i;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                g.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.h = s;
            this.i = str;
        }

        public String a() {
            return this.i;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new b("target", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.FEEDBACKS, new b("feedbacks", (byte) 2, new org.apache.thrift.meta_data.e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        g = Collections.unmodifiableMap(enumMap);
        b.a(ah.class, g);
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                g();
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
                    if (bVarI.b == 13) {
                        org.apache.thrift.protocol.d dVarK = eVar.k();
                        this.e = new HashMap(dVarK.c * 2);
                        for (int i2 = 0; i2 < dVarK.c; i2++) {
                            this.e.put(eVar.w(), eVar.w());
                        }
                        eVar.l();
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

    public boolean a(ah ahVar) {
        if (ahVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = ahVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(ahVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = ahVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(ahVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = ahVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(ahVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = ahVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(ahVar.d))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = ahVar.e();
        if ((zE || zE2) && !(zE && zE2 && this.e.equals(ahVar.e))) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = ahVar.f();
        return !(zF || zF2) || (zF && zF2 && this.f.equals(ahVar.f));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(ah ahVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        if (!getClass().equals(ahVar.getClass())) {
            return getClass().getName().compareTo(ahVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(ahVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA6 = org.apache.thrift.b.a(this.a, ahVar.a)) != 0) {
            return iA6;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(ahVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA5 = org.apache.thrift.b.a(this.b, ahVar.b)) != 0) {
            return iA5;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(ahVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA4 = org.apache.thrift.b.a(this.c, ahVar.c)) != 0) {
            return iA4;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(ahVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA3 = org.apache.thrift.b.a(this.d, ahVar.d)) != 0) {
            return iA3;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(ahVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA2 = org.apache.thrift.b.a(this.e, ahVar.e)) != 0) {
            return iA2;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(ahVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (!f() || (iA = org.apache.thrift.b.a(this.f, ahVar.f)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        g();
        eVar.a(h);
        if (this.a != null && a()) {
            eVar.a(i);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(j);
            this.b.b(eVar);
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(k);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null) {
            eVar.a(l);
            eVar.a(this.d);
            eVar.b();
        }
        if (this.e != null && e()) {
            eVar.a(m);
            eVar.a(new org.apache.thrift.protocol.d((byte) 11, (byte) 11, this.e.size()));
            for (Map.Entry<String, String> entry : this.e.entrySet()) {
                eVar.a(entry.getKey());
                eVar.a(entry.getValue());
            }
            eVar.d();
            eVar.b();
        }
        if (this.f != null && f()) {
            eVar.a(n);
            eVar.a(this.f);
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

    public boolean e() {
        return this.e != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ah)) {
            return a((ah) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f != null;
    }

    public void g() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'appId' was not present! Struct: " + toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        boolean z = false;
        StringBuilder sb = new StringBuilder("XmPushActionSendFeedback(");
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
            sb.append("feedbacks:");
            if (this.e == null) {
                sb.append("null");
            } else {
                sb.append(this.e);
            }
        }
        if (f()) {
            sb.append(", ");
            sb.append("category:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
