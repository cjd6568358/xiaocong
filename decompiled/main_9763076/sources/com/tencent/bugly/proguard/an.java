package com.tencent.bugly.proguard;

import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class an extends k {
    private static byte[] i = {0};
    private static Map<String, String> j = new HashMap();
    public byte a = 0;
    public int b = 0;
    public byte[] c = null;
    public String d = Constants.MAIN_VERSION_TAG;
    public long e = 0;
    private String h = Constants.MAIN_VERSION_TAG;
    public String f = Constants.MAIN_VERSION_TAG;
    public Map<String, String> g = null;

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
        jVar.a(this.e, 4);
        if (this.h != null) {
            jVar.a(this.h, 5);
        }
        if (this.f != null) {
            jVar.a(this.f, 6);
        }
        if (this.g != null) {
            jVar.a((Map) this.g, 7);
        }
    }

    static {
        j.put(Constants.MAIN_VERSION_TAG, Constants.MAIN_VERSION_TAG);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        this.a = iVar.a(this.a, 0, true);
        this.b = iVar.a(this.b, 1, true);
        byte[] bArr = i;
        this.c = iVar.c(2, false);
        this.d = iVar.b(3, false);
        this.e = iVar.a(this.e, 4, false);
        this.h = iVar.b(5, false);
        this.f = iVar.b(6, false);
        this.g = (Map) iVar.a(j, 7, false);
    }
}
