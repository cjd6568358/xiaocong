package com.alibaba.mtl.appmonitor.d;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: EventTypeSampling.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class g extends a<JSONObject> {
    private com.alibaba.mtl.appmonitor.a.f e;
    protected int q;
    protected Map<String, h> r;

    public g(com.alibaba.mtl.appmonitor.a.f fVar, int i) {
        super(i);
        this.q = -1;
        this.e = fVar;
        this.r = Collections.synchronizedMap(new HashMap());
    }

    public boolean a(int i, String str, String str2, Map<String, String> map) {
        h hVar;
        if (this.r != null && (hVar = this.r.get(str)) != null) {
            return hVar.a(i, str2, map);
        }
        if (i < this.n) {
            return true;
        }
        return false;
    }

    public void b(JSONObject jSONObject) {
        a(jSONObject);
        c(jSONObject);
        this.r.clear();
        try {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("metrics");
            if (jSONArrayOptJSONArray != null) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < jSONArrayOptJSONArray.length()) {
                        JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i2);
                        String strOptString = jSONObject2.optString("module");
                        if (com.alibaba.mtl.appmonitor.f.b.c(strOptString)) {
                            h hVar = this.r.get(strOptString);
                            if (hVar == null) {
                                hVar = new h(strOptString, this.n);
                                this.r.put(strOptString, hVar);
                            }
                            hVar.b(jSONObject2);
                        }
                        i = i2 + 1;
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    protected void c(JSONObject jSONObject) {
        com.alibaba.mtl.log.e.i.a("EventTypeSampling", "[updateEventTypeTriggerCount]", this, jSONObject);
        if (jSONObject != null) {
            try {
                int iOptInt = jSONObject.optInt("cacheCount");
                if (iOptInt > 0 && this.e != null) {
                    this.e.b(iOptInt);
                }
            } catch (Throwable th) {
                com.alibaba.mtl.log.e.i.a("EventTypeSampling", "updateTriggerCount", th);
            }
        }
    }

    public void setSampling(int sampling) {
        this.n = sampling;
    }
}
