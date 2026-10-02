package com.tencent.android.tpush.stat.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.meizu.cloud.pushsdk.notification.model.NotificationStyle;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.t;
import java.util.Collections;
import java.util.List;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private static String a = Constants.MAIN_VERSION_TAG;
    private static String b = Constants.MAIN_VERSION_TAG;
    private static String c = Constants.MAIN_VERSION_TAG;

    public static boolean a(Context context, String str) {
        try {
            return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
        } catch (Throwable th) {
            Log.e("XgStat", "checkPermission error", th);
            return false;
        }
    }

    public static String a(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return telephonyManager.getSimOperator();
            }
        } catch (Throwable th) {
            Log.e("XgStat", Constants.MAIN_VERSION_TAG, th);
        }
        return null;
    }

    public static String b(Context context) {
        String path;
        try {
            String externalStorageState = Environment.getExternalStorageState();
            if (externalStorageState != null && externalStorageState.equals("mounted") && (path = Environment.getExternalStorageDirectory().getPath()) != null) {
                StatFs statFs = new StatFs(path);
                return String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + "/" + String.valueOf((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000);
            }
        } catch (Throwable th) {
            Log.e("XgStat", Constants.MAIN_VERSION_TAG, th);
        }
        return null;
    }

    public static DisplayMetrics c(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static String d(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                String typeName = activeNetworkInfo.getTypeName();
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (typeName != null) {
                    if (typeName.equalsIgnoreCase("WIFI")) {
                        return "WIFI";
                    }
                    if (!typeName.equalsIgnoreCase("MOBILE")) {
                        return extraInfo == null ? typeName : extraInfo;
                    }
                    if (extraInfo == null) {
                        return "MOBILE";
                    }
                    return extraInfo;
                }
            }
        } catch (Throwable th) {
            Log.e("XgStat", Constants.MAIN_VERSION_TAG, th);
        }
        return null;
    }

    public static String e(Context context) {
        try {
            if (t.c(a)) {
                a = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
                if (a != null) {
                    return a;
                }
            }
        } catch (SecurityException e) {
            com.tencent.android.tpush.a.a.g("XgStat", "No perrmission to get DeviceId");
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.b("XgStat", "get device id error", th);
        }
        return a;
    }

    public static String f(Context context) {
        try {
            b = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        } catch (Throwable th) {
            Log.e("XgStat", "get wifi address error", th);
        }
        return b;
    }

    public static String a(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 8) {
            try {
                return new String(d.b(Base64.decode(str.getBytes(HTTP.UTF_8), 0)), HTTP.UTF_8);
            } catch (Throwable th) {
                Log.e("XgStat", "decode error", th);
                return str;
            }
        }
        return str;
    }

    public static String b(String str) {
        if (str == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 8) {
            try {
                return new String(Base64.encode(d.a(str.getBytes(HTTP.UTF_8)), 0), HTTP.UTF_8);
            } catch (Throwable th) {
                Log.e("XgStat", "encode error", th);
                return str;
            }
        }
        return str;
    }

    public static void a(JSONObject jSONObject, String str, String str2) {
        if (str2 != null) {
            try {
                if (str2.length() > 0) {
                    jSONObject.put(str, str2);
                }
            } catch (Throwable th) {
                Log.e("XgStat", "jsonPut error", th);
            }
        }
    }

    public static WifiInfo g(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                return wifiManager.getConnectionInfo();
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String h(Context context) {
        try {
            WifiInfo wifiInfoG = g(context);
            if (wifiInfoG != null) {
                return wifiInfoG.getBSSID();
            }
        } catch (Throwable th) {
            Log.e("XgStat", "encode error", th);
        }
        return null;
    }

    public static String i(Context context) {
        try {
            WifiInfo wifiInfoG = g(context);
            if (wifiInfoG != null) {
                return wifiInfoG.getSSID();
            }
        } catch (Throwable th) {
            Log.e("XgStat", "encode error", th);
        }
        return null;
    }

    public static boolean j(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                    return true;
                }
                Log.w("XgStat", "Network error");
                return false;
            }
        } catch (Throwable th) {
            Log.e("XgStat", "isNetworkAvailable error", th);
        }
        return false;
    }

    public static boolean k(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI");
            }
        } catch (Throwable th) {
            Log.e("XgStat", "isWifiNet error", th);
        }
        return false;
    }

    public static JSONArray a(Context context, int i) {
        List<ScanResult> scanResults;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            if (wifiManager != null && (scanResults = wifiManager.getScanResults()) != null && scanResults.size() > 0) {
                Collections.sort(scanResults, new i());
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
            Log.e("XgStat", "isWifiNet error", th);
        }
        return null;
    }
}
