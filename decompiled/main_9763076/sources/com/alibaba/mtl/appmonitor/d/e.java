package com.alibaba.mtl.appmonitor.d;

import java.util.Iterator;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: compiled from: AlarmMonitorPointSampling.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e extends i {
    private int o;
    private int p;

    @Override // com.alibaba.mtl.appmonitor.d.i
    public /* bridge */ /* synthetic */ boolean a(int i, Map map) {
        return super.a(i, (Map<String, String>) map);
    }

    public e(String str, int i, int i2) {
        super(str, 0);
        this.o = i;
        this.p = i2;
    }

    public boolean a(int i, Boolean bool, Map<String, String> map) {
        com.alibaba.mtl.log.e.i.a("AlarmMonitorPointSampling", "samplingSeed:", Integer.valueOf(i), "isSuccess:", bool, "successSampling:", Integer.valueOf(this.o), "failSampling:", Integer.valueOf(this.p));
        if (this.e != null && map != null) {
            Iterator<c> it = this.e.iterator();
            while (it.hasNext()) {
                Boolean boolA = it.next().a(i, map);
                if (boolA != null) {
                    return boolA.booleanValue();
                }
            }
        }
        return a(i, bool.booleanValue());
    }

    protected boolean a(int i, boolean z) {
        if (z) {
            return i < this.o;
        }
        return i < this.p;
    }

    @Override // com.alibaba.mtl.appmonitor.d.i
    public void b(JSONObject jSONObject) {
        super.b(jSONObject);
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
            com.alibaba.mtl.log.e.i.a("AlarmMonitorPointSampling", "[updateSelfSampling]", jSONObject, "successSampling:", numValueOf, "failSampling", numValueOf2);
        } catch (Exception e) {
        }
    }
}
