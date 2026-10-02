package com.tencent.android.tpush.common;

import android.content.Context;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.tencent.android.tpush.XGPushConfig;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class o {
    private static o a = null;
    private Context b;
    private String c;
    private String d;

    private o(Context context) {
        this.b = null;
        this.c = null;
        this.d = null;
        this.b = context.getApplicationContext();
        this.c = t.f(context);
        this.d = String.valueOf(3.24f);
    }

    public static synchronized o a(Context context) {
        if (a == null) {
            a = new o(context);
        }
        return a;
    }

    public String a() {
        boolean z;
        int i;
        int i2;
        boolean z2 = false;
        JSONObject jSONObject = new JSONObject();
        try {
            e.a(jSONObject, "appVer", this.c);
            e.a(jSONObject, "appSdkVer", this.d);
            e.a(jSONObject, "ch", XGPushConfig.getInstallChannel(this.b));
            e.a(jSONObject, "gs", XGPushConfig.getGameServer(this.b));
            if (s.a(this.b).c() || (XGPushConfig.isUsedOtherPush(this.b) && com.tencent.android.tpush.c.e.a(this.b).a())) {
                String strF = com.tencent.android.tpush.c.e.a(this.b).f();
                String strD = com.tencent.android.tpush.c.e.a(this.b).d();
                com.tencent.android.tpush.a.a.f(Constants.OTHER_PUSH_TAG, "Reservert info: other push token is : " + strD + "  other push type: " + strF);
                if (t.c(strF) || t.c(strD)) {
                    z = false;
                } else {
                    e.a(jSONObject, strF, strD);
                    z = true;
                }
                if (!s.a(this.b).c()) {
                    i = -1;
                } else {
                    i = 2;
                }
                if (XGPushConfig.isUsedOtherPush(this.b)) {
                    i = 1;
                }
                i2 = i;
                z2 = z;
            } else {
                i2 = 0;
            }
            if (!z2) {
                com.tencent.android.tpush.a.a.f(Constants.OTHER_PUSH_TAG, "Reservert info: use normal xg token register");
                e.a(jSONObject, "fcm", Constants.MAIN_VERSION_TAG);
                e.a(jSONObject, "miid", Constants.MAIN_VERSION_TAG);
            }
            int iA = n.a(this.b, ".firstregister", 1);
            int iA2 = n.a(this.b, ".usertype", 0);
            long jA = n.a(this.b, ".installtime", 0L);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jA == 0) {
                n.b(this.b, ".installtime", jCurrentTimeMillis);
            } else if (iA2 != 0 || iA == 1 || t.a(jA).equals(t.a(System.currentTimeMillis()))) {
                jCurrentTimeMillis = jA;
            } else {
                n.b(this.b, ".usertype", 1);
                jCurrentTimeMillis = jA;
                iA2 = 1;
            }
            jSONObject.put("ut", iA2);
            if (iA == 1) {
                jSONObject.put("freg", 1);
            }
            jSONObject.put(AdvanceSetting.NETWORK_TYPE, (int) (jCurrentTimeMillis / 1000));
            if (t.b(this.b)) {
                jSONObject.put("aidl", 1);
            }
            jSONObject.put(PushConstants.PUSH_TYPE, i2);
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c("RegisterReservedInfo", "toSting", e);
        }
        return jSONObject.toString();
    }
}
