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
public class u implements Serializable, Cloneable, org.apache.thrift.a<u, a> {
    public static final Map<a, b> f;
    private static final org.apache.thrift.protocol.j g = new org.apache.thrift.protocol.j("Target");
    private static final org.apache.thrift.protocol.b h = new org.apache.thrift.protocol.b("channelId", (byte) 10, 1);
    private static final org.apache.thrift.protocol.b i = new org.apache.thrift.protocol.b("userId", (byte) 11, 2);
    private static final org.apache.thrift.protocol.b j = new org.apache.thrift.protocol.b("server", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b k = new org.apache.thrift.protocol.b("resource", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b l = new org.apache.thrift.protocol.b("isPreview", (byte) 2, 5);
    public String b;
    private BitSet m = new BitSet(2);
    public long a = 5;
    public String c = "xiaomi.com";
    public String d = "";
    public boolean e = false;

    public enum a {
        CHANNEL_ID(1, "channelId"),
        USER_ID(2, "userId"),
        SERVER(3, "server"),
        RESOURCE(4, "resource"),
        IS_PREVIEW(5, "isPreview");

        private static final Map<String, a> f = new HashMap();
        private final short g;
        private final String h;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                f.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.g = s;
            this.h = str;
        }

        public String a() {
            return this.h;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.CHANNEL_ID, new b("channelId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.USER_ID, new b("userId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.SERVER, new b("server", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.RESOURCE, new b("resource", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.IS_PREVIEW, new b("isPreview", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        f = Collections.unmodifiableMap(enumMap);
        b.a(u.class, f);
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!a()) {
                    throw new org.apache.thrift.protocol.f("Required field 'channelId' was not found in serialized data! Struct: " + toString());
                }
                f();
                return;
            }
            switch (bVarI.c) {
                case 1:
                    if (bVarI.b != 10) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.a = eVar.u();
                        a(true);
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
                    if (bVarI.b != 11) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.d = eVar.w();
                    }
                    break;
                case 5:
                    if (bVarI.b != 2) {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    } else {
                        this.e = eVar.q();
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
        this.m.set(0, z);
    }

    public boolean a() {
        return this.m.get(0);
    }

    public boolean a(u uVar) {
        if (uVar == null || this.a != uVar.a) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = uVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.equals(uVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = uVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(uVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = uVar.d();
        if ((zD || zD2) && !(zD && zD2 && this.d.equals(uVar.d))) {
            return false;
        }
        boolean zE = e();
        boolean zE2 = uVar.e();
        return !(zE || zE2) || (zE && zE2 && this.e == uVar.e);
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(u uVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        if (!getClass().equals(uVar.getClass())) {
            return getClass().getName().compareTo(uVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(uVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA5 = org.apache.thrift.b.a(this.a, uVar.a)) != 0) {
            return iA5;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(uVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA4 = org.apache.thrift.b.a(this.b, uVar.b)) != 0) {
            return iA4;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(uVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA3 = org.apache.thrift.b.a(this.c, uVar.c)) != 0) {
            return iA3;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(uVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA2 = org.apache.thrift.b.a(this.d, uVar.d)) != 0) {
            return iA2;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(uVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (!e() || (iA = org.apache.thrift.b.a(this.e, uVar.e)) == 0) {
            return 0;
        }
        return iA;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) {
        f();
        eVar.a(g);
        eVar.a(h);
        eVar.a(this.a);
        eVar.b();
        if (this.b != null) {
            eVar.a(i);
            eVar.a(this.b);
            eVar.b();
        }
        if (this.c != null && c()) {
            eVar.a(j);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null && d()) {
            eVar.a(k);
            eVar.a(this.d);
            eVar.b();
        }
        if (e()) {
            eVar.a(l);
            eVar.a(this.e);
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z) {
        this.m.set(1, z);
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
        return this.m.get(1);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof u)) {
            return a((u) obj);
        }
        return false;
    }

    public void f() throws org.apache.thrift.protocol.f {
        if (this.b == null) {
            throw new org.apache.thrift.protocol.f("Required field 'userId' was not present! Struct: " + toString());
        }
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Target(");
        sb.append("channelId:");
        sb.append(this.a);
        sb.append(", ");
        sb.append("userId:");
        if (this.b == null) {
            sb.append("null");
        } else {
            sb.append(this.b);
        }
        if (c()) {
            sb.append(", ");
            sb.append("server:");
            if (this.c == null) {
                sb.append("null");
            } else {
                sb.append(this.c);
            }
        }
        if (d()) {
            sb.append(", ");
            sb.append("resource:");
            if (this.d == null) {
                sb.append("null");
            } else {
                sb.append(this.d);
            }
        }
        if (e()) {
            sb.append(", ");
            sb.append("isPreview:");
            sb.append(this.e);
        }
        sb.append(")");
        return sb.toString();
    }
}
