package com.alibaba.mtl.appmonitor.a;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: AlarmEvent.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends d {
    public int f = 0;
    public int g = 0;

    /* JADX INFO: renamed from: g, reason: collision with other field name */
    public Map<String, String> f13g;
    public Map<String, Integer> h;

    public synchronized void f() {
        this.f++;
    }

    public synchronized void g() {
        this.g++;
    }

    public synchronized void a(String str, String str2) {
        synchronized (this) {
            if (!com.alibaba.mtl.appmonitor.f.b.isBlank(str)) {
                if (this.f13g == null) {
                    this.f13g = new HashMap();
                }
                if (this.h == null) {
                    this.h = new HashMap();
                }
                if (com.alibaba.mtl.appmonitor.f.b.c(str2)) {
                    this.f13g.put(str, str2.substring(0, str2.length() <= 100 ? str2.length() : 100));
                }
                if (!this.h.containsKey(str)) {
                    this.h.put(str, 1);
                } else {
                    this.h.put(str, Integer.valueOf(this.h.get(str).intValue() + 1));
                }
            }
        }
    }

    @Override // com.alibaba.mtl.appmonitor.a.d
    public synchronized JSONObject a() {
        JSONObject jSONObjectA;
        jSONObjectA = super.a();
        try {
            jSONObjectA.put("successCount", this.f);
            jSONObjectA.put("failCount", this.g);
            if (this.h != null) {
                JSONArray jSONArray = (JSONArray) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.d.class, new Object[0]);
                for (Map.Entry<String, Integer> entry : this.h.entrySet()) {
                    JSONObject jSONObject = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(com.alibaba.mtl.appmonitor.c.e.class, new Object[0]);
                    String key = entry.getKey();
                    jSONObject.put("errorCode", key);
                    jSONObject.put("errorCount", entry.getValue());
                    if (this.f13g.containsKey(key)) {
                        jSONObject.put("errorMsg", this.f13g.get(key));
                    }
                    jSONArray.put(jSONObject);
                }
                jSONObjectA.put("errors", jSONArray);
            }
        } catch (Exception e) {
        }
        return jSONObjectA;
    }

    @Override // com.alibaba.mtl.appmonitor.a.d, com.alibaba.mtl.appmonitor.c.b
    public synchronized void clean() {
        super.clean();
        this.f = 0;
        this.g = 0;
        if (this.f13g != null) {
            this.f13g.clear();
        }
        if (this.h != null) {
            this.h.clear();
        }
    }
}
