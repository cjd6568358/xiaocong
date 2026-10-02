package com.alibaba.mtl.appmonitor.b;

import android.content.Context;
import com.alibaba.mtl.appmonitor.SdkMeta;
import com.alibaba.mtl.appmonitor.a.f;
import com.alibaba.mtl.appmonitor.a.h;
import com.alibaba.mtl.appmonitor.c.d;
import com.alibaba.mtl.appmonitor.c.e;
import com.alibaba.mtl.appmonitor.f.c;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: compiled from: ExceptionEventBuilder.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class b {
    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static void m15a(Context context, Throwable th) {
        if (th != null) {
            try {
                h hVar = (h) com.alibaba.mtl.appmonitor.c.a.a().a(h.class, new Object[0]);
                hVar.e = f.ALARM.m12a();
                HashMap map = new HashMap();
                map.put("meta", SdkMeta.getSDKMetaData());
                d dVar = (d) com.alibaba.mtl.appmonitor.c.a.a().a(d.class, new Object[0]);
                dVar.put(a(context, th));
                map.put("data", dVar);
                hVar.n.put(f.ALARM.m13a(), new JSONObject(map).toString());
                hVar.v = "APPMONITOR";
                hVar.w = "sdk-exception";
                c.a(hVar);
                com.alibaba.mtl.appmonitor.c.a.a().a(dVar);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    /* JADX INFO: renamed from: a, reason: collision with other method in class */
    public static void m16a(Throwable th) {
        m15a((Context) null, th);
    }

    private static JSONObject a(Context context, Throwable th) throws IOException {
        JSONObject jSONObject = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(e.class, new Object[0]);
        if (context != null) {
            try {
                jSONObject.put("pname", com.alibaba.mtl.log.e.b.a(context));
            } catch (Exception e) {
            }
        }
        jSONObject.put("page", "APPMONITOR");
        jSONObject.put("monitorPoint", "sdk-exception");
        jSONObject.put("arg", th.getClass().getSimpleName());
        jSONObject.put("successCount", 0);
        jSONObject.put("failCount", 1);
        ArrayList arrayList = new ArrayList();
        String strA = a(th);
        if (strA != null) {
            JSONObject jSONObject2 = (JSONObject) com.alibaba.mtl.appmonitor.c.a.a().a(e.class, new Object[0]);
            jSONObject2.put("errorCode", strA);
            jSONObject2.put("errorCount", 1);
            arrayList.add(jSONObject2);
        }
        jSONObject.put("errors", arrayList);
        return jSONObject;
    }

    private static String a(Throwable th) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(th.getClass().getName());
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement.toString());
            }
        }
        String string = sb.toString();
        if (com.alibaba.mtl.appmonitor.f.b.isBlank(string)) {
            return th.toString();
        }
        return string;
    }
}
