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
public class l implements Serializable, Cloneable, org.apache.thrift.a<l, a> {
    public static final Map<a, b> c;
    private static final org.apache.thrift.protocol.j d = new org.apache.thrift.protocol.j("Location");
    private static final org.apache.thrift.protocol.b e = new org.apache.thrift.protocol.b("longitude", (byte) 4, 1);
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("latitude", (byte) 4, 2);
    public double a;
    public double b;
    private BitSet g = new BitSet(2);

    public enum a {
        LONGITUDE(1, "longitude"),
        LATITUDE(2, "latitude");

        private static final Map<String, a> c = new HashMap();
        private final short d;
        private final String e;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                c.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.d = s;
            this.e = str;
        }

        public String a() {
            return this.e;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.LONGITUDE, new b("longitude", (byte) 1, new org.apache.thrift.meta_data.c((byte) 4)));
        enumMap.put(a.LATITUDE, new b("latitude", (byte) 1, new org.apache.thrift.meta_data.c((byte) 4)));
        c = Collections.unmodifiableMap(enumMap);
        b.a(l.class, c);
    }

    public double a() {
        return this.a;
    }

    public l a(double d2) {
        this.a = d2;
        a(true);
        return this;
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!b()) {
                    throw new org.apache.thrift.protocol.f("Required field 'longitude' was not found in serialized data! Struct: " + toString());
                }
                if (!d()) {
                    throw new org.apache.thrift.protocol.f("Required field 'latitude' was not found in serialized data! Struct: " + toString());
                }
                e();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b != 4) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.a = eVar.v();
                        a(true);
                    }
                    break;
                case 2:
                    if (bVarI.b != 4) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.b = eVar.v();
                        b(true);
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
        this.g.set(0, z);
    }

    public boolean a(l lVar) {
        return lVar != null && this.a == lVar.a && this.b == lVar.b;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(l lVar) {
        int iA;
        int iA2;
        if (!getClass().equals(lVar.getClass())) {
            return getClass().getName().compareTo(lVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(lVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (b() && (iA2 = org.apache.thrift.b.a(this.a, lVar.a)) != 0) {
            return iA2;
        }
        int iCompareTo2 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(lVar.d()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (!d() || (iA = org.apache.thrift.b.a(this.b, lVar.b)) == 0) {
            return 0;
        }
        return iA;
    }

    public l b(double d2) {
        this.b = d2;
        b(true);
        return this;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) {
        e();
        eVar.a(d);
        eVar.a(e);
        eVar.a(this.a);
        eVar.b();
        eVar.a(f);
        eVar.a(this.b);
        eVar.b();
        eVar.c();
        eVar.a();
    }

    public void b(boolean z) {
        this.g.set(1, z);
    }

    public boolean b() {
        return this.g.get(0);
    }

    public double c() {
        return this.b;
    }

    public boolean d() {
        return this.g.get(1);
    }

    public void e() {
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof l)) {
            return a((l) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "Location(longitude:" + this.a + ", latitude:" + this.b + ")";
    }
}
