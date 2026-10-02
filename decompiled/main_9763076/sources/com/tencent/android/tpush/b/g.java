package com.tencent.android.tpush.b;

import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.t;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    public int a = 1;
    public String b = Constants.MAIN_VERSION_TAG;
    public h c = new h();
    public String d = Constants.MAIN_VERSION_TAG;
    public String e = Constants.MAIN_VERSION_TAG;
    public String f = Constants.MAIN_VERSION_TAG;
    public int g = 0;
    public String h = Constants.MAIN_VERSION_TAG;
    public String i = Constants.MAIN_VERSION_TAG;
    public String j = Constants.MAIN_VERSION_TAG;

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull(Constants.FLAG_ACTION_TYPE)) {
            this.a = jSONObject.getInt(Constants.FLAG_ACTION_TYPE);
        }
        if (!jSONObject.isNull("activity")) {
            this.b = jSONObject.getString("activity");
        }
        if (!jSONObject.isNull("aty_attr")) {
            String strOptString = jSONObject.optString("aty_attr");
            if (!t.c(strOptString)) {
                try {
                    JSONObject jSONObject2 = new JSONObject(strOptString);
                    this.c.a = jSONObject2.optInt("if");
                    this.c.b = jSONObject2.optInt("pf");
                } catch (Exception e) {
                    com.tencent.android.tpush.a.a.c(Constants.LogTag, "decode activityAttribute error", e);
                }
            }
        }
        if (!jSONObject.isNull("intent")) {
            this.d = jSONObject.getString("intent");
        }
        if (!jSONObject.isNull("browser")) {
            this.e = jSONObject.getString("browser");
            JSONObject jSONObject3 = new JSONObject(this.e);
            if (!jSONObject3.isNull("url")) {
                this.f = jSONObject3.getString("url");
            }
            if (!jSONObject3.isNull("confirm")) {
                this.g = jSONObject3.getInt("confirm");
            }
        }
        if (!jSONObject.isNull("package_name")) {
            this.i = jSONObject.getString("package_name");
            JSONObject jSONObject4 = new JSONObject(this.i);
            if (!jSONObject4.isNull(Constants.FLAG_PACKAGE_DOWNLOAD_URL)) {
                this.j = jSONObject4.getString(Constants.FLAG_PACKAGE_DOWNLOAD_URL);
            }
            if (!jSONObject4.isNull(Constants.FLAG_PACKAGE_NAME)) {
                this.h = jSONObject4.getString(Constants.FLAG_PACKAGE_NAME);
            }
            if (!jSONObject4.isNull("confirm")) {
                this.g = jSONObject4.getInt("confirm");
            }
        }
    }
}
