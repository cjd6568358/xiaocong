package com.tencent.mid.a;

import android.content.Context;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.mid.api.MidConstants;
import com.tencent.mid.api.MidEntity;
import com.tencent.mid.util.Util;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g extends f {
    protected g(Context context) {
        super(context);
    }

    private String a(String str) {
        return !Util.isEmpty(str) ? str : "-";
    }

    @Override // com.tencent.mid.a.f
    protected int a() {
        return 2;
    }

    @Override // com.tencent.mid.a.f
    protected void b(JSONObject jSONObject) throws JSONException {
        jSONObject.put("mid", PushConstants.PUSH_TYPE_NOTIFY);
        jSONObject.put(MidEntity.TAG_IMEI, a(Util.getImei(this.a)));
        jSONObject.put(MidEntity.TAG_IMSI, a(Util.getImsi(this.a)));
        jSONObject.put(MidEntity.TAG_MAC, a(Util.getWifiMacAddress(this.a)));
        jSONObject.put("ts", System.currentTimeMillis() / 1000);
        MidEntity midEntityA = h.a(this.a);
        if (midEntityA != null && midEntityA.isMidValid()) {
            jSONObject.put("mid", midEntityA.getMid());
        }
        String strB = com.tencent.mid.b.g.a(this.a).b();
        if (Util.isMidValid(strB)) {
            jSONObject.put(MidConstants.NEW_MID_TAG, strB);
        } else {
            jSONObject.put(MidConstants.NEW_MID_TAG, PushConstants.PUSH_TYPE_NOTIFY);
        }
        try {
            new com.tencent.mid.util.c(this.a).a(jSONObject);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
