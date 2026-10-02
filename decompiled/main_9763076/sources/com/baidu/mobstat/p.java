package com.baidu.mobstat;

import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class p {
    private String a;
    private String b;
    private String c;
    private String d;

    public p(String str, String str2, String str3, String str4) {
        str = str == null ? Constants.MAIN_VERSION_TAG : str;
        str2 = str2 == null ? Constants.MAIN_VERSION_TAG : str2;
        str3 = str3 == null ? Constants.MAIN_VERSION_TAG : str3;
        str4 = str4 == null ? Constants.MAIN_VERSION_TAG : str4;
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("n", this.a);
            jSONObject.put(NotifyType.VIBRATE, this.b);
            jSONObject.put("c", this.c);
            jSONObject.put("a", this.d);
            return jSONObject;
        } catch (JSONException e) {
            bd.b(e);
            return null;
        }
    }
}
