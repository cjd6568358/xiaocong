package com.alibaba.mtl.appmonitor.c;

import org.json.JSONArray;

/* JADX INFO: compiled from: ReuseJSONArray.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d extends JSONArray implements b {
    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < length()) {
                Object objOpt = opt(i2);
                if (objOpt != null && (objOpt instanceof b)) {
                    a.a().a((b) objOpt);
                }
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
    }
}
