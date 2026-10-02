package com.alibaba.mtl.appmonitor.d;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: MonitorPointSampling.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class i extends a<JSONObject> {
    protected List<c> e;
    private String p;

    public i(String str, int i) {
        super(i);
        this.p = str;
    }

    public boolean a(int i, Map<String, String> map) {
        if (this.e != null && map != null) {
            Iterator<c> it = this.e.iterator();
            while (it.hasNext()) {
                Boolean boolA = it.next().a(i, map);
                if (boolA != null) {
                    return boolA.booleanValue();
                }
            }
        }
        return a(i);
    }

    public void b(JSONObject jSONObject) {
        a(jSONObject);
        try {
            JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(PushConstants.EXTRA);
            if (jSONArrayOptJSONArray != null) {
                for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
                    JSONObject jSONObject2 = jSONArrayOptJSONArray.getJSONObject(i);
                    c cVar = new c(this.n);
                    if (this.e == null) {
                        this.e = new ArrayList();
                    }
                    this.e.add(cVar);
                    cVar.b(jSONObject2);
                }
            }
        } catch (Exception e) {
        }
    }
}
