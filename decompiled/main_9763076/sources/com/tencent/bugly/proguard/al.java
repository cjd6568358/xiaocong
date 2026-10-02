package com.tencent.bugly.proguard;

import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class al extends k implements Cloneable {
    private static ArrayList<ak> b;
    public ArrayList<ak> a = null;

    @Override // com.tencent.bugly.proguard.k
    public final void a(j jVar) {
        jVar.a((Collection) this.a, 0);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        if (b == null) {
            b = new ArrayList<>();
            b.add(new ak());
        }
        this.a = (ArrayList) iVar.a(b, 0, true);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(StringBuilder sb, int i) {
    }
}
