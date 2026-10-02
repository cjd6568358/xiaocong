package com.tencent.bugly.proguard;

import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ai extends k implements Cloneable {
    private static ArrayList<String> c;
    private String a = Constants.MAIN_VERSION_TAG;
    private ArrayList<String> b = null;

    @Override // com.tencent.bugly.proguard.k
    public final void a(j jVar) {
        jVar.a(this.a, 0);
        if (this.b != null) {
            jVar.a((Collection) this.b, 1);
        }
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        this.a = iVar.b(0, true);
        if (c == null) {
            c = new ArrayList<>();
            c.add(Constants.MAIN_VERSION_TAG);
        }
        this.b = (ArrayList) iVar.a(c, 1, false);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(StringBuilder sb, int i) {
    }
}
