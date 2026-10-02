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
public class c implements Serializable, Cloneable, org.apache.thrift.a<c, a> {
    public static final Map<a, b> b;
    private static final org.apache.thrift.protocol.j c = new org.apache.thrift.protocol.j("ClientUploadData");
    private static final org.apache.thrift.protocol.b d = new org.apache.thrift.protocol.b("uploadDataItems", (byte) 15, 1);
    public List<d> a;

    public enum a {
        UPLOAD_DATA_ITEMS(1, "uploadDataItems");

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
        enumMap.put(a.UPLOAD_DATA_ITEMS, new b("uploadDataItems", (byte) 1, new org.apache.thrift.meta_data.d((byte) 15, new org.apache.thrift.meta_data.g((byte) 12, d.class))));
        b = Collections.unmodifiableMap(enumMap);
        b.a(c.class, b);
    }

    public int a() {
        if (this.a == null) {
            return 0;
        }
        return this.a.size();
    }

    public void a(d dVar) {
        if (this.a == null) {
            this.a = new ArrayList();
        }
        this.a.add(dVar);
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
                            d dVar = new d();
                            dVar.a(eVar);
                            this.a.add(dVar);
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

    public boolean a(c cVar) {
        if (cVar == null) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = cVar.b();
        return !(zB || zB2) || (zB && zB2 && this.a.equals(cVar.a));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(c cVar) {
        int iA;
        if (!getClass().equals(cVar.getClass())) {
            return getClass().getName().compareTo(cVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(cVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (!b() || (iA = org.apache.thrift.b.a(this.a, cVar.a)) == 0) {
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
            Iterator<d> it = this.a.iterator();
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
            throw new org.apache.thrift.protocol.f("Required field 'uploadDataItems' was not present! Struct: " + toString());
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
        StringBuilder sb = new StringBuilder("ClientUploadData(");
        sb.append("uploadDataItems:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        sb.append(")");
        return sb.toString();
    }
}
