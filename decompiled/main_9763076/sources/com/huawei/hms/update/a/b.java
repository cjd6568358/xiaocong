package com.huawei.hms.update.a;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CheckResponse.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b {
    private final String a;
    private String b = Constants.MAIN_VERSION_TAG;
    private String c = Constants.MAIN_VERSION_TAG;

    public b(String str) {
        this.a = str;
    }

    public String a() {
        if (!PushConstants.PUSH_TYPE_NOTIFY.equals(this.b) || this.c == null || this.c.isEmpty()) {
            return null;
        }
        return b(this.c);
    }

    public String toString() {
        try {
            return new JSONObject(this.a).toString(2);
        } catch (JSONException e) {
            return this.a;
        }
    }

    public static b a(String str) {
        b bVar = new b(str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            bVar.b = jSONObject.getString("status");
            if (PushConstants.PUSH_TYPE_NOTIFY.equals(bVar.b)) {
                JSONArray jSONArray = jSONObject.getJSONArray("components");
                if (0 < jSONArray.length()) {
                    bVar.c = jSONArray.getJSONObject(0).getString("url");
                    return bVar;
                }
                return bVar;
            }
            return bVar;
        } catch (JSONException e) {
            com.huawei.hms.support.log.a.d("CheckResponse", "In parseResponse, Failed to parse json for check-update response." + e.getMessage());
            return new b(str);
        }
    }

    private static String b(String str) {
        int i = -1;
        for (int length = str.length(); length > 0 && str.charAt(length - 1) == '/'; length--) {
            i = length;
        }
        return i == -1 ? str + "/" : str.substring(0, i);
    }
}
