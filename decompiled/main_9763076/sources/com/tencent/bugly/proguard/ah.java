package com.tencent.bugly.proguard;

import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: BUGLY */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class ah extends k implements Cloneable {
    public String a = Constants.MAIN_VERSION_TAG;
    public String b = Constants.MAIN_VERSION_TAG;
    public String c = Constants.MAIN_VERSION_TAG;
    private String e = Constants.MAIN_VERSION_TAG;
    public String d = Constants.MAIN_VERSION_TAG;

    @Override // com.tencent.bugly.proguard.k
    public final void a(j jVar) {
        jVar.a(this.a, 0);
        if (this.b != null) {
            jVar.a(this.b, 1);
        }
        if (this.c != null) {
            jVar.a(this.c, 2);
        }
        if (this.e != null) {
            jVar.a(this.e, 3);
        }
        if (this.d != null) {
            jVar.a(this.d, 4);
        }
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(i iVar) {
        this.a = iVar.b(0, true);
        this.b = iVar.b(1, false);
        this.c = iVar.b(2, false);
        this.e = iVar.b(3, false);
        this.d = iVar.b(4, false);
    }

    @Override // com.tencent.bugly.proguard.k
    public final void a(StringBuilder sb, int i) {
    }
}
