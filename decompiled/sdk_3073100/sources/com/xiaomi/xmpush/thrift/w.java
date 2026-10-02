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
public class w implements Serializable, Cloneable, org.apache.thrift.a<w, a> {
    public static final Map<a, b> u;
    public String a;
    public u b;
    public String c;
    public String d;
    public long e;
    public String f;
    public String g;
    public aj h;
    public String i;
    public String j;
    public String l;
    public String m;
    public String n;
    public short o;
    public short p;
    public String q;
    public String r;
    public int s;
    public Map<String, String> t;
    private static final org.apache.thrift.protocol.j v = new org.apache.thrift.protocol.j("XmPushActionAckMessage");
    private static final org.apache.thrift.protocol.b w = new org.apache.thrift.protocol.b("debug", (byte) 11, 1);
    private static final org.apache.thrift.protocol.b x = new org.apache.thrift.protocol.b("target", (byte) 12, 2);
    private static final org.apache.thrift.protocol.b y = new org.apache.thrift.protocol.b("id", (byte) 11, 3);
    private static final org.apache.thrift.protocol.b z = new org.apache.thrift.protocol.b("appId", (byte) 11, 4);
    private static final org.apache.thrift.protocol.b A = new org.apache.thrift.protocol.b("messageTs", (byte) 10, 5);
    private static final org.apache.thrift.protocol.b B = new org.apache.thrift.protocol.b("topic", (byte) 11, 6);
    private static final org.apache.thrift.protocol.b C = new org.apache.thrift.protocol.b("aliasName", (byte) 11, 7);
    private static final org.apache.thrift.protocol.b D = new org.apache.thrift.protocol.b("request", (byte) 12, 8);
    private static final org.apache.thrift.protocol.b E = new org.apache.thrift.protocol.b("packageName", (byte) 11, 9);
    private static final org.apache.thrift.protocol.b F = new org.apache.thrift.protocol.b("category", (byte) 11, 10);
    private static final org.apache.thrift.protocol.b G = new org.apache.thrift.protocol.b("isOnline", (byte) 2, 11);
    private static final org.apache.thrift.protocol.b H = new org.apache.thrift.protocol.b("regId", (byte) 11, 12);
    private static final org.apache.thrift.protocol.b I = new org.apache.thrift.protocol.b("callbackUrl", (byte) 11, 13);
    private static final org.apache.thrift.protocol.b J = new org.apache.thrift.protocol.b("userAccount", (byte) 11, 14);
    private static final org.apache.thrift.protocol.b K = new org.apache.thrift.protocol.b("deviceStatus", (byte) 6, 15);
    private static final org.apache.thrift.protocol.b L = new org.apache.thrift.protocol.b("geoMsgStatus", (byte) 6, 16);
    private static final org.apache.thrift.protocol.b M = new org.apache.thrift.protocol.b("imeiMd5", (byte) 11, 20);
    private static final org.apache.thrift.protocol.b N = new org.apache.thrift.protocol.b("deviceId", (byte) 11, 21);
    private static final org.apache.thrift.protocol.b O = new org.apache.thrift.protocol.b("passThrough", (byte) 8, 22);
    private static final org.apache.thrift.protocol.b P = new org.apache.thrift.protocol.b("extra", (byte) 13, 23);
    private BitSet Q = new BitSet(5);
    public boolean k = false;

    public enum a {
        DEBUG(1, "debug"),
        TARGET(2, "target"),
        ID(3, "id"),
        APP_ID(4, "appId"),
        MESSAGE_TS(5, "messageTs"),
        TOPIC(6, "topic"),
        ALIAS_NAME(7, "aliasName"),
        REQUEST(8, "request"),
        PACKAGE_NAME(9, "packageName"),
        CATEGORY(10, "category"),
        IS_ONLINE(11, "isOnline"),
        REG_ID(12, "regId"),
        CALLBACK_URL(13, "callbackUrl"),
        USER_ACCOUNT(14, "userAccount"),
        DEVICE_STATUS(15, "deviceStatus"),
        GEO_MSG_STATUS(16, "geoMsgStatus"),
        IMEI_MD5(20, "imeiMd5"),
        DEVICE_ID(21, "deviceId"),
        PASS_THROUGH(22, "passThrough"),
        EXTRA(23, "extra");

        private static final Map<String, a> u = new HashMap();
        private final short v;
        private final String w;

        static {
            for (a aVar : EnumSet.allOf(a.class)) {
                u.put(aVar.a(), aVar);
            }
        }

        a(short s, String str) {
            this.v = s;
            this.w = str;
        }

        public String a() {
            return this.w;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(a.class);
        enumMap.put(a.DEBUG, new b("debug", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.TARGET, new b("target", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, u.class)));
        enumMap.put(a.ID, new b("id", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.APP_ID, new b("appId", (byte) 1, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.MESSAGE_TS, new b("messageTs", (byte) 1, new org.apache.thrift.meta_data.c((byte) 10)));
        enumMap.put(a.TOPIC, new b("topic", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.ALIAS_NAME, new b("aliasName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.REQUEST, new b("request", (byte) 2, new org.apache.thrift.meta_data.g((byte) 12, aj.class)));
        enumMap.put(a.PACKAGE_NAME, new b("packageName", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CATEGORY, new b("category", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.IS_ONLINE, new b("isOnline", (byte) 2, new org.apache.thrift.meta_data.c((byte) 2)));
        enumMap.put(a.REG_ID, new b("regId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.CALLBACK_URL, new b("callbackUrl", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.USER_ACCOUNT, new b("userAccount", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.DEVICE_STATUS, new b("deviceStatus", (byte) 2, new org.apache.thrift.meta_data.c((byte) 6)));
        enumMap.put(a.GEO_MSG_STATUS, new b("geoMsgStatus", (byte) 2, new org.apache.thrift.meta_data.c((byte) 6)));
        enumMap.put(a.IMEI_MD5, new b("imeiMd5", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.DEVICE_ID, new b("deviceId", (byte) 2, new org.apache.thrift.meta_data.c((byte) 11)));
        enumMap.put(a.PASS_THROUGH, new b("passThrough", (byte) 2, new org.apache.thrift.meta_data.c((byte) 8)));
        enumMap.put(a.EXTRA, new b("extra", (byte) 2, new org.apache.thrift.meta_data.e((byte) 13, new org.apache.thrift.meta_data.c((byte) 11), new org.apache.thrift.meta_data.c((byte) 11))));
        u = Collections.unmodifiableMap(enumMap);
        b.a(w.class, u);
    }

    public w a(long j) {
        this.e = j;
        a(true);
        return this;
    }

    public w a(String str) {
        this.c = str;
        return this;
    }

    public w a(short s) {
        this.o = s;
        c(true);
        return this;
    }

    @Override // org.apache.thrift.a
    public void a(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.f {
        eVar.g();
        while (true) {
            org.apache.thrift.protocol.b bVarI = eVar.i();
            if (bVarI.b == 0) {
                eVar.h();
                if (!e()) {
                    throw new org.apache.thrift.protocol.f("Required field 'messageTs' was not found in serialized data! Struct: " + toString());
                }
                u();
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
                    if (bVarI.b == 10) {
                        this.e = eVar.u();
                        a(true);
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
                case 7:
                    if (bVarI.b == 11) {
                        this.g = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 8:
                    if (bVarI.b == 12) {
                        this.h = new aj();
                        this.h.a(eVar);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 9:
                    if (bVarI.b == 11) {
                        this.i = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 10:
                    if (bVarI.b == 11) {
                        this.j = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 11:
                    if (bVarI.b == 2) {
                        this.k = eVar.q();
                        b(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 12:
                    if (bVarI.b == 11) {
                        this.l = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 13:
                    if (bVarI.b == 11) {
                        this.m = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 14:
                    if (bVarI.b == 11) {
                        this.n = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 15:
                    if (bVarI.b == 6) {
                        this.o = eVar.s();
                        c(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 16:
                    if (bVarI.b == 6) {
                        this.p = eVar.s();
                        d(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 17:
                case 18:
                case 19:
                default:
                    org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    break;
                case 20:
                    if (bVarI.b == 11) {
                        this.q = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 21:
                    if (bVarI.b == 11) {
                        this.r = eVar.w();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 22:
                    if (bVarI.b == 8) {
                        this.s = eVar.t();
                        e(true);
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
                case 23:
                    if (bVarI.b == 13) {
                        org.apache.thrift.protocol.d dVarK = eVar.k();
                        this.t = new HashMap(dVarK.c * 2);
                        for (int i = 0; i < dVarK.c; i++) {
                            this.t.put(eVar.w(), eVar.w());
                        }
                        eVar.l();
                    } else {
                        org.apache.thrift.protocol.h.a(eVar, bVarI.b);
                    }
                    break;
            }
            eVar.j();
        }
    }

    public void a(boolean z2) {
        this.Q.set(0, z2);
    }

    public boolean a() {
        return this.a != null;
    }

    public boolean a(w wVar) {
        if (wVar == null) {
            return false;
        }
        boolean zA = a();
        boolean zA2 = wVar.a();
        if ((zA || zA2) && !(zA && zA2 && this.a.equals(wVar.a))) {
            return false;
        }
        boolean zB = b();
        boolean zB2 = wVar.b();
        if ((zB || zB2) && !(zB && zB2 && this.b.a(wVar.b))) {
            return false;
        }
        boolean zC = c();
        boolean zC2 = wVar.c();
        if ((zC || zC2) && !(zC && zC2 && this.c.equals(wVar.c))) {
            return false;
        }
        boolean zD = d();
        boolean zD2 = wVar.d();
        if (((zD || zD2) && !(zD && zD2 && this.d.equals(wVar.d))) || this.e != wVar.e) {
            return false;
        }
        boolean zF = f();
        boolean zF2 = wVar.f();
        if ((zF || zF2) && !(zF && zF2 && this.f.equals(wVar.f))) {
            return false;
        }
        boolean zG = g();
        boolean zG2 = wVar.g();
        if ((zG || zG2) && !(zG && zG2 && this.g.equals(wVar.g))) {
            return false;
        }
        boolean zH = h();
        boolean zH2 = wVar.h();
        if ((zH || zH2) && !(zH && zH2 && this.h.a(wVar.h))) {
            return false;
        }
        boolean zI = i();
        boolean zI2 = wVar.i();
        if ((zI || zI2) && !(zI && zI2 && this.i.equals(wVar.i))) {
            return false;
        }
        boolean zJ = j();
        boolean zJ2 = wVar.j();
        if ((zJ || zJ2) && !(zJ && zJ2 && this.j.equals(wVar.j))) {
            return false;
        }
        boolean zK = k();
        boolean zK2 = wVar.k();
        if ((zK || zK2) && !(zK && zK2 && this.k == wVar.k)) {
            return false;
        }
        boolean zL = l();
        boolean zL2 = wVar.l();
        if ((zL || zL2) && !(zL && zL2 && this.l.equals(wVar.l))) {
            return false;
        }
        boolean zM = m();
        boolean zM2 = wVar.m();
        if ((zM || zM2) && !(zM && zM2 && this.m.equals(wVar.m))) {
            return false;
        }
        boolean zN = n();
        boolean zN2 = wVar.n();
        if ((zN || zN2) && !(zN && zN2 && this.n.equals(wVar.n))) {
            return false;
        }
        boolean zO = o();
        boolean zO2 = wVar.o();
        if ((zO || zO2) && !(zO && zO2 && this.o == wVar.o)) {
            return false;
        }
        boolean zP = p();
        boolean zP2 = wVar.p();
        if ((zP || zP2) && !(zP && zP2 && this.p == wVar.p)) {
            return false;
        }
        boolean zQ = q();
        boolean zQ2 = wVar.q();
        if ((zQ || zQ2) && !(zQ && zQ2 && this.q.equals(wVar.q))) {
            return false;
        }
        boolean zR = r();
        boolean zR2 = wVar.r();
        if ((zR || zR2) && !(zR && zR2 && this.r.equals(wVar.r))) {
            return false;
        }
        boolean zS = s();
        boolean zS2 = wVar.s();
        if ((zS || zS2) && !(zS && zS2 && this.s == wVar.s)) {
            return false;
        }
        boolean zT = t();
        boolean zT2 = wVar.t();
        return !(zT || zT2) || (zT && zT2 && this.t.equals(wVar.t));
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(w wVar) {
        int iA;
        int iA2;
        int iA3;
        int iA4;
        int iA5;
        int iA6;
        int iA7;
        int iA8;
        int iA9;
        int iA10;
        int iA11;
        int iA12;
        int iA13;
        int iA14;
        int iA15;
        int iA16;
        int iA17;
        int iA18;
        int iA19;
        int iA20;
        if (!getClass().equals(wVar.getClass())) {
            return getClass().getName().compareTo(wVar.getClass().getName());
        }
        int iCompareTo = Boolean.valueOf(a()).compareTo(Boolean.valueOf(wVar.a()));
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (a() && (iA20 = org.apache.thrift.b.a(this.a, wVar.a)) != 0) {
            return iA20;
        }
        int iCompareTo2 = Boolean.valueOf(b()).compareTo(Boolean.valueOf(wVar.b()));
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        if (b() && (iA19 = org.apache.thrift.b.a(this.b, wVar.b)) != 0) {
            return iA19;
        }
        int iCompareTo3 = Boolean.valueOf(c()).compareTo(Boolean.valueOf(wVar.c()));
        if (iCompareTo3 != 0) {
            return iCompareTo3;
        }
        if (c() && (iA18 = org.apache.thrift.b.a(this.c, wVar.c)) != 0) {
            return iA18;
        }
        int iCompareTo4 = Boolean.valueOf(d()).compareTo(Boolean.valueOf(wVar.d()));
        if (iCompareTo4 != 0) {
            return iCompareTo4;
        }
        if (d() && (iA17 = org.apache.thrift.b.a(this.d, wVar.d)) != 0) {
            return iA17;
        }
        int iCompareTo5 = Boolean.valueOf(e()).compareTo(Boolean.valueOf(wVar.e()));
        if (iCompareTo5 != 0) {
            return iCompareTo5;
        }
        if (e() && (iA16 = org.apache.thrift.b.a(this.e, wVar.e)) != 0) {
            return iA16;
        }
        int iCompareTo6 = Boolean.valueOf(f()).compareTo(Boolean.valueOf(wVar.f()));
        if (iCompareTo6 != 0) {
            return iCompareTo6;
        }
        if (f() && (iA15 = org.apache.thrift.b.a(this.f, wVar.f)) != 0) {
            return iA15;
        }
        int iCompareTo7 = Boolean.valueOf(g()).compareTo(Boolean.valueOf(wVar.g()));
        if (iCompareTo7 != 0) {
            return iCompareTo7;
        }
        if (g() && (iA14 = org.apache.thrift.b.a(this.g, wVar.g)) != 0) {
            return iA14;
        }
        int iCompareTo8 = Boolean.valueOf(h()).compareTo(Boolean.valueOf(wVar.h()));
        if (iCompareTo8 != 0) {
            return iCompareTo8;
        }
        if (h() && (iA13 = org.apache.thrift.b.a(this.h, wVar.h)) != 0) {
            return iA13;
        }
        int iCompareTo9 = Boolean.valueOf(i()).compareTo(Boolean.valueOf(wVar.i()));
        if (iCompareTo9 != 0) {
            return iCompareTo9;
        }
        if (i() && (iA12 = org.apache.thrift.b.a(this.i, wVar.i)) != 0) {
            return iA12;
        }
        int iCompareTo10 = Boolean.valueOf(j()).compareTo(Boolean.valueOf(wVar.j()));
        if (iCompareTo10 != 0) {
            return iCompareTo10;
        }
        if (j() && (iA11 = org.apache.thrift.b.a(this.j, wVar.j)) != 0) {
            return iA11;
        }
        int iCompareTo11 = Boolean.valueOf(k()).compareTo(Boolean.valueOf(wVar.k()));
        if (iCompareTo11 != 0) {
            return iCompareTo11;
        }
        if (k() && (iA10 = org.apache.thrift.b.a(this.k, wVar.k)) != 0) {
            return iA10;
        }
        int iCompareTo12 = Boolean.valueOf(l()).compareTo(Boolean.valueOf(wVar.l()));
        if (iCompareTo12 != 0) {
            return iCompareTo12;
        }
        if (l() && (iA9 = org.apache.thrift.b.a(this.l, wVar.l)) != 0) {
            return iA9;
        }
        int iCompareTo13 = Boolean.valueOf(m()).compareTo(Boolean.valueOf(wVar.m()));
        if (iCompareTo13 != 0) {
            return iCompareTo13;
        }
        if (m() && (iA8 = org.apache.thrift.b.a(this.m, wVar.m)) != 0) {
            return iA8;
        }
        int iCompareTo14 = Boolean.valueOf(n()).compareTo(Boolean.valueOf(wVar.n()));
        if (iCompareTo14 != 0) {
            return iCompareTo14;
        }
        if (n() && (iA7 = org.apache.thrift.b.a(this.n, wVar.n)) != 0) {
            return iA7;
        }
        int iCompareTo15 = Boolean.valueOf(o()).compareTo(Boolean.valueOf(wVar.o()));
        if (iCompareTo15 != 0) {
            return iCompareTo15;
        }
        if (o() && (iA6 = org.apache.thrift.b.a(this.o, wVar.o)) != 0) {
            return iA6;
        }
        int iCompareTo16 = Boolean.valueOf(p()).compareTo(Boolean.valueOf(wVar.p()));
        if (iCompareTo16 != 0) {
            return iCompareTo16;
        }
        if (p() && (iA5 = org.apache.thrift.b.a(this.p, wVar.p)) != 0) {
            return iA5;
        }
        int iCompareTo17 = Boolean.valueOf(q()).compareTo(Boolean.valueOf(wVar.q()));
        if (iCompareTo17 != 0) {
            return iCompareTo17;
        }
        if (q() && (iA4 = org.apache.thrift.b.a(this.q, wVar.q)) != 0) {
            return iA4;
        }
        int iCompareTo18 = Boolean.valueOf(r()).compareTo(Boolean.valueOf(wVar.r()));
        if (iCompareTo18 != 0) {
            return iCompareTo18;
        }
        if (r() && (iA3 = org.apache.thrift.b.a(this.r, wVar.r)) != 0) {
            return iA3;
        }
        int iCompareTo19 = Boolean.valueOf(s()).compareTo(Boolean.valueOf(wVar.s()));
        if (iCompareTo19 != 0) {
            return iCompareTo19;
        }
        if (s() && (iA2 = org.apache.thrift.b.a(this.s, wVar.s)) != 0) {
            return iA2;
        }
        int iCompareTo20 = Boolean.valueOf(t()).compareTo(Boolean.valueOf(wVar.t()));
        if (iCompareTo20 != 0) {
            return iCompareTo20;
        }
        if (!t() || (iA = org.apache.thrift.b.a(this.t, wVar.t)) == 0) {
            return 0;
        }
        return iA;
    }

    public w b(String str) {
        this.d = str;
        return this;
    }

    public w b(short s) {
        this.p = s;
        d(true);
        return this;
    }

    @Override // org.apache.thrift.a
    public void b(org.apache.thrift.protocol.e eVar) throws org.apache.thrift.protocol.f {
        u();
        eVar.a(v);
        if (this.a != null && a()) {
            eVar.a(w);
            eVar.a(this.a);
            eVar.b();
        }
        if (this.b != null && b()) {
            eVar.a(x);
            this.b.b(eVar);
            eVar.b();
        }
        if (this.c != null) {
            eVar.a(y);
            eVar.a(this.c);
            eVar.b();
        }
        if (this.d != null) {
            eVar.a(z);
            eVar.a(this.d);
            eVar.b();
        }
        eVar.a(A);
        eVar.a(this.e);
        eVar.b();
        if (this.f != null && f()) {
            eVar.a(B);
            eVar.a(this.f);
            eVar.b();
        }
        if (this.g != null && g()) {
            eVar.a(C);
            eVar.a(this.g);
            eVar.b();
        }
        if (this.h != null && h()) {
            eVar.a(D);
            this.h.b(eVar);
            eVar.b();
        }
        if (this.i != null && i()) {
            eVar.a(E);
            eVar.a(this.i);
            eVar.b();
        }
        if (this.j != null && j()) {
            eVar.a(F);
            eVar.a(this.j);
            eVar.b();
        }
        if (k()) {
            eVar.a(G);
            eVar.a(this.k);
            eVar.b();
        }
        if (this.l != null && l()) {
            eVar.a(H);
            eVar.a(this.l);
            eVar.b();
        }
        if (this.m != null && m()) {
            eVar.a(I);
            eVar.a(this.m);
            eVar.b();
        }
        if (this.n != null && n()) {
            eVar.a(J);
            eVar.a(this.n);
            eVar.b();
        }
        if (o()) {
            eVar.a(K);
            eVar.a(this.o);
            eVar.b();
        }
        if (p()) {
            eVar.a(L);
            eVar.a(this.p);
            eVar.b();
        }
        if (this.q != null && q()) {
            eVar.a(M);
            eVar.a(this.q);
            eVar.b();
        }
        if (this.r != null && r()) {
            eVar.a(N);
            eVar.a(this.r);
            eVar.b();
        }
        if (s()) {
            eVar.a(O);
            eVar.a(this.s);
            eVar.b();
        }
        if (this.t != null && t()) {
            eVar.a(P);
            eVar.a(new org.apache.thrift.protocol.d((byte) 11, (byte) 11, this.t.size()));
            for (Map.Entry<String, String> entry : this.t.entrySet()) {
                eVar.a(entry.getKey());
                eVar.a(entry.getValue());
            }
            eVar.d();
            eVar.b();
        }
        eVar.c();
        eVar.a();
    }

    public void b(boolean z2) {
        this.Q.set(1, z2);
    }

    public boolean b() {
        return this.b != null;
    }

    public w c(String str) {
        this.f = str;
        return this;
    }

    public void c(boolean z2) {
        this.Q.set(2, z2);
    }

    public boolean c() {
        return this.c != null;
    }

    public w d(String str) {
        this.g = str;
        return this;
    }

    public void d(boolean z2) {
        this.Q.set(3, z2);
    }

    public boolean d() {
        return this.d != null;
    }

    public void e(boolean z2) {
        this.Q.set(4, z2);
    }

    public boolean e() {
        return this.Q.get(0);
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof w)) {
            return a((w) obj);
        }
        return false;
    }

    public boolean f() {
        return this.f != null;
    }

    public boolean g() {
        return this.g != null;
    }

    public boolean h() {
        return this.h != null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean i() {
        return this.i != null;
    }

    public boolean j() {
        return this.j != null;
    }

    public boolean k() {
        return this.Q.get(1);
    }

    public boolean l() {
        return this.l != null;
    }

    public boolean m() {
        return this.m != null;
    }

    public boolean n() {
        return this.n != null;
    }

    public boolean o() {
        return this.Q.get(2);
    }

    public boolean p() {
        return this.Q.get(3);
    }

    public boolean q() {
        return this.q != null;
    }

    public boolean r() {
        return this.r != null;
    }

    public boolean s() {
        return this.Q.get(4);
    }

    public boolean t() {
        return this.t != null;
    }

    public String toString() {
        boolean z2 = false;
        StringBuilder sb = new StringBuilder("XmPushActionAckMessage(");
        boolean z3 = true;
        if (a()) {
            sb.append("debug:");
            if (this.a == null) {
                sb.append("null");
            } else {
                sb.append(this.a);
            }
            z3 = false;
        }
        if (b()) {
            if (!z3) {
                sb.append(", ");
            }
            sb.append("target:");
            if (this.b == null) {
                sb.append("null");
            } else {
                sb.append(this.b);
            }
        } else {
            z2 = z3;
        }
        if (!z2) {
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
        sb.append(", ");
        sb.append("messageTs:");
        sb.append(this.e);
        if (f()) {
            sb.append(", ");
            sb.append("topic:");
            if (this.f == null) {
                sb.append("null");
            } else {
                sb.append(this.f);
            }
        }
        if (g()) {
            sb.append(", ");
            sb.append("aliasName:");
            if (this.g == null) {
                sb.append("null");
            } else {
                sb.append(this.g);
            }
        }
        if (h()) {
            sb.append(", ");
            sb.append("request:");
            if (this.h == null) {
                sb.append("null");
            } else {
                sb.append(this.h);
            }
        }
        if (i()) {
            sb.append(", ");
            sb.append("packageName:");
            if (this.i == null) {
                sb.append("null");
            } else {
                sb.append(this.i);
            }
        }
        if (j()) {
            sb.append(", ");
            sb.append("category:");
            if (this.j == null) {
                sb.append("null");
            } else {
                sb.append(this.j);
            }
        }
        if (k()) {
            sb.append(", ");
            sb.append("isOnline:");
            sb.append(this.k);
        }
        if (l()) {
            sb.append(", ");
            sb.append("regId:");
            if (this.l == null) {
                sb.append("null");
            } else {
                sb.append(this.l);
            }
        }
        if (m()) {
            sb.append(", ");
            sb.append("callbackUrl:");
            if (this.m == null) {
                sb.append("null");
            } else {
                sb.append(this.m);
            }
        }
        if (n()) {
            sb.append(", ");
            sb.append("userAccount:");
            if (this.n == null) {
                sb.append("null");
            } else {
                sb.append(this.n);
            }
        }
        if (o()) {
            sb.append(", ");
            sb.append("deviceStatus:");
            sb.append((int) this.o);
        }
        if (p()) {
            sb.append(", ");
            sb.append("geoMsgStatus:");
            sb.append((int) this.p);
        }
        if (q()) {
            sb.append(", ");
            sb.append("imeiMd5:");
            if (this.q == null) {
                sb.append("null");
            } else {
                sb.append(this.q);
            }
        }
        if (r()) {
            sb.append(", ");
            sb.append("deviceId:");
            if (this.r == null) {
                sb.append("null");
            } else {
                sb.append(this.r);
            }
        }
        if (s()) {
            sb.append(", ");
            sb.append("passThrough:");
            sb.append(this.s);
        }
        if (t()) {
            sb.append(", ");
            sb.append("extra:");
            if (this.t == null) {
                sb.append("null");
            } else {
                sb.append(this.t);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public void u() throws org.apache.thrift.protocol.f {
        if (this.c == null) {
            throw new org.apache.thrift.protocol.f("Required field 'id' was not present! Struct: " + toString());
        }
        if (this.d == null) {
            throw new org.apache.thrift.protocol.f("Required field 'appId' was not present! Struct: " + toString());
        }
    }
}
