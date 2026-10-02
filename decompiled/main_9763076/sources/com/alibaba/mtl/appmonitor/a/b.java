package com.alibaba.mtl.appmonitor.a;

import org.json.JSONObject;

/* JADX INFO: compiled from: CountEvent.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b extends d {
    public int count;
    public double e;

    public synchronized void a(double d) {
        this.e += d;
        this.count++;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d
    public synchronized JSONObject a() {
        JSONObject jSONObjectA;
        jSONObjectA = super.a();
        try {
            jSONObjectA.put("count", this.count);
            jSONObjectA.put("value", this.e);
        } catch (Exception e) {
        }
        return jSONObjectA;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public synchronized void fill(Object... params) {
        super.fill(params);
        this.e = 0.0d;
        this.count = 0;
    }
}
