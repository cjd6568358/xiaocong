package com.tencent.mid.util;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class j {
    private static String a = null;
    private static String b = null;
    private static String c = null;
    private static f d = Util.getLogger();
    private static String e = null;
    private static k f = null;
    private static m g = null;

    public static String a() {
        long jB = b() / 1000000;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return String.valueOf((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1000000) + "/" + String.valueOf(jB);
    }

    public static String a(Context context) {
        if (c == null || Constants.MAIN_VERSION_TAG == c) {
            c = b(context);
        }
        return c;
    }

    public static long b() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0038, code lost:
    
        r0 = com.tencent.android.tpush.common.Constants.MAIN_VERSION_TAG;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String b(Context context) {
        String hostAddress;
        try {
            if (Util.checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddressNextElement = inetAddresses.nextElement();
                        if (!inetAddressNextElement.isLoopbackAddress()) {
                            hostAddress = inetAddressNextElement.getHostAddress();
                            if (!InetAddressUtils.isIPv4Address(hostAddress)) {
                            }
                        }
                    }
                }
                hostAddress = Constants.MAIN_VERSION_TAG;
            } else {
                d.c("Can not get the permission of android.permission.ACCESS_WIFI_STATE");
                hostAddress = Constants.MAIN_VERSION_TAG;
            }
        } catch (Exception e2) {
            d.b(e2);
            hostAddress = Constants.MAIN_VERSION_TAG;
        }
        return hostAddress;
    }

    public static DisplayMetrics c(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private static long d() throws Throwable {
        BufferedReader bufferedReader;
        long jIntValue = 0;
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            try {
                jIntValue = Integer.valueOf(bufferedReader.readLine().split("\\s+")[1]).intValue() * WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e2) {
                    }
                }
            } catch (IOException e3) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e4) {
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception e5) {
                    }
                }
                throw th;
            }
        } catch (IOException e6) {
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
        }
        return jIntValue;
    }

    public static String d(Context context) {
        try {
            if (!Util.checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                d.f("Could not get permission of android.permission.READ_PHONE_STATE");
                return null;
            }
            if (!f(context)) {
                return null;
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            return telephonyManager != null ? telephonyManager.getSimOperator() : null;
        } catch (Throwable th) {
            d.f(th);
            return null;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static String e(Context context) {
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return str == null ? Constants.MAIN_VERSION_TAG : str;
        } catch (Throwable th) {
            d.f(th);
            return Constants.MAIN_VERSION_TAG;
        }
    }

    public static boolean f(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }

    public static String g(Context context) {
        try {
            if (Util.checkPermission(context, "android.permission.INTERNET") && Util.checkPermission(context, "android.permission.ACCESS_NETWORK_STATE")) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                    String typeName = activeNetworkInfo.getTypeName();
                    String extraInfo = activeNetworkInfo.getExtraInfo();
                    if (typeName != null) {
                        if (typeName.equalsIgnoreCase("WIFI")) {
                            return "WIFI";
                        }
                        if (typeName.equalsIgnoreCase("MOBILE")) {
                            return extraInfo == null ? "MOBILE" : extraInfo;
                        }
                        return extraInfo == null ? typeName : extraInfo;
                    }
                }
            } else {
                d.f("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            }
        } catch (Throwable th) {
            d.f(th);
        }
        return null;
    }

    public static Integer h(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static int i(Context context) {
        try {
            return n.a() ? 1 : 0;
        } catch (Throwable th) {
            d.f(th);
        }
    }

    public static String j(Context context) {
        String path;
        String str = null;
        try {
            if (Util.checkPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                String externalStorageState = Environment.getExternalStorageState();
                if (externalStorageState != null && externalStorageState.equals("mounted") && (path = Environment.getExternalStorageDirectory().getPath()) != null) {
                    StatFs statFs = new StatFs(path);
                    str = String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + "/" + String.valueOf((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000);
                }
            } else {
                d.c("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
            }
        } catch (Throwable th) {
            d.f(th);
        }
        return str;
    }

    public static String k(Context context) {
        return String.valueOf(o(context) / 1000000) + "/" + String.valueOf(d() / 1000000);
    }

    public static synchronized k l(Context context) {
        if (f == null) {
            f = new k();
        }
        return f;
    }

    public static JSONObject m(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            l(context);
            int iB = k.b();
            if (iB > 0) {
                jSONObject.put("fx", iB / 1000000);
            }
            l(context);
            int iC = k.c();
            if (iC > 0) {
                jSONObject.put("fn", iC / 1000000);
            }
            l(context);
            int iA = k.a();
            if (iA > 0) {
                jSONObject.put("n", iA);
            }
            l(context);
            String strD = k.d();
            if (strD != null && strD.length() == 0) {
                l(context);
                jSONObject.put("na", k.d());
            }
        } catch (Throwable th) {
            d.f(th);
        }
        return jSONObject;
    }

    public static String n(Context context) {
        List<Sensor> sensorList;
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager != null && (sensorList = sensorManager.getSensorList(-1)) != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < sensorList.size(); i++) {
                    sb.append(sensorList.get(i).getType());
                    if (i != sensorList.size() - 1) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
        } catch (Throwable th) {
            d.f(th);
        }
        return Constants.MAIN_VERSION_TAG;
    }

    private static long o(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }
}
