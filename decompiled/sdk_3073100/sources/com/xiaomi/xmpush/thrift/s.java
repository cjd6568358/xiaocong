package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.thrift.meta_data.b;
import org.apache.thrift.protocol.i;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class s implements Serializable, Cloneable, org.apache.thrift.a<s, a> {
    public static final Map<a, b> b;
    private static final org.apache.thrift.protocol.j c = new org.apache.thrift.protocol.j("RegisteredGeoFencing");
    private static final org.apache.thrift.protocol.b d = new org.apache.thrift.protocol.b("geoFencings", (byte) 14, 1);
    public Set<j> a;

    public enum a {
        GEO_FENCINGS(1, "geoFencings");

        private static final Map<String, a> b = new HashMap();
        private final short c;
        private final String d;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                b.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.c = s;
            this.d = str;
        }

        public String a() {
            return this.d;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.GEO_FENCINGS, new b("geoFencings", (byte) 1, new org.apache.thrift.meta_data.f((byte) 14, new org.apache.thrift.meta_data.g((byte) 12, j.class))));
        b = Collections.unmodifiableMap(enumMap);
        b.a(s.class, b);
    }

    public s a(Set<j> set) {
        this.a = set;
        return this;
    }

    public Set<j> a() {
        return this.a;
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                c();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b == 14) {
                        i iVarO = eVar.o();
                        this.a = new HashSet(iVarO.b * 2);
                        for (int i = 0; i < iVarO.b; i++) {
                            j jVar = new j();
                            jVar.a(eVar);
                            this.a.add(jVar);
                        }
                        eVar.p();
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

    public boolean a(s sVar) {
        if (sVar == null) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = sVar.b();
        return !(zB || zB2) || (zB && zB2 && this.a.equals(sVar.a));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(s sVar) {
        int iA;
        if (!getClass().equals(sVar.getClass())) {
            return getClass().getName().compareTo(sVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(sVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!b() || (iA = org.apache.thrift.b.a(this.a, sVar.a)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        c();
        eVar.a(c);
        if (this.a != null) {
            eVar.a(d);
            eVar.a(new i((byte) 12, this.a.size()));
            Iterator<j> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().b(eVar);
            }
            eVar.f();
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public boolean b() {
        return this.a != null;
    }

    public void c() throws org.apache.thrift.protocol.f {
        if (this.a == null) {
            throw new org.apache.thrift.protocol.f("Required field 'geoFencings' was not present! Struct: " + toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof s)) {
            return a((s) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("RegisteredGeoFencing(");
        sb.append("geoFencings:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
