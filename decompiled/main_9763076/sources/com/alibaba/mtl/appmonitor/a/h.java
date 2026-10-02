package com.alibaba.mtl.appmonitor.a;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: UTEvent.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h implements com.alibaba.mtl.appmonitor.c.b {
    public int e;
    public Map<String, String> n;
    public String u;
    public String v;
    public String w;
    public String x;

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        this.u = null;
        this.e = 0;
        this.v = null;
        this.w = null;
        this.x = null;
        if (this.n != null) {
            this.n.clear();
        }
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
        if (this.n == null) {
            this.n = new HashMap();
        }
    }
}
