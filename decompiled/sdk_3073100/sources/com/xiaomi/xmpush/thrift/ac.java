package com.xiaomi.xmpush.thrift;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.thrift.meta_data.b;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ac implements Serializable, Cloneable, org.apache.thrift.a<ac, a> {
    public static final Map<a, b> b;
    private static final org.apache.thrift.protocol.j c = new org.apache.thrift.protocol.j("XmPushActionCustomConfig");
    private static final org.apache.thrift.protocol.b d = new org.apache.thrift.protocol.b("customConfigs", (byte) 15, 1);
    public List<p> a;

    public enum a {
        CUSTOM_CONFIGS(1, "customConfigs");

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
        enumMap.put(a.CUSTOM_CONFIGS, new b("customConfigs", (byte) 1, new org.apache.thrift.meta_data.d((byte) 15, new org.apache.thrift.meta_data.g((byte) 12, p.class))));
        b = Collections.unmodifiableMap(enumMap);
        b.a(ac.class, b);
    }

    public List<p> a() {
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
                    if (bVarI.b == 15) {
                        org.apache.thrift.protocol.c cVarM = eVar.m();
                        this.a = new ArrayList(cVarM.b);
                        for (int i = 0; i < cVarM.b; i++) {
                            p pVar = new p();
                            pVar.a(eVar);
                            this.a.add(pVar);
                        }
                        eVar.n();
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

    public boolean a(ac acVar) {
        if (acVar == null) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = acVar.b();
        return !(zB || zB2) || (zB && zB2 && this.a.equals(acVar.a));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(ac acVar) {
        int iA;
        if (!getClass().equals(acVar.getClass())) {
            return getClass().getName().compareTo(acVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(acVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!b() || (iA = org.apache.thrift.b.a(this.a, acVar.a)) == 0) {
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
            eVar.a(new org.apache.thrift.protocol.c((byte) 12, this.a.size()));
            Iterator<p> it = this.a.iterator();
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
        return this.a != null;
    }

    public void c() throws org.apache.thrift.protocol.f {
        if (this.a == null) {
            throw new org.apache.thrift.protocol.f("Required field 'customConfigs' was not present! Struct: " + toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ac)) {
            return a((ac) obj);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionCustomConfig(");
        sb.append("customConfigs:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
