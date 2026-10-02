package com.baidu.uaq.agent.android.harvest.bean;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: Transmission.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends com.baidu.uaq.agent.android.harvest.type.d {
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private String aT;

    public f(String data) {
        this.aT = data;
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    /* JADX INFO: renamed from: av, reason: merged with bridge method [inline-methods] */
    public JSONObject aw() {
        try {
            JSONObject jsonObject = new JSONObject(this.aT);
            return jsonObject;
        } catch (JSONException e) {
            LOG.a("Caught error while Transmission asJSONObject: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
            return null;
        }
    }
}
