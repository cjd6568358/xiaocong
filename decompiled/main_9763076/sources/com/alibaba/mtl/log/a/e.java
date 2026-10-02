package com.alibaba.mtl.log.a;

import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import com.alibaba.mtl.log.e.r;
import com.tencent.android.tpush.common.Constants;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONObject;

/* JADX INFO: compiled from: SystemConfig.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:32:0x0105 -> B:45:0x00d6). Please report as a decompilation issue!!! */
    public static void i(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject != null && jSONObject.has("SYSTEM")) {
                    i.a("SystemConfig", "server system config ", str);
                    JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("SYSTEM");
                    if (jSONObjectOptJSONObject != null) {
                        try {
                            if (jSONObjectOptJSONObject.has("bg_interval")) {
                                a.f(jSONObjectOptJSONObject.getInt("bg_interval") + Constants.MAIN_VERSION_TAG);
                            }
                        } catch (Throwable th) {
                        }
                        try {
                            if (jSONObjectOptJSONObject.has("fg_interval")) {
                                a.g(jSONObjectOptJSONObject.getInt("fg_interval") + Constants.MAIN_VERSION_TAG);
                            }
                        } catch (Throwable th2) {
                        }
                        i.a("SystemConfig", "UTDC.bSendToNewLogStore:", Boolean.valueOf(com.alibaba.mtl.log.a.r));
                        i.a("SystemConfig", "Config.BACKGROUND_PERIOD:", Long.valueOf(a.b()));
                        i.a("SystemConfig", "Config.FOREGROUND_PERIOD:", Long.valueOf(a.a()));
                        try {
                            if (jSONObjectOptJSONObject.has(ClientCookie.DISCARD_ATTR)) {
                                int i = jSONObjectOptJSONObject.getInt(ClientCookie.DISCARD_ATTR);
                                if (i == 1) {
                                    a.A = true;
                                    com.alibaba.mtl.log.d.a.a().stop();
                                } else if (i == 0) {
                                    a.A = false;
                                    com.alibaba.mtl.log.d.a.a().start();
                                }
                            } else if (a.A) {
                                a.A = false;
                                com.alibaba.mtl.log.d.a.a().start();
                            }
                        } catch (Throwable th3) {
                        }
                        try {
                            if (jSONObjectOptJSONObject.has("cdb") && jSONObjectOptJSONObject.getInt("cdb") > f()) {
                                r.a().b(new Runnable() { // from class: com.alibaba.mtl.log.a.e.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        com.alibaba.mtl.log.c.c.a().clear();
                                    }
                                });
                            }
                        } catch (Throwable th4) {
                        }
                    }
                }
            } catch (Throwable th5) {
                i.a("SystemConfig", "updateconfig", th5);
            }
        }
    }

    public static int f() {
        JSONObject jSONObject;
        String strF = a.f();
        if (TextUtils.isEmpty(strF)) {
            return 0;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(strF);
            if (jSONObject2 == null || !jSONObject2.has("SYSTEM") || (jSONObject = jSONObject2.getJSONObject("SYSTEM")) == null || !jSONObject.has("cdb")) {
                return 0;
            }
            return jSONObject.getInt("cdb");
        } catch (Throwable th) {
            return 0;
        }
    }
}
