package com.baidu.mobstat;

import java.util.Comparator;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ce implements Comparator<String> {
    final /* synthetic */ cc a;

    ce(cc ccVar) {
        this.a = ccVar;
    }

    @Override // java.util.Comparator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compare(String str, String str2) {
        return str.compareTo(str2);
    }
}
