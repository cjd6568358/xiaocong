package com.tencent.wxop.stat.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.tencent.wxop.stat.StatConfig;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import org.apache.http.HttpHost;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class l {
    private static String a = null;
    private static String b = null;
    private static String c = null;
    private static String d = null;
    private static Random e = null;
    private static DisplayMetrics f = null;
    private static String g = null;
    private static String h = "";
    private static String i = "";
    private static int j = -1;
    private static StatLogger k = null;
    private static String l = null;
    private static String m = null;
    private static volatile int n = -1;
    private static String o = null;
    private static String p = null;
    private static long q = -1;
    private static String r = "";
    private static o s = null;
    private static String t = "__MTA_FIRST_ACTIVATE__";
    private static int u = -1;
    private static long v = -1;
    private static int w = 0;
    private static String x = "";

    public static String A(Context context) {
        if (context == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveInfoResolveActivity = context.getPackageManager().resolveActivity(intent, 0);
        if (resolveInfoResolveActivity.activityInfo == null || resolveInfoResolveActivity.activityInfo.packageName.equals("android")) {
            return null;
        }
        return resolveInfoResolveActivity.activityInfo.packageName;
    }

    private static long B(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static int a() {
        return g().nextInt(Integer.MAX_VALUE);
    }

    public static int a(Context context, boolean z) {
        if (z) {
            w = y(context);
        }
        return w;
    }

    public static Long a(String str, String str2, int i2, int i3, Long l2) {
        if (str == null || str2 == null) {
            return l2;
        }
        if (str2.equalsIgnoreCase(".") || str2.equalsIgnoreCase("|")) {
            str2 = "\\" + str2;
        }
        String[] strArrSplit = str.split(str2);
        if (strArrSplit.length != i3) {
            return l2;
        }
        try {
            Long l3 = 0L;
            int i4 = 0;
            while (i4 < strArrSplit.length) {
                Long lValueOf = Long.valueOf(((long) i2) * (l3.longValue() + Long.valueOf(strArrSplit[i4]).longValue()));
                i4++;
                l3 = lValueOf;
            }
            return l3;
        } catch (NumberFormatException e2) {
            return l2;
        }
    }

    public static String a(int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.roll(6, i2);
        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }

    public static String a(long j2) {
        return new SimpleDateFormat("yyyyMMdd").format(new Date(j2));
    }

    public static String a(Context context, String str) {
        if (!StatConfig.isEnableConcurrentProcess()) {
            return str;
        }
        if (m == null) {
            m = o(context);
        }
        return m != null ? str + "_" + m : str;
    }

    public static String a(String str) {
        if (str == null) {
            return "0";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            byte[] bArrDigest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b2 : bArrDigest) {
                int i2 = b2 & 255;
                if (i2 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i2));
            }
            return stringBuffer.toString();
        } catch (Throwable th) {
            return "0";
        }
    }

    public static HttpHost a(Context context) {
        NetworkInfo activeNetworkInfo;
        String extraInfo;
        HttpHost httpHost;
        if (context == null) {
            return null;
        }
        try {
            if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0 || (activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo()) == null) {
                httpHost = null;
            } else if ((activeNetworkInfo.getTypeName() != null && activeNetworkInfo.getTypeName().equalsIgnoreCase("WIFI")) || (extraInfo = activeNetworkInfo.getExtraInfo()) == null) {
                httpHost = null;
            } else if (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap")) {
                httpHost = new HttpHost("10.0.0.172", 80);
            } else {
                if (!extraInfo.equals("ctwap")) {
                    String defaultHost = Proxy.getDefaultHost();
                    if (defaultHost != null && defaultHost.trim().length() > 0) {
                        httpHost = new HttpHost(defaultHost, Proxy.getDefaultPort());
                    }
                    return null;
                }
                httpHost = new HttpHost("10.0.0.200", 80);
            }
            return httpHost;
        } catch (Throwable th) {
            k.e(th);
        }
    }

    public static void a(Context context, int i2) {
        w = i2;
        q.b(context, "mta.qq.com.difftime", i2);
    }

    public static boolean a(StatSpecifyReportedInfo statSpecifyReportedInfo) {
        if (statSpecifyReportedInfo == null) {
            return false;
        }
        return c(statSpecifyReportedInfo.getAppKey());
    }

    public static byte[] a(byte[] bArr) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length * 2);
        while (true) {
            int i2 = gZIPInputStream.read(bArr2);
            if (i2 == -1) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayInputStream.close();
                gZIPInputStream.close();
                byteArrayOutputStream.close();
                return byteArray;
            }
            byteArrayOutputStream.write(bArr2, 0, i2);
        }
    }

    public static long b(String str) {
        return a(str, ".", 100, 3, 0L).longValue();
    }

    public static synchronized StatLogger b() {
        if (k == null) {
            StatLogger statLogger = new StatLogger(StatConstants.LOG_TAG);
            k = statLogger;
            statLogger.setDebugEnable(false);
        }
        return k;
    }

    public static synchronized String b(Context context) {
        String str;
        if (a == null || a.trim().length() == 0) {
            String strA = r.a(context);
            a = strA;
            if (strA == null || a.trim().length() == 0) {
                a = Integer.toString(g().nextInt(Integer.MAX_VALUE));
            }
            str = a;
        } else {
            str = a;
        }
        return str;
    }

    public static long c() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            return calendar.getTimeInMillis() + 86400000;
        } catch (Throwable th) {
            k.e(th);
            return System.currentTimeMillis() + 86400000;
        }
    }

    public static synchronized String c(Context context) {
        if (c == null || c.trim().length() == 0) {
            c = r.b(context);
        }
        return c;
    }

    public static boolean c(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static DisplayMetrics d(Context context) {
        if (f == null) {
            f = new DisplayMetrics();
            ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(f);
        }
        return f;
    }

    public static String d() {
        if (c(p)) {
            return p;
        }
        long jE = e() / 1000000;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        String str = String.valueOf((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1000000) + "/" + String.valueOf(jE);
        p = str;
        return str;
    }

    public static long e() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
    }

    public static boolean e(Context context) {
        NetworkInfo[] allNetworkInfo;
        try {
            if (!r.a(context, "android.permission.ACCESS_WIFI_STATE")) {
                k.warn("can not get the permission of android.permission.ACCESS_WIFI_STATE");
                return false;
            }
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            if (connectivityManager != null && (allNetworkInfo = connectivityManager.getAllNetworkInfo()) != null) {
                for (int i2 = 0; i2 < allNetworkInfo.length; i2++) {
                    if (allNetworkInfo[i2].getTypeName().equalsIgnoreCase("WIFI") && allNetworkInfo[i2].isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Throwable th) {
            k.e(th);
        }
    }

    public static String f(Context context) {
        if (context == null) {
            return null;
        }
        return context.getClass().getName();
    }

    public static String g(Context context) {
        TelephonyManager telephonyManager;
        if (g != null) {
            return g;
        }
        try {
            if (!r.a(context, "android.permission.READ_PHONE_STATE")) {
                k.e("Could not get permission of android.permission.READ_PHONE_STATE");
            } else if (i(context) && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null) {
                g = telephonyManager.getSimOperator();
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return g;
    }

    private static synchronized Random g() {
        if (e == null) {
            e = new Random();
        }
        return e;
    }

    private static long h() {
        if (q > 0) {
            return q;
        }
        long jIntValue = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), AsyncHttpClient.DEFAULT_SOCKET_BUFFER_SIZE);
            jIntValue = Integer.valueOf(bufferedReader.readLine().split("\\s+")[1]).intValue() * 1024;
            bufferedReader.close();
        } catch (Exception e2) {
        }
        q = jIntValue;
        return jIntValue;
    }

    public static String h(Context context) {
        if (c(h)) {
            return h;
        }
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            h = str;
            if (str == null) {
                return "";
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return h;
    }

    public static boolean i(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x004e  */
    public static String j(Context context) {
        String typeName;
        String str = "";
        try {
            if (r.a(context, "android.permission.INTERNET") && r.a(context, "android.permission.ACCESS_NETWORK_STATE")) {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                    typeName = "";
                    str = typeName;
                } else {
                    typeName = activeNetworkInfo.getTypeName();
                    String extraInfo = activeNetworkInfo.getExtraInfo();
                    if (typeName == null) {
                        typeName = "";
                    } else if (typeName.equalsIgnoreCase("WIFI")) {
                        str = "WIFI";
                    } else if (typeName.equalsIgnoreCase("MOBILE")) {
                        str = extraInfo != null ? extraInfo : "MOBILE";
                    } else if (extraInfo != null) {
                        str = extraInfo;
                    }
                    str = typeName;
                }
            } else {
                k.e("can not get the permission of android.permission.ACCESS_WIFI_STATE");
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return str;
    }

    public static Integer k(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String l(Context context) {
        if (c(i)) {
            return i;
        }
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            i = str;
            if (str == null || i.length() == 0) {
                return NetworkUtils.NETWORKTYPE_INVALID;
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return i;
    }

    public static int m(Context context) {
        if (j != -1) {
            return j;
        }
        try {
            if (p.a()) {
                j = 1;
            }
        } catch (Throwable th) {
            k.e(th);
        }
        j = 0;
        return 0;
    }

    public static String n(Context context) {
        String str;
        String path;
        if (c(l)) {
            return l;
        }
        try {
            if (r.a(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
                String externalStorageState = Environment.getExternalStorageState();
                if (externalStorageState != null && externalStorageState.equals("mounted") && (path = Environment.getExternalStorageDirectory().getPath()) != null) {
                    StatFs statFs = new StatFs(path);
                    str = String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + "/" + String.valueOf((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000);
                    l = str;
                }
                return null;
            }
            k.warn("can not get the permission of android.permission.WRITE_EXTERNAL_STORAGE");
            str = null;
            return str;
        } catch (Throwable th) {
            k.e(th);
        }
    }

    static String o(Context context) {
        try {
            if (m != null) {
                return m;
            }
            int iMyPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == iMyPid) {
                    m = runningAppProcessInfo.processName;
                    break;
                }
            }
            return m;
        } catch (Throwable th) {
        }
    }

    public static String p(Context context) {
        return a(context, StatConstants.DATABASE_NAME);
    }

    public static synchronized Integer q(Context context) {
        Integer numValueOf;
        synchronized (l.class) {
            if (n <= 0) {
                n = q.a(context, "MTA_EVENT_INDEX", 0);
                q.b(context, "MTA_EVENT_INDEX", n + 1000);
            } else if (n % 1000 == 0) {
                try {
                    q.b(context, "MTA_EVENT_INDEX", n < 2147383647 ? n + 1000 : 0);
                } catch (Throwable th) {
                    k.w(th);
                }
            }
            int i2 = n + 1;
            n = i2;
            numValueOf = Integer.valueOf(i2);
        }
        return numValueOf;
    }

    public static String r(Context context) {
        try {
            return String.valueOf(B(context) / 1000000) + "/" + String.valueOf(h() / 1000000);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public static JSONObject s(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("n", m.a());
            String strD = m.d();
            if (strD != null && strD.length() > 0) {
                jSONObject.put("na", strD);
            }
            int iB = m.b();
            if (iB > 0) {
                jSONObject.put("fx", iB / 1000000);
            }
            int iC = m.c();
            if (iC > 0) {
                jSONObject.put("fn", iC / 1000000);
            }
        } catch (Throwable th) {
            Log.w(StatConstants.LOG_TAG, "get cpu error", th);
        }
        return jSONObject;
    }

    public static String t(Context context) {
        List<Sensor> sensorList;
        if (c(r)) {
            return r;
        }
        try {
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            if (sensorManager != null && (sensorList = sensorManager.getSensorList(-1)) != null) {
                StringBuilder sb = new StringBuilder(sensorList.size() * 10);
                for (int i2 = 0; i2 < sensorList.size(); i2++) {
                    sb.append(sensorList.get(i2).getType());
                    if (i2 != sensorList.size() - 1) {
                        sb.append(",");
                    }
                }
                r = sb.toString();
            }
        } catch (Throwable th) {
            k.e(th);
        }
        return r;
    }

    public static synchronized int u(Context context) {
        int i2;
        if (u != -1) {
            i2 = u;
        } else {
            v(context);
            i2 = u;
        }
        return i2;
    }

    public static void v(Context context) {
        int iA = q.a(context, t, 1);
        u = iA;
        if (iA == 1) {
            q.b(context, t, 0);
        }
    }

    public static boolean w(Context context) {
        if (v < 0) {
            v = q.a(context, "mta.qq.com.checktime", 0L);
        }
        return Math.abs(System.currentTimeMillis() - v) > 86400000;
    }

    public static void x(Context context) {
        v = System.currentTimeMillis();
        q.b(context, "mta.qq.com.checktime", v);
    }

    public static int y(Context context) {
        return q.a(context, "mta.qq.com.difftime", 0);
    }

    public static boolean z(Context context) {
        ActivityManager activityManager;
        if (context != null && (activityManager = (ActivityManager) context.getSystemService("activity")) != null) {
            String packageName = context.getPackageName();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.processName.startsWith(packageName)) {
                    return runningAppProcessInfo.importance == 400;
                }
            }
            return false;
        }
        return false;
    }
}
