package com.baidu.uaq.agent.android.harvest;

import android.text.TextUtils;
import com.baidu.uaq.agent.android.UAQ;
import com.baidu.uaq.agent.android.harvest.bean.g;
import com.baidu.uaq.agent.android.harvest.type.c;
import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.kookong.app.data.AppConst;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: HarvestData.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends c {
    private static final UAQ AGENT = UAQ.getInstance();
    private static final com.baidu.uaq.agent.android.logging.a LOG = com.baidu.uaq.agent.android.logging.b.bg();
    private com.baidu.uaq.agent.android.harvest.bean.b A = new com.baidu.uaq.agent.android.harvest.bean.b();
    private com.baidu.uaq.agent.android.harvest.health.a aw = new com.baidu.uaq.agent.android.harvest.health.a();
    private g ax = new g(new com.baidu.uaq.agent.android.transmission.lss.a());
    private com.baidu.uaq.agent.android.harvest.bean.c l;
    private com.baidu.uaq.agent.android.harvest.bean.a m;

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONArray U() {
        JSONArray jSONArray = new JSONArray();
        try {
            jSONArray.put(0, this.A.aw());
            if (AGENT.isNeedBasicInfo()) {
                jSONArray.put(1, e().aw());
            } else {
                jSONArray.put(1, new JSONArray());
            }
            jSONArray.put(2, 0);
            jSONArray.put(3, new JSONArray());
            JSONArray jSONArray2 = new JSONArray();
            for (Map.Entry<String, com.baidu.uaq.agent.android.metric.a> entry : com.baidu.uaq.agent.android.stats.a.br().bt().entrySet()) {
                JSONArray measurement = new JSONArray();
                com.baidu.uaq.agent.android.metric.a metric = entry.getValue();
                JSONObject jo = new JSONObject();
                jo.put(RNMessageModule.NAME, metric.getName());
                jo.put("scope", metric.bk());
                measurement.put(jo);
                measurement.put(metric.z());
                jSONArray2.put(measurement);
            }
            jSONArray.put(4, jSONArray2);
            jSONArray.put(5, new JSONObject());
            jSONArray.put(6, new JSONArray());
            jSONArray.put(7, this.aw.aw());
            if (AGENT.isNeedBasicInfo()) {
                jSONArray.put(8, f().aw());
            } else {
                jSONArray.put(8, new JSONArray());
            }
            jSONArray.put(9, new JSONArray());
            jSONArray.put(10, this.ax.aw());
            jSONArray.put(11, V());
        } catch (JSONException e) {
            LOG.a("Caught error while HarvestData asJSONArray: ", e);
            com.baidu.uaq.agent.android.harvest.health.a.a(e);
        }
        return jSONArray;
    }

    private JSONObject V() throws JSONException {
        JSONObject jo = new JSONObject();
        jo.put("wanType", com.baidu.uaq.agent.android.a.c());
        jo.put("carrier", com.baidu.uaq.agent.android.a.d());
        jo.put("timestamp", System.currentTimeMillis());
        String channel = AGENT.getConfig().getChannel();
        if (!TextUtils.isEmpty(channel)) {
            jo.put(AppConst.CHANNEL_NAME, AGENT.getConfig().getChannel());
        }
        return jo;
    }

    public void reset() {
        this.aw.clear();
    }

    public void a(com.baidu.uaq.agent.android.harvest.bean.b dataToken) {
        if (dataToken != null) {
            this.A = dataToken;
        }
    }

    public com.baidu.uaq.agent.android.harvest.bean.c e() {
        if (this.l == null) {
            this.l = com.baidu.uaq.agent.android.a.e();
        }
        return this.l;
    }

    public com.baidu.uaq.agent.android.harvest.health.a W() {
        return this.aw;
    }

    public com.baidu.uaq.agent.android.harvest.bean.b r() {
        return this.A;
    }

    public com.baidu.uaq.agent.android.harvest.bean.a f() {
        if (this.m == null) {
            this.m = com.baidu.uaq.agent.android.a.f();
        }
        return this.m;
    }

    public g X() {
        return this.ax;
    }
}
