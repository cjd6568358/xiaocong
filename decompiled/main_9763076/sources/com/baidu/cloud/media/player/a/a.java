package com.baidu.cloud.media.player.a;

import android.content.Context;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.util.Random;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a {
    private String a;
    private String b;
    private String c = "2.2.1";
    private String d = "sw";
    private String e = Constants.MAIN_VERSION_TAG;
    private e f;
    private f g;
    private c h;
    private JSONObject i;

    public a(Context context) {
        this.f = new e(context);
        this.g = new f(context);
        this.h = new c(context);
    }

    private String a(int i) {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26)));
        }
        return stringBuffer.toString();
    }

    private String c() {
        return (Constants.MAIN_VERSION_TAG + System.currentTimeMillis()) + a(8);
    }

    public JSONObject a() {
        if (this.i == null) {
            this.i = new JSONObject();
            try {
                this.i.put("url", this.a);
                this.i.put("vvid", this.b);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("playerVersion", this.c);
                jSONObject.put("decodeMode", this.d);
                jSONObject.put("ak", this.e);
                this.i.put("env", jSONObject);
            } catch (Exception e) {
                Log.d("BaseInfo", Constants.MAIN_VERSION_TAG + e.getMessage());
            }
        }
        return this.i;
    }

    public void a(String str) {
        if (str == null || str.equals(Constants.MAIN_VERSION_TAG)) {
            return;
        }
        this.a = str;
        this.b = c();
        Log.i("BaseInfo", "Current vvid is:" + this.b);
        this.i = null;
    }

    public void a(String str, String str2, String str3) {
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.i = null;
    }

    public String b() {
        return this.b;
    }
}
