package com.alibaba.mtl.log.a;

import com.alibaba.mtl.log.e.i;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: HostConfigMgr.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private static d a = new d();
    private String S;
    private Map<String, c> v = Collections.synchronizedMap(new HashMap());

    public static d a() {
        return a;
    }

    public void b(String str) {
        JSONObject jSONObject;
        i.a("HostConfigMgr", "host config:" + str);
        if (str != null) {
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                if (jSONObject2 != null) {
                    JSONObject jSONObject3 = jSONObject2.getJSONObject("content");
                    if (jSONObject3 != null && (jSONObject = jSONObject3.getJSONObject("hosts")) != null) {
                        Iterator<String> itKeys = jSONObject.keys();
                        while (itKeys.hasNext()) {
                            String next = itKeys.next();
                            if (next != null) {
                                c cVar = new c();
                                JSONObject jSONObject4 = jSONObject.getJSONObject(next);
                                if (jSONObject4 != null) {
                                    cVar.R = next.substring(1);
                                    cVar.Q = jSONObject4.getString("host");
                                    JSONArray jSONArray = jSONObject4.getJSONArray("eids");
                                    if (jSONArray != null) {
                                        cVar.a = new ArrayList<>();
                                        for (int i = 0; i < jSONArray.length(); i++) {
                                            cVar.a.add(jSONArray.getString(i));
                                        }
                                    }
                                }
                                this.v.put(cVar.R + Constants.MAIN_VERSION_TAG, cVar);
                            }
                        }
                    }
                    this.S = jSONObject2.getString("timestamp");
                }
            } catch (Throwable th) {
            }
        }
    }

    public Map<String, c> b() {
        return this.v;
    }
}
