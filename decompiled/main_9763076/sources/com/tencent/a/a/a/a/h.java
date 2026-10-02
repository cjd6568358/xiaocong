package com.tencent.a.a.a.a;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class h {
    static String a(Context context) {
        try {
            if (a(context, "android.permission.READ_PHONE_STATE")) {
                String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                if (deviceId != null) {
                    return deviceId;
                }
            } else {
                Log.i("MID", "Could not get permission of android.permission.READ_PHONE_STATE");
            }
        } catch (Throwable th) {
            Log.w("MID", th);
        }
        return Constants.MAIN_VERSION_TAG;
    }

    private static void a(String str, Throwable th) {
        Log.e("MID", str, th);
    }

    static void a(JSONObject jSONObject, String str, String str2) throws JSONException {
        if (b(str2)) {
            jSONObject.put(str, str2);
        }
    }

    static boolean a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            a("checkPermission error", th);
            return false;
        }
    }

    static String b(Context context) {
        if (!a(context, "android.permission.ACCESS_WIFI_STATE")) {
            Log.i("MID", "Could not get permission of android.permission.ACCESS_WIFI_STATE");
            return Constants.MAIN_VERSION_TAG;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            return wifiManager == null ? Constants.MAIN_VERSION_TAG : wifiManager.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            Log.i("MID", "get wifi address error" + e);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    static boolean b(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static boolean c(String str) {
        return str != null && str.trim().length() >= 40;
    }

    static String f(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(com.tencent.wxop.stat.common.f.b(Base64.decode(str.getBytes(HTTP.UTF_8), 0)), HTTP.UTF_8).trim().replace("\t", Constants.MAIN_VERSION_TAG).replace("\n", Constants.MAIN_VERSION_TAG).replace("\r", Constants.MAIN_VERSION_TAG);
        } catch (Throwable th) {
            a("decode error", th);
            return str;
        }
    }

    static String g(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(Base64.encode(com.tencent.wxop.stat.common.f.a(str.getBytes(HTTP.UTF_8)), 0), HTTP.UTF_8).trim().replace("\t", Constants.MAIN_VERSION_TAG).replace("\n", Constants.MAIN_VERSION_TAG).replace("\r", Constants.MAIN_VERSION_TAG);
        } catch (Throwable th) {
            a("decode error", th);
            return str;
        }
    }
}
