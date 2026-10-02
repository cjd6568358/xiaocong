package com.tencent.bugly.proguard;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ar extends k implements Cloneable {
    private static ArrayList<aq> f;
    private static Map<String, String> g;
    public byte a = 0;
    public String b = Constants.MAIN_VERSION_TAG;
    public String c = Constants.MAIN_VERSION_TAG;
    public ArrayList<aq> d = null;
    public Map<String, String> e = null;

    @Override // com.tencent.bugly.proguard.k
    public final void a(j jVar) {
        jVar.a(this.a, 0);
        if (this.b != null) {
            jVar.a(this.b, 1);
        }
        if (this.c != null) {
            jVar.a(this.c, 2);
        }
        if (this.d != null) {
            jVar.a((Collection) this.d, 3);
        }
        if (this.e != null) {
            jVar.a((Map) this.e, 4);
        }
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        this.a = iVar.a(this.a, 0, true);
        this.b = iVar.b(1, false);
        this.c = iVar.b(2, false);
        if (f == null) {
            f = new ArrayList<>();
            f.add(new aq());
        }
        this.d = (ArrayList) iVar.a(f, 3, false);
        if (g == null) {
            g = new HashMap();
            g.put(Constants.MAIN_VERSION_TAG, Constants.MAIN_VERSION_TAG);
        }
        this.e = (Map) iVar.a(g, 4, false);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(StringBuilder sb, int i) {
    }
}
