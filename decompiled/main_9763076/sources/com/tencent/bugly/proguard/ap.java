package com.tencent.bugly.proguard;

import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ap extends k implements Cloneable {
    private static ao m;
    private static Map<String, String> n;
    private static /* synthetic */ boolean o;
    public boolean a = true;
    public boolean b = true;
    public boolean c = true;
    public String d = Constants.MAIN_VERSION_TAG;
    public String e = Constants.MAIN_VERSION_TAG;
    public ao f = null;
    public Map<String, String> g = null;
    public long h = 0;
    private String j = Constants.MAIN_VERSION_TAG;
    private String k = Constants.MAIN_VERSION_TAG;
    private int l = 0;
    public int i = 0;

    static {
        o = !ap.class.desiredAssertionStatus();
        m = new ao();
        n = new HashMap();
        n.put(Constants.MAIN_VERSION_TAG, Constants.MAIN_VERSION_TAG);
    }

    public final boolean equals(Object o2) {
        if (o2 == null) {
            return false;
        }
        ap apVar = (ap) o2;
        return l.a(this.a, apVar.a) && l.a(this.b, apVar.b) && l.a(this.c, apVar.c) && l.a(this.d, apVar.d) && l.a(this.e, apVar.e) && l.a(this.f, apVar.f) && l.a(this.g, apVar.g) && l.a(this.h, apVar.h) && l.a(this.j, apVar.j) && l.a(this.k, apVar.k) && l.a(this.l, apVar.l) && l.a(this.i, apVar.i);
    }

    public final int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            if (o) {
                return null;
            }
            throw new AssertionError();
        }
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(j jVar) {
        jVar.a(this.a, 0);
        jVar.a(this.b, 1);
        jVar.a(this.c, 2);
        if (this.d != null) {
            jVar.a(this.d, 3);
        }
        if (this.e != null) {
            jVar.a(this.e, 4);
        }
        if (this.f != null) {
            jVar.a((k) this.f, 5);
        }
        if (this.g != null) {
            jVar.a((Map) this.g, 6);
        }
        jVar.a(this.h, 7);
        if (this.j != null) {
            jVar.a(this.j, 8);
        }
        if (this.k != null) {
            jVar.a(this.k, 9);
        }
        jVar.a(this.l, 10);
        jVar.a(this.i, 11);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        boolean z = this.a;
        this.a = iVar.a(0, true);
        boolean z2 = this.b;
        this.b = iVar.a(1, true);
        boolean z3 = this.c;
        this.c = iVar.a(2, true);
        this.d = iVar.b(3, false);
        this.e = iVar.b(4, false);
        this.f = (ao) iVar.a((k) m, 5, false);
        this.g = (Map) iVar.a(n, 6, false);
        this.h = iVar.a(this.h, 7, false);
        this.j = iVar.b(8, false);
        this.k = iVar.b(9, false);
        this.l = iVar.a(this.l, 10, false);
        this.i = iVar.a(this.i, 11, false);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(StringBuilder sb, int i) {
        h hVar = new h(sb, i);
        hVar.a(this.a, "enable");
        hVar.a(this.b, "enableUserInfo");
        hVar.a(this.c, "enableQuery");
        hVar.a(this.d, "url");
        hVar.a(this.e, "expUrl");
        hVar.a((k) this.f, "security");
        hVar.a((Map) this.g, "valueMap");
        hVar.a(this.h, "strategylastUpdateTime");
        hVar.a(this.j, "httpsUrl");
        hVar.a(this.k, "httpsExpUrl");
        hVar.a(this.l, "eventRecordCount");
        hVar.a(this.i, "eventTimeInterval");
    }
}
