package com.baidu.lbsapi.auth;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c {
    private Context a;
    private HashMap<String, String> b = null;
    private a<String> c = null;

    interface a<Result> {
        void a(Result result);
    }

    protected c(Context context) {
        this.a = context;
    }

    private HashMap<String, String> a(HashMap<String, String> map) {
        HashMap<String, String> map2 = new HashMap<>();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String string = it.next().toString();
            map2.put(string, map.get(string));
        }
        return map2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        JSONObject jSONObject;
        if (str == null) {
            str = Constants.MAIN_VERSION_TAG;
        }
        try {
            jSONObject = new JSONObject(str);
            if (!jSONObject.has("status")) {
                jSONObject.put("status", -1);
            }
        } catch (JSONException e) {
            jSONObject = new JSONObject();
            try {
                jSONObject.put("status", -1);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (this.c != null) {
            this.c.a(jSONObject != null ? jSONObject.toString() : new JSONObject().toString());
        }
    }

    protected void a(HashMap<String, String> map, a<String> aVar) {
        this.b = a(map);
        this.c = aVar;
        new Thread(new d(this)).start();
    }
}
