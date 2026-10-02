package com.baidu.mobstat;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.tencent.android.tpush.common.Constants;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class bl implements Thread.UncaughtExceptionHandler {
    private static final bl a = new bl();
    private Thread.UncaughtExceptionHandler b = null;
    private Context c = null;
    private bu d = new bu();

    public static bl a() {
        return a;
    }

    private bl() {
    }

    public void a(Context context) {
        if (this.b == null) {
            this.b = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
        if (this.c == null) {
            this.c = context.getApplicationContext();
        }
        this.d.a(this.c);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) throws Throwable {
        String string = th.toString();
        String str = Constants.MAIN_VERSION_TAG;
        if (string != null && !string.equals(Constants.MAIN_VERSION_TAG)) {
            try {
                str = string.length() > 1 ? string.split(":")[0] : string;
            } catch (Exception e) {
                db.c(e);
                str = Constants.MAIN_VERSION_TAG;
            }
        }
        if (str == null || str.equals(Constants.MAIN_VERSION_TAG)) {
            str = string;
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        String string2 = stringWriter.toString();
        db.a(string2);
        a(System.currentTimeMillis(), string2, str, 0);
        if (!this.b.equals(this)) {
            this.b.uncaughtException(thread, th);
        }
    }

    public void a(long j, String str, String str2, int i) throws Throwable {
        ch.a().b(this.c, System.currentTimeMillis());
        if (this.c != null && str != null && !str.trim().equals(Constants.MAIN_VERSION_TAG)) {
            try {
                String appVersionName = CooperService.a().getAppVersionName(this.c);
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("t", j);
                jSONObject.put("c", str);
                jSONObject.put("y", str2);
                jSONObject.put(NotifyType.VIBRATE, appVersionName);
                jSONObject.put("ct", i);
                jSONObject.put("mem", c());
                JSONArray jSONArray = new JSONArray();
                jSONArray.put(jSONObject);
                JSONObject jSONObject2 = new JSONObject();
                this.d.a(this.c, jSONObject2);
                jSONObject2.put("ss", 0);
                jSONObject2.put("sq", 0);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("he", jSONObject2);
                jSONObject3.put("pr", new JSONArray());
                jSONObject3.put("ev", new JSONArray());
                jSONObject3.put("ex", jSONArray);
                jSONObject3.put("trace", b());
                cu.a(this.c, "__send_data_" + System.currentTimeMillis(), jSONObject3.toString(), false);
                db.a("Dump exception successlly");
            } catch (Exception e) {
                db.b(e);
            }
        }
    }

    private JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("app_session", 0);
        } catch (Exception e) {
        }
        try {
            jSONObject.put("failed_cnt", 0);
        } catch (Exception e2) {
        }
        return jSONObject;
    }

    @SuppressLint({"NewApi"})
    private JSONObject c() {
        ActivityManager activityManager = (ActivityManager) this.c.getSystemService("activity");
        if (activityManager == null) {
            return null;
        }
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        JSONObject jSONObject = new JSONObject();
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                jSONObject.put("total", memoryInfo.totalMem);
            }
            jSONObject.put("free", memoryInfo.availMem);
            jSONObject.put("low", memoryInfo.lowMemory ? 1 : 0);
            return jSONObject;
        } catch (Exception e) {
            return jSONObject;
        }
    }
}
