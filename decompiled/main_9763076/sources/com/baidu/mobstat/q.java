package com.baidu.mobstat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class q {
    static q a = new q();

    q() {
    }

    public void a(Context context, String str, String str2) {
        String strA;
        PackageManager packageManager = context.getPackageManager();
        String str3 = "unkown";
        if (!"android.intent.action.PACKAGE_REMOVED".equals(str)) {
            try {
                str3 = packageManager.getPackageInfo(str2, 8192).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                bd.a(e);
            }
        }
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("n", str2);
            jSONObject.put("a", str);
            jSONObject.put(NotifyType.VIBRATE, str3);
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(jSONObject);
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("app_change", jSONArray);
            jSONObject2.put("meta-data", sb.toString());
            strA = cs.a(jSONObject2.toString().getBytes());
        } catch (Exception e2) {
            bd.b(e2.getMessage());
            strA = Constants.MAIN_VERSION_TAG;
        }
        if (!TextUtils.isEmpty(strA)) {
            y.d.a(System.currentTimeMillis(), strA);
        }
    }
}
