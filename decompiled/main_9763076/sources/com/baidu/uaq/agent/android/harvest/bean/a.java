package com.baidu.uaq.agent.android.harvest.bean;

import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: compiled from: ApplicationInformation.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends com.baidu.uaq.agent.android.harvest.type.c {
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private String aA;
    private String aB;
    private String aC;

    public a(String appName, String appVersion, String packageId) {
        this.aA = appName;
        this.aB = appVersion;
        this.aC = packageId;
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONArray U() {
        JSONArray array = new JSONArray();
        try {
            C(this.aA);
            array.put(0, this.aA);
            C(this.aB);
            array.put(1, this.aB);
            C(this.aC);
            array.put(2, this.aC);
        } catch (JSONException e) {
            LOG.a("Caught error while ApplicationInformation asJSONArray", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        return array;
    }

    public String ac() {
        return this.aB;
    }

    public String ad() {
        return this.aC;
    }

    public String ae() {
        return this.aA;
    }
}
