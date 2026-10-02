package com.alibaba.mtl.appmonitor.d;

import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: AlarmModuleSampling.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d extends h {
    private int o;
    private int p;

    @Override // com.alibaba.mtl.appmonitor.d.h
    public /* bridge */ /* synthetic */ boolean a(int i, String str, Map map) {
        return super.a(i, str, map);
    }

    public d(String str, int i, int i2) {
        super(str, 0);
        this.o = this.n;
        this.p = this.n;
    }

    public boolean a(int i, String str, Boolean bool, Map<String, String> map) {
        i iVar;
        com.alibaba.mtl.log.e.i.a("AlarmModuleSampling", "samplingSeed:", Integer.valueOf(i), "isSuccess:", bool, "successSampling:", Integer.valueOf(this.o), "failSampling:", Integer.valueOf(this.p));
        return (this.s == null || (iVar = this.s.get(str)) == null || !(iVar instanceof e)) ? a(i, bool.booleanValue()) : ((e) iVar).a(i, bool, map);
    }

    @Override // com.alibaba.mtl.appmonitor.d.h
    public void b(JSONObject jSONObject) {
        a(jSONObject);
        try {
            JSONArray jSONArray = jSONObject.getJSONArray("monitorPoints");
            if (jSONArray != null) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 < jSONArray.length()) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                        String string = jSONObject2.getString("monitorPoint");
                        if (com.alibaba.mtl.appmonitor.f.b.c(string)) {
                            i eVar = this.s.get(string);
                            if (eVar == null) {
                                eVar = new e(string, this.o, this.p);
                                this.s.put(string, eVar);
                            }
                            eVar.b(jSONObject2);
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

    protected boolean a(int i, boolean z) {
        if (z) {
            return i < this.o;
        }
        return i < this.p;
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
            com.alibaba.mtl.log.e.i.a("AlarmModuleSampling", "[updateSelfSampling]", jSONObject, "successSampling:", numValueOf, "failSampling");
        } catch (Exception e) {
        }
    }
}
