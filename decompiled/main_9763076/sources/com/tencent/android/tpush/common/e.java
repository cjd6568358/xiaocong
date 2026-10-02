package com.tencent.android.tpush.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import bsh.ParserConstants;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import java.io.Closeable;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    public static boolean a(JSONObject jSONObject, String str, Object obj) {
        if (obj != null) {
            try {
                jSONObject.put(str, obj);
                return true;
            } catch (JSONException e) {
            }
        }
        return false;
    }

    public static Object b(JSONObject jSONObject, String str, Object obj) {
        try {
            if (jSONObject.has(str)) {
                return jSONObject.get(str);
            }
            return obj;
        } catch (JSONException e) {
            return obj;
        }
    }

    public static Object a(Context context, String str, Object obj) {
        Object obj2;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), ParserConstants.LSHIFTASSIGN);
            return (applicationInfo == null || (obj2 = applicationInfo.metaData.get(str)) == null) ? obj : obj2;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, Constants.MAIN_VERSION_TAG, th);
            return obj;
        }
    }

    public static JSONArray a(Context context, int i) {
        List<ScanResult> scanResults;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager != null && (scanResults = wifiManager.getScanResults()) != null && scanResults.size() > 0) {
                Collections.sort(scanResults, new f());
                JSONArray jSONArray = new JSONArray();
                int i2 = 0;
                while (true) {
                    int i3 = i2;
                    if (i3 >= scanResults.size() || i3 >= i) {
                        break;
                    }
                    ScanResult scanResult = scanResults.get(i3);
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(NotificationStyle.BASE_STYLE, scanResult.BSSID);
                    jSONObject.put("ss", scanResult.SSID);
                    jSONArray.put(jSONObject);
                    i2 = i3 + 1;
                }
                return jSONArray;
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "getMetaData", th);
        }
        return null;
    }

    public static boolean a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                return true;
            } catch (Exception e) {
            }
        }
        return false;
    }
}
