package com.baidu.mobstat;

import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.mid.api.MidEntity;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j {
    public String a;
    public String b;
    public int c;

    private j() {
        this.c = 2;
    }

    /* synthetic */ j(h hVar) {
        this();
    }

    public static j a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString("deviceid");
            String string2 = jSONObject.getString(MidEntity.TAG_IMEI);
            int i = jSONObject.getInt(MidEntity.TAG_VER);
            if (TextUtils.isEmpty(string) || string2 == null) {
                return null;
            }
            j jVar = new j();
            jVar.a = string;
            jVar.b = string2;
            jVar.c = i;
            return jVar;
        } catch (JSONException e) {
            g.b(e);
            return null;
        }
    }

    public String a() {
        try {
            return new JSONObject().put("deviceid", this.a).put(MidEntity.TAG_IMEI, this.b).put(MidEntity.TAG_VER, this.c).toString();
        } catch (JSONException e) {
            g.b(e);
            return null;
        }
    }

    public String b() {
        String str = this.b;
        if (TextUtils.isEmpty(str)) {
            str = PushConstants.PUSH_TYPE_NOTIFY;
        }
        return this.a + "|" + new StringBuffer(str).reverse().toString();
    }
}
