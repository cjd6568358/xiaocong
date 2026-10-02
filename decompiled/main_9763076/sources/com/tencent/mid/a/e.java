package com.tencent.mid.a;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    private int a;
    private JSONObject b;

    public e(int i, String str) {
        this.a = -1;
        this.b = null;
        this.a = i;
        try {
            this.b = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int a() {
        return this.a;
    }

    public JSONObject b() {
        return this.b;
    }
}
