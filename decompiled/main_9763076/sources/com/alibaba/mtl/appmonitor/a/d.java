package com.alibaba.mtl.appmonitor.a;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Event.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public abstract class d implements com.alibaba.mtl.appmonitor.c.b {
    public int e;
    public String o;
    public String p;
    public String s;

    public JSONObject a() {
        JSONObject jSONObject = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.e.class, new Object[0]);
        try {
            jSONObject.put("page", this.o);
            jSONObject.put("monitorPoint", this.p);
            if (this.s != null) {
                jSONObject.put("arg", this.s);
            }
        } catch (JSONException e) {
        }
        return jSONObject;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        this.e = 0;
        this.o = null;
        this.p = null;
        this.s = null;
    }

    @Override // com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... params) {
        this.e = ((Integer) params[0]).intValue();
        this.o = (String) params[1];
        this.p = (String) params[2];
        if (params.length > 3 && params[3] != null) {
            this.s = (String) params[3];
        }
    }
}
