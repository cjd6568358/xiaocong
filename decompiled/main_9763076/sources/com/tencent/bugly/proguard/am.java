package com.tencent.bugly.proguard;

import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class am extends k {
    private static byte[] y = {0};
    private static Map<String, String> z = new HashMap();
    public int a = 0;
    public String b = Constants.MAIN_VERSION_TAG;
    public String c = Constants.MAIN_VERSION_TAG;
    public String d = Constants.MAIN_VERSION_TAG;
    public String e = Constants.MAIN_VERSION_TAG;
    public String f = Constants.MAIN_VERSION_TAG;
    public int g = 0;
    public byte[] h = null;
    public String i = Constants.MAIN_VERSION_TAG;
    public String j = Constants.MAIN_VERSION_TAG;
    public Map<String, String> k = null;
    public String l = Constants.MAIN_VERSION_TAG;
    public long m = 0;
    public String n = Constants.MAIN_VERSION_TAG;
    public String o = Constants.MAIN_VERSION_TAG;
    public String p = Constants.MAIN_VERSION_TAG;
    public long q = 0;
    public String r = Constants.MAIN_VERSION_TAG;
    public String s = Constants.MAIN_VERSION_TAG;
    public String t = Constants.MAIN_VERSION_TAG;
    public String u = Constants.MAIN_VERSION_TAG;
    public String v = Constants.MAIN_VERSION_TAG;
    public String w = Constants.MAIN_VERSION_TAG;
    private String x = Constants.MAIN_VERSION_TAG;

    @Override // com.tencent.bugly.proguard.k
    public final void a(j jVar) {
        jVar.a(this.a, 0);
        jVar.a(this.b, 1);
        jVar.a(this.c, 2);
        jVar.a(this.d, 3);
        if (this.e != null) {
            jVar.a(this.e, 4);
        }
        jVar.a(this.f, 5);
        jVar.a(this.g, 6);
        jVar.a(this.h, 7);
        if (this.i != null) {
            jVar.a(this.i, 8);
        }
        if (this.j != null) {
            jVar.a(this.j, 9);
        }
        if (this.k != null) {
            jVar.a((Map) this.k, 10);
        }
        if (this.l != null) {
            jVar.a(this.l, 11);
        }
        jVar.a(this.m, 12);
        if (this.n != null) {
            jVar.a(this.n, 13);
        }
        if (this.o != null) {
            jVar.a(this.o, 14);
        }
        if (this.p != null) {
            jVar.a(this.p, 15);
        }
        jVar.a(this.q, 16);
        if (this.r != null) {
            jVar.a(this.r, 17);
        }
        if (this.s != null) {
            jVar.a(this.s, 18);
        }
        if (this.t != null) {
            jVar.a(this.t, 19);
        }
        if (this.u != null) {
            jVar.a(this.u, 20);
        }
        if (this.v != null) {
            jVar.a(this.v, 21);
        }
        if (this.w != null) {
            jVar.a(this.w, 22);
        }
        if (this.x != null) {
            jVar.a(this.x, 23);
        }
    }

    static {
        z.put(Constants.MAIN_VERSION_TAG, Constants.MAIN_VERSION_TAG);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        this.a = iVar.a(this.a, 0, true);
        this.b = iVar.b(1, true);
        this.c = iVar.b(2, true);
        this.d = iVar.b(3, true);
        this.e = iVar.b(4, false);
        this.f = iVar.b(5, true);
        this.g = iVar.a(this.g, 6, true);
        byte[] bArr = y;
        this.h = iVar.c(7, true);
        this.i = iVar.b(8, false);
        this.j = iVar.b(9, false);
        this.k = (Map) iVar.a(z, 10, false);
        this.l = iVar.b(11, false);
        this.m = iVar.a(this.m, 12, false);
        this.n = iVar.b(13, false);
        this.o = iVar.b(14, false);
        this.p = iVar.b(15, false);
        this.q = iVar.a(this.q, 16, false);
        this.r = iVar.b(17, false);
        this.s = iVar.b(18, false);
        this.t = iVar.b(19, false);
        this.u = iVar.b(20, false);
        this.v = iVar.b(21, false);
        this.w = iVar.b(22, false);
        this.x = iVar.b(23, false);
    }
}
