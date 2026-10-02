package com.tencent.wxop.stat.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class r {
    private static String a = "";

    public static String a(Context context) {
        try {
            if (a(context, "android.permission.READ_PHONE_STATE")) {
                String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                if (deviceId != null) {
                    return deviceId;
                }
            } else {
                Log.e(StatConstants.LOG_TAG, "Could not get permission of android.permission.READ_PHONE_STATE");
            }
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "get device id error", th);
        }
        return null;
    }

    public static String a(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(f.b(h.a(str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET), 0)), AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "decode error", th);
            return str;
        }
    }

    public static JSONArray a(Context context, int i) {
        return null;
    }

    public static void a(JSONObject jSONObject, String str, String str2) {
        if (str2 != null) {
            try {
                if (str2.length() > 0) {
                    jSONObject.put(str, str2);
                }
            } catch (Throwable th) {
                Log.e(StatConstants.LOG_TAG, "jsonPut error", th);
            }
        }
    }

    public static boolean a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "checkPermission error", th);
            return false;
        }
    }

    public static String b(Context context) {
        if (!a(context, "android.permission.ACCESS_WIFI_STATE")) {
            Log.e(StatConstants.LOG_TAG, "Could not get permission of android.permission.ACCESS_WIFI_STATE");
            return "";
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(NetworkUtils.NETWORKTYPE_WIFI);
            return wifiManager == null ? "" : wifiManager.getConnectionInfo().getMacAddress();
        } catch (Exception e) {
            Log.e(StatConstants.LOG_TAG, "get wifi address error", e);
            return "";
        }
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 8) {
            return str;
        }
        try {
            return new String(h.b(f.a(str.getBytes(AsyncHttpResponseHandler.DEFAULT_CHARSET)), 0), AsyncHttpResponseHandler.DEFAULT_CHARSET);
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "encode error", th);
            return str;
        }
    }

    public static WifiInfo c(Context context) {
        WifiManager wifiManager;
        if (!a(context, "android.permission.ACCESS_WIFI_STATE") || (wifiManager = (WifiManager) context.getApplicationContext().getSystemService(NetworkUtils.NETWORKTYPE_WIFI)) == null) {
            return null;
        }
        return wifiManager.getConnectionInfo();
    }

    public static String d(Context context) {
        try {
            WifiInfo wifiInfoC = c(context);
            if (wifiInfoC != null) {
                return wifiInfoC.getBSSID();
            }
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "encode error", th);
        }
        return null;
    }

    public static String e(Context context) {
        try {
            WifiInfo wifiInfoC = c(context);
            if (wifiInfoC != null) {
                return wifiInfoC.getSSID();
            }
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "encode error", th);
        }
        return null;
    }

    public static boolean f(Context context) {
        try {
            if (a(context, "android.permission.INTERNET") && a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                        return true;
                    }
                    Log.w(StatConstants.LOG_TAG, "Network error");
                    return false;
                }
            } else {
                Log.e(StatConstants.LOG_TAG, "can not get the permisson of android.permission.INTERNET");
            }
        } catch (Throwable th) {
            Log.e(StatConstants.LOG_TAG, "isNetworkAvailable error", th);
        }
        return false;
    }
}
