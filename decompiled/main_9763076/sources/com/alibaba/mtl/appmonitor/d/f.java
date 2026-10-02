package com.alibaba.mtl.appmonitor.d;

import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: AlarmSampling.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends g {
    String TAG;
    private int o;
    private int p;

    @Override // com.alibaba.mtl.appmonitor.d.g
    public /* bridge */ /* synthetic */ boolean a(int i, String str, String str2, Map map) {
        return super.a(i, str, str2, map);
    }

    public f(com.alibaba.mtl.appmonitor.a.f fVar, int i) {
        super(fVar, i);
        this.TAG = "AlarmSampling";
        this.o = 0;
        this.p = 0;
        this.o = i;
        this.p = i;
    }

    public boolean a(int i, String str, String str2, Boolean bool, Map<String, String> map) {
        h hVar;
        com.alibaba.mtl.log.e.i.a(this.TAG, "samplingSeed:", Integer.valueOf(i), "isSuccess:", bool, "successSampling:", Integer.valueOf(this.o), "failSampling:" + this.p);
        if (this.r != null && (hVar = this.r.get(str)) != null && (hVar instanceof d)) {
            return ((d) hVar).a(i, str2, bool, map);
        }
        if (bool.booleanValue()) {
            return i < this.o;
        }
        return i < this.p;
    }

    @Override // com.alibaba.mtl.appmonitor.d.g
    public void b(JSONObject jSONObject) {
        a(jSONObject);
        c(jSONObject);
        this.r.clear();
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("metrics");
            if (jSONArray != null) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < jSONArray.length()) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        String string = jSONObject2.getString("module");
                        if (com.alibaba.mtl.appmonitor.f.b.c(string)) {
                            h dVar = this.r.get(string);
                            if (dVar == null) {
                                dVar = new d(string, this.o, this.p);
                                this.r.put(string, dVar);
                            }
                            dVar.b(jSONObject2);
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

    @Override // com.alibaba.mtl.appmonitor.d.a
    protected void a(JSONObject jSONObject) {
        super.a(jSONObject);
        this.o = this.n;
        this.p = this.n;
        try {
            Integer numValueOf = Integer.valueOf(jSONObject.getInt("successSampling"));
            if (numValueOf != null) {
                this.o = numValueOf.intValue();
            }
            Integer numValueOf2 = Integer.valueOf(jSONObject.getInt("failSampling"));
            if (numValueOf2 != null) {
                this.p = numValueOf2.intValue();
            }
        } catch (Exception e) {
        }
    }

    @Override // com.alibaba.mtl.appmonitor.d.g
    public void setSampling(int sampling) {
        super.setSampling(sampling);
        this.o = sampling;
        this.p = sampling;
    }
}
