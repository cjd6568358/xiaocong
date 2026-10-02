package com.alibaba.mtl.appmonitor.d;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: compiled from: AccurateSampling.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c extends a<JSONObject> {
    private Map<String, b> q;

    public c(int i) {
        super(i);
        this.q = new HashMap();
    }

    public void b(JSONObject jSONObject) {
        a(jSONObject);
    }

    public Boolean a(int i, Map<String, String> map) {
        if (map == null || this.q == null) {
            return null;
        }
        for (String str : this.q.keySet()) {
            if (!this.q.get(str).b(map.get(str))) {
                return null;
            }
        }
        return Boolean.valueOf(a(i));
    }
}
