package com.baidu.mobstat;

import java.util.Comparator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class h implements Comparator<i> {
    final /* synthetic */ g a;

    h(g gVar) {
        this.a = gVar;
    }

    @Override // java.util.Comparator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(i iVar, i iVar2) {
        int i = iVar2.b - iVar.b;
        if (i != 0) {
            return i;
        }
        if (iVar.d && iVar2.d) {
            return 0;
        }
        if (iVar.d) {
            return -1;
        }
        if (iVar2.d) {
            return 1;
        }
        return i;
    }
}
