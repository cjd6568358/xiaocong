package com.tencent.bugly.proguard;

import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class aq extends k {
    private static Map<String, String> i = new HashMap();
    public long a = 0;
    public byte b = 0;
    public String c = Constants.MAIN_VERSION_TAG;
    public String d = Constants.MAIN_VERSION_TAG;
    public String e = Constants.MAIN_VERSION_TAG;
    public Map<String, String> f = null;
    public String g = Constants.MAIN_VERSION_TAG;
    public boolean h = true;

    @Override // com.tencent.bugly.proguard.k
    public final void a(j jVar) {
        jVar.a(this.a, 0);
        jVar.a(this.b, 1);
        if (this.c != null) {
            jVar.a(this.c, 2);
        }
        if (this.d != null) {
            jVar.a(this.d, 3);
        }
        if (this.e != null) {
            jVar.a(this.e, 4);
        }
        if (this.f != null) {
            jVar.a((Map) this.f, 5);
        }
        if (this.g != null) {
            jVar.a(this.g, 6);
        }
        jVar.a(this.h, 7);
    }

    static {
        i.put(Constants.MAIN_VERSION_TAG, Constants.MAIN_VERSION_TAG);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        this.a = iVar.a(this.a, 0, true);
        this.b = iVar.a(this.b, 1, true);
        this.c = iVar.b(2, false);
        this.d = iVar.b(3, false);
        this.e = iVar.b(4, false);
        this.f = (Map) iVar.a(i, 5, false);
        this.g = iVar.b(6, false);
        boolean z = this.h;
        this.h = iVar.a(7, false);
    }
}
