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
public class ad implements Serializable, Cloneable, org.apache.thrift.a<ad, a> {
    public static final Map<a, b> d;
    private static final org.apache.thrift.protocol.j e = new org.apache.thrift.protocol.j("XmPushActionNormalConfig");
    private static final org.apache.thrift.protocol.b f = new org.apache.thrift.protocol.b("normalConfigs", (byte) 15, 1);
    private static final org.apache.thrift.protocol.b g = new org.apache.thrift.protocol.b("appId", (byte) 10, 4);
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("packageName", (byte) 11, 5);
    public List<n> a;
    public long b;
    public String c;
    private BitSet i = new BitSet(1);

    public enum a {
        NORMAL_CONFIGS(1, "normalConfigs"),
        APP_ID(4, "appId"),
        PACKAGE_NAME(5, "packageName");

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
        enumMap.put(a.NORMAL_CONFIGS, new b("normalConfigs", (byte) 1, new org.apache.thrift.meta_data.d((byte) 15, new org.apache.thrift.meta_data.g((byte) 12, n.class))));
        enumMap.put(a.APP_ID, new b("appId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        d = Collections.unmodifiableMap(enumMap);
        b.a(ad.class, d);
    }

    public List<n> a() {
        return this.a;
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                e();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b == 15) {
                        org.apache.thrift.protocol.c cVarM = eVar.m();
                        this.a = new ArrayList(cVarM.b);
                        for (int i = 0; i < cVarM.b; i++) {
                            n nVar = new n();
                            nVar.a(eVar);
                            this.a.add(nVar);
                        }
                        eVar.n();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 2:
                case 3:
                default:
                    org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    break;
                case 4:
                    if (bVarI.b == 10) {
                        this.b = eVar.u();
                        a(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 5:
                    if (bVarI.b == 11) {
                        this.c = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
            }
            eVar.j();
        }
    }

    public void a(boolean z) {
        this.i.set(0, z);
    }

    public boolean a(ad adVar) {
        if (adVar == null) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = adVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.a.equals(adVar.a))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = adVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.b == adVar.b)) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = adVar.d();
        return !(zD || zD2) || (zD && zD2 && this.c.equals(adVar.c));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(ad adVar) {
        int iA;
        int iA2;
        int iA3;
        if (!getClass().equals(adVar.getClass())) {
            return getClass().getName().compareTo(adVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(b()).compareTo(Boolean.valueOf(adVar.b()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (b() && (iA3 = org.apache.thrift.b.a(this.a, adVar.a)) != 0) {
            return iA3;
        }
        int iCompareTo2 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(adVar.c()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (c() && (iA2 = org.apache.thrift.b.a(this.b, adVar.b)) != 0) {
            return iA2;
        }
        int iCompareTo3 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(adVar.d()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (!d() || (iA = org.apache.thrift.b.a(this.c, adVar.c)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        e();
        eVar.a(e);
        if (this.a != null) {
            eVar.a(f);
            eVar.a(new org.apache.thrift.protocol.c((byte) 12, this.a.size()));
            Iterator<n> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().b(eVar);
            }
            eVar.e();
            eVar.b();
        }
        if (c()) {
            eVar.a(g);
            eVar.a(this.b);
            eVar.b();
        }
        if (this.c != null && d()) {
            eVar.a(h);
            eVar.a(this.c);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public boolean b() {
        return this.a != null;
    }

    public boolean c() {
        return this.i.get(0);
    }

    public boolean d() {
        return this.c != null;
    }

    public void e() throws org.apache.thrift.protocol.f {
        if (this.a == null) {
            throw new org.apache.thrift.protocol.f("Required field 'normalConfigs' was not present! Struct: " + toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ad)) {
            return a((ad) obj);
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("XmPushActionNormalConfig(");
        sb.append("normalConfigs:");
        if (this.a == null) {
            sb.append("null");
        } else {
            sb.append(this.a);
        }
        if (c()) {
            sb.append(", ");
            sb.append("appId:");
            sb.append(this.b);
        }
        if (d()) {
            sb.append(", ");
            sb.append("packageName:");
            if (this.c == null) {
                sb.append("null");
            } else {
                sb.append(this.c);
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
