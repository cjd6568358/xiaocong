package com.baidu.mobstat;

import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class t {
    private String a;
    private String b;
    private String c;

    public t(String str, String str2, String str3) {
        this.a = str == null ? Constants.MAIN_VERSION_TAG : str;
        this.b = str2 == null ? Constants.MAIN_VERSION_TAG : str2;
        this.c = str3 == null ? Constants.MAIN_VERSION_TAG : str3;
    }

    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("n", this.a);
            jSONObject.put(NotifyType.VIBRATE, this.b);
            jSONObject.put("w", this.c);
            return jSONObject;
        } catch (JSONException e) {
            bd.b(e);
            return null;
        }
    }

    public String b() {
        return this.a;
    }
}
