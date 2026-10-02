package com.baidu.mobstat;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class v {
    public static JSONObject a(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NotifyType.SOUND, Build.VERSION.SDK_INT);
            jSONObject.put("sv", Build.VERSION.RELEASE);
            jSONObject.put("ii", de.a(2, context));
            jSONObject.put("w", de.b(context));
            jSONObject.put("h", de.c(context));
            jSONObject.put("ly", bc.c);
            jSONObject.put("pv", "14");
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                jSONObject.put("pn", de.h(2, context));
                jSONObject.put("a", packageInfo.versionCode);
                jSONObject.put("n", packageInfo.versionName);
            } catch (Exception e) {
                bd.a(e);
            }
            jSONObject.put("mc", de.b(2, context));
            jSONObject.put("bm", de.f(2, context));
            jSONObject.put("m", Build.MODEL);
            jSONObject.put("dn", de.a(context, 2));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("failed_cnt", 0);
            jSONObject2.put("send_index", 0);
            String strB = de.b();
            if (strB == null) {
                strB = Constants.MAIN_VERSION_TAG;
            }
            jSONObject2.put("rom", strB);
            jSONObject.put("trace", jSONObject2);
        } catch (JSONException e2) {
            bd.b(e2);
        }
        return jSONObject;
    }

    public static JSONObject a(JSONObject jSONObject) {
        JSONObject jSONObject2;
        if (jSONObject == null) {
            return null;
        }
        try {
            JSONArray jSONArray = (JSONArray) jSONObject.get("payload");
            JSONObject jSONObject3 = (jSONArray == null || jSONArray.length() <= 0) ? null : (JSONObject) jSONArray.get(0);
            jSONObject2 = jSONObject3 != null ? jSONObject3.getJSONObject("he") : null;
        } catch (Exception e) {
            jSONObject2 = null;
        }
        return jSONObject2;
    }

    public static void b(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("trace");
            jSONObject2.put("failed_cnt", jSONObject2.getLong("failed_cnt") + 1);
        } catch (Exception e) {
        }
    }

    public static void c(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("trace");
            jSONObject2.put("send_index", jSONObject2.getLong("send_index") + 1);
        } catch (Exception e) {
        }
    }
}
