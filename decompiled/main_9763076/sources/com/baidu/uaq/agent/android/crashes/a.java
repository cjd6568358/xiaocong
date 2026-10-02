package com.baidu.uaq.agent.android.crashes;

import com.tencent.android.tpush.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ApplicationInfo.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a extends com.baidu.uaq.agent.android.harvest.type.d {
    private String I;
    private String J;
    private String K;

    public a() {
        this.I = Constants.MAIN_VERSION_TAG;
        this.J = Constants.MAIN_VERSION_TAG;
        this.K = Constants.MAIN_VERSION_TAG;
    }

    public a(com.baidu.uaq.agent.android.harvest.bean.a applicationInformation) {
        this.I = Constants.MAIN_VERSION_TAG;
        this.J = Constants.MAIN_VERSION_TAG;
        this.K = Constants.MAIN_VERSION_TAG;
        this.I = applicationInformation.ae();
        this.J = applicationInformation.ac();
        this.K = applicationInformation.ad();
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONObject z() {
        JSONObject data = new JSONObject();
        try {
            data.put("appName", this.I);
            data.put("appVersion", this.J);
            data.put("bundleId", this.K);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static a a(JSONObject jsonObject) {
        a info = new a();
        try {
            info.I = jsonObject.getString("appName");
            info.J = jsonObject.getString("appVersion");
            info.K = jsonObject.getString("bundleId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }
}
