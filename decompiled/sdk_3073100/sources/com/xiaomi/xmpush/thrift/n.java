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
public class n implements Serializable, Cloneable, org.apache.thrift.a<n, a> {
    public static final Map<a, b> d;
    private static final org.apache.thrift.protocol.j e = new org.apache.thrift.protocol.j("NormalConfig");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("version", (byte) 8, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("configItems", (byte) 15, 2);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("type", (byte) 8, 3);
    public int a;
    public List<p> b;
    public f c;
    private BitSet i = new BitSet(1);

    public enum a {
        VERSION(1, "version"),
        CONFIG_ITEMS(2, "configItems"),
        TYPE(3, "type");

        private static final Map<String, a> d = new HashMap();
        private final short e;
        private final String f;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                d.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.e = s;
            this.f = str;
        }

        public String a() {
            return this.f;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.VERSION, new b("version", (byte) 1, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.CONFIG_ITEMS, new b("configItems", (byte) 1, new org.apache.thrift.meta_data.d((byte) 15, new org.apache.thrift.meta_data.g((byte) 12, p.class))));
        enumMap.put(a.TYPE, new b("type", (byte) 1, new org.apache.thrift.meta_data.a((byte) 16, f.class)));
        d = Collections.unmodifiableMap(enumMap);
        b.a(n.class, d);
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
                if (!b()) {
                    throw new org.apache.thrift.protocol.f("Required field 'version' was not found in serialized data! Struct: " + toString());
                }
                f();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b == 8) {
                        this.a = eVar.t();
                        a(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 2:
                    if (bVarI.b == 15) {
                        org.apache.thrift.protocol.c cVarM = eVar.m();
                        this.b = new ArrayList(cVarM.b);
                        for (int i = 0; i < cVarM.b; i++) {
                            p pVar = new p();
                            pVar.a(eVar);
                            this.b.add(pVar);
                        }
                        eVar.n();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 3:
                    if (bVarI.b == 8) {
                        this.c = f.a(eVar.t());
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

    public void a(boolean z) {
        this.i.set(0, z);
    }

    public boolean a(n nVar) {
        if (nVar == null || this.a != nVar.a) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = nVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.b.equals(nVar.b))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = nVar.e();
        return !(zE || zE2) || (zE && zE2 && this.c.equals(nVar.c));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(n nVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(nVar.getClass())) {
            return getClass().getName().compareTo(nVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(nVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (b() && (iA3 = org.apache.thrift.b.a(this.a, nVar.a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(nVar.c()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (c() && (iA2 = org.apache.thrift.b.a(this.b, nVar.b)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(nVar.e()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!e() || (iA = org.apache.thrift.b.a(this.c, nVar.c)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        f();
        eVar.a(e);
        eVar.a(f);
        eVar.a(this.a);
        eVar.b();
        if (this.b != null) {
            eVar.a(g);
            eVar.a(new org.apache.thrift.protocol.c((byte) 12, this.b.size()));
            Iterator<p> it = this.b.iterator();
            while (it.hasNext()) {
                it.next().b(eVar);
            }
            eVar.e();
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(h);
            eVar.a(this.c.a());
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public boolean b() {
        return this.i.get(0);
    }

    public boolean c() {
        return this.b != null;
    }

    public f d() {
        return this.c;
    }

    public boolean e() {
        return this.c != null;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof n)) {
            return a((n) obj);
        }
        return false;
    }

    public void f() throws org.apache.thrift.protocol.f {
        if (this.b == null) {
            throw new org.apache.thrift.protocol.f("Required field 'configItems' was not present! Struct: " + toString());
        }
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'type' was not present! Struct: " + toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("NormalConfig(");
        sb.append("version:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("configItems:");
        if (this.b == null) {
            sb.append("null");
        } else {
            sb.append(this.b);
        }
        sb.append(", ");
        sb.append("type:");
        if (this.c == null) {
            sb.append("null");
        } else {
            sb.append(this.c);
        }
        sb.append(")");
        return sb.toString();
    }
}
