package com.alibaba.mtl.appmonitor.d;

import android.text.TextUtils;
import java.util.Set;

/* JADX INFO: compiled from: AccurateSampleCondition.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    private a a;
    private Set<String> c;

    /* JADX INFO: compiled from: AccurateSampleCondition.java */
    private enum a {
        IN,
        NOT_IN
    }

    public boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        boolean zContains = this.c.contains(str);
        if (this.a == a.IN) {
            return zContains;
        }
        return !zContains;
    }
}
