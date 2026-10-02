package com.baidu.android.bbalbs.common.util;

import java.util.Comparator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements Comparator<b.a> {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    @Override // java.util.Comparator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(b.a aVar, b.a aVar2) {
        int i = aVar2.b - aVar.b;
        if (i != 0) {
            return i;
        }
        if (aVar.d && aVar2.d) {
            return 0;
        }
        if (aVar.d) {
            return -1;
        }
        if (aVar2.d) {
            return 1;
        }
        return i;
    }
}
