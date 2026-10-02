package com.baidu.uaq.agent.android.crashes;

import com.ixiaocong.smarthome.phone.rn.module.RNMessageModule;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ExceptionInfo.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class f extends com.baidu.uaq.agent.android.harvest.type.d {
    private String className;
    private String message;

    public f() {
    }

    public f(Throwable throwable) {
        this.className = throwable.getClass().getName();
        if (throwable.getMessage() != null) {
            this.message = throwable.getMessage();
        } else {
            this.message = Constants.MAIN_VERSION_TAG;
        }
    }

    @Override // com.baidu.uaq.agent.android.harvest.type.a
    public JSONObject z() {
        JSONObject data = new JSONObject();
        try {
            data.put(RNMessageModule.NAME, this.className);
            data.put("cause", this.message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static f c(JSONObject jsonObject) {
        f info = new f();
        try {
            info.className = jsonObject.getString(RNMessageModule.NAME);
            info.message = jsonObject.getString("cause");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return info;
    }
}
