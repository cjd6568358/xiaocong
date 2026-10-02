package com.xiaomi.push.thrift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.thrift.f;
import org.apache.thrift.meta_data.d;
import org.apache.thrift.meta_data.g;
import org.apache.thrift.protocol.e;
import org.apache.thrift.protocol.h;
import org.apache.thrift.protocol.j;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class c implements Serializable, Cloneable, org.apache.thrift.a<c, a> {
    public static final Map<a, org.apache.thrift.meta_data.b> d;
    private static final j e = new j("StatsEvents");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("uuid", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("operator", (byte) 11, 2);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("events", (byte) 15, 3);
    public String a;
    public String b;
    public List<b> c;

    public enum a {
        UUID(1, "uuid"),
        OPERATOR(2, "operator"),
        EVENTS(3, "events");

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
        enumMap.put(a.UUID, new org.apache.thrift.meta_data.b("uuid", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.OPERATOR, new org.apache.thrift.meta_data.b("operator", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.EVENTS, new org.apache.thrift.meta_data.b("events", (byte) 1, new d((byte) 15, new g((byte) 12, b.class))));
        d = Collections.unmodifiableMap(enumMap);
        org.apache.thrift.meta_data.b.a(c.class, d);
    }

    public c() {
    }

    public c(String str, List<b> list) {
        this();
        this.a = str;
        this.c = list;
    }

    public c a(String str) {
        this.b = str;
        return this;
    }

    @Override // org.apache.thrift.a
    public void a(e eVar) throws f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                d();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b == 11) {
                        this.a = eVar.w();
                    } else {
                        h.a(eVar, bVarI.b);
                    }
                    break;
                case 2:
                    if (bVarI.b == 11) {
                        this.b = eVar.w();
                    } else {
                        h.a(eVar, bVarI.b);
                    }
                    break;
                case 3:
                    if (bVarI.b == 15) {
                        org.apache.thrift.protocol.c cVarM = eVar.m();
                        this.c = new ArrayList(cVarM.b);
                        for (int i = 0; i < cVarM.b; i++) {
                            b bVar = new b();
                            bVar.a(eVar);
                            this.c.add(bVar);
                        }
                        eVar.n();
                    } else {
                        h.a(eVar, bVarI.b);
                    }
                    break;
                default:
                    h.a(eVar, bVarI.b);
                    break;
            }
            eVar.j();
        }
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(c cVar) {
        if (cVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = cVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(cVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = cVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.equals(cVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = cVar.c();
        return !(zC || zC2) || (zC && zC2 && this.c.equals(cVar.c));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(c cVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(cVar.getClass())) {
            return getClass().getName().compareTo(cVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(cVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA3 = org.apache.thrift.b.a(this.a, cVar.a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(cVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA2 = org.apache.thrift.b.a(this.b, cVar.b)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(cVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!c() || (iA = org.apache.thrift.b.a(this.c, cVar.c)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(e eVar) {
        d();
        eVar.a(e);
        if (this.a != null) {
            eVar.a(f);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(g);
            eVar.a(this.b);
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(h);
            eVar.a(new org.apache.thrift.protocol.c((byte) 12, this.c.size()));
            Iterator<b> it = this.c.iterator();
            while (it.hasNext()) {
                it.next().b(eVar);
            }
            eVar.e();
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

    public void d() throws org.apache.thrift.protocol.f {
        if (this.a == null) {
            throw new org.apache.thrift.protocol.f("Required field 'uuid' was not present! Struct: " + toString());
        }
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'events' was not present! Struct: " + toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof c)) {
            return a((c) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("StatsEvents(");
        sb.append("uuid:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        if (b()) {
            sb.append(", ");
            sb.append("operator:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
        }
        sb.append(", ");
        sb.append("events:");
        if (this.c == null) {
            sb.append("null");
        } else {
            sb.append(this.c);
        }
        sb.append(")");
        return sb.toString();
    }
}
