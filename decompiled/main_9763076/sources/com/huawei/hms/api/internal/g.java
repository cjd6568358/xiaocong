package com.huawei.hms.api.internal;

import java.util.List;

/* JADX INFO: compiled from: ProtocolNegotiate.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static g b = new g();
    private int a = 1;

    public static g a() {
        return b;
    }

    public int a(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            this.a = 1;
            return this.a;
        }
        if (!list.contains(2)) {
            this.a = list.get(list.size() - 1).intValue();
        } else {
            this.a = 2;
        }
        return this.a;
    }

    public int b() {
        return this.a;
    }
}
