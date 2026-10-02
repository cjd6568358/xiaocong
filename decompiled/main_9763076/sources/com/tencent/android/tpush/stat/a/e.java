package com.tencent.android.tpush.stat.a;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.data.RegisterEntity;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.e.m;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import org.apache.http.HttpHost;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class e {
    private static String a = null;
    private static String b = null;
    private static String c = null;
    private static Random d = null;
    private static Map e = new HashMap(10);
    private static DisplayMetrics f = null;
    private static String g = null;
    private static String h = Constants.MAIN_VERSION_TAG;
    private static f i = null;
    private static String j = null;
    private static String k = null;
    private static String l = null;
    private static long m = -1;
    private static long n = -1;
    private static int o = 0;
    private static String p = "__MTA_FIRST_ACTIVATE__";
    private static int q = -1;

    private static synchronized Random f() {
        if (d == null) {
            d = new Random();
        }
        return d;
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0065 -> B:22:0x0018). Please report as a decompilation issue!!! */
    public static String a(Context context, long j2) {
        String str;
        List listD;
        RegisterEntity registerInfoByPkgName;
        try {
            if (e.containsKey(Long.valueOf(j2))) {
                str = (String) e.get(Long.valueOf(j2));
            } else if (context != null && (listD = m.d(context)) != null) {
                Iterator it = listD.iterator();
                while (it.hasNext()) {
                    String str2 = ((ResolveInfo) it.next()).activityInfo.packageName;
                    if (str2 != null && (registerInfoByPkgName = CacheManager.getRegisterInfoByPkgName(str2)) != null && registerInfoByPkgName.accessId == j2) {
                        str = registerInfoByPkgName.xgSDKVersion + Constants.MAIN_VERSION_TAG;
                        e.put(Long.valueOf(registerInfoByPkgName.accessId), str);
                    }
                }
                str = PushConstants.PUSH_TYPE_NOTIFY;
            } else {
                str = PushConstants.PUSH_TYPE_NOTIFY;
            }
        } catch (Throwable th) {
        }
        return str;
    }

    public static String a(Context context) {
        try {
            return String.valueOf(3.24f);
        } catch (Throwable th) {
            return PushConstants.PUSH_TYPE_NOTIFY;
        }
    }

    public static int a() {
        return f().nextInt(Integer.MAX_VALUE);
    }

    public static byte[] a(byte[] bArr) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
        byte[] bArr2 = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length * 2);
        while (true) {
            int i2 = gZIPInputStream.read(bArr2);
            if (i2 != -1) {
                byteArrayOutputStream.write(bArr2, 0, i2);
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayInputStream.close();
                gZIPInputStream.close();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    public static HttpHost b(Context context) {
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
            } else if (extraInfo.equals("ctwap")) {
                httpHost = new HttpHost("10.0.0.200", 80);
            } else {
                String defaultHost = Proxy.getDefaultHost();
                if (defaultHost != null && defaultHost.trim().length() > 0) {
                    httpHost = new HttpHost(defaultHost, Proxy.getDefaultPort());
                }
                return null;
            }
            return httpHost;
        } catch (Throwable th) {
            i.b(th);
        }
    }

    public static DisplayMetrics c(Context context) {
        if (f == null) {
            f = new DisplayMetrics();
            ((WindowManager) context.getApplicationContext().getSystemService("window")).getDefaultDisplay().getMetrics(f);
        }
        return f;
    }

    public static String d(Context context) {
        TelephonyManager telephonyManager;
        if (g != null) {
            return g;
        }
        try {
            if (e(context) && (telephonyManager = (TelephonyManager) context.getSystemService("phone")) != null) {
                g = telephonyManager.getSimOperator();
            }
        } catch (Throwable th) {
            i.b(th);
        }
        return g;
    }

    public static String b(Context context, long j2) {
        return com.tencent.android.tpush.service.e.e.a(context).a(j2);
    }

    public static boolean e(Context context) {
        return context.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x003f  */
    /* JADX WARN: Code duplicated, block: B:22:0x004e A[PHI: r2
  0x004e: PHI (r2v4 int) = (r2v12 int), (r2v13 int) binds: [B:19:0x0042, B:21:0x004c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:25:0x0056 A[PHI: r2
  0x0056: PHI (r2v2 java.lang.String) = (r2v14 java.lang.String), (r2v15 java.lang.String), (r2v16 java.lang.String), (r2v17 java.lang.String) binds: [B:24:0x0051, B:4:0x000e, B:6:0x0014, B:8:0x001e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0056 -> B:26:0x002a). Please report as a decompilation issue!!! */
    public static String f(Context context) {
        String str;
        String str2;
        int i2;
        String str3 = Constants.MAIN_VERSION_TAG;
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            str = str3;
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                str = str3;
                str = str3;
                str = str3;
                str2 = str;
                str3 = str;
            } else {
                String typeName = activeNetworkInfo.getTypeName();
                String extraInfo = activeNetworkInfo.getExtraInfo();
                if (typeName == null) {
                    str = str3;
                    str = str3;
                    str = str3;
                    str2 = str;
                    str3 = str;
                } else if (typeName.equalsIgnoreCase("WIFI")) {
                    str = str3;
                    str = str3;
                    str2 = "WIFI";
                    str3 = str3;
                } else if (typeName.equalsIgnoreCase("MOBILE")) {
                    if (extraInfo != null) {
                        int length = extraInfo.trim().length();
                        str2 = extraInfo;
                        str3 = str3;
                        if (length <= 0) {
                            str2 = "MOBILE";
                            str3 = str3;
                        }
                    } else {
                        str2 = "MOBILE";
                        str3 = str3;
                    }
                } else if (extraInfo != null) {
                    int length2 = extraInfo.trim().length();
                    str2 = extraInfo;
                    str3 = length2;
                    i2 = length2;
                    if (length2 <= 0) {
                        str2 = typeName;
                        str3 = i2;
                    }
                } else {
                    str2 = typeName;
                    str3 = i2;
                }
            }
        } catch (Throwable th) {
            i.b(th);
            str = str3;
        }
        return str2;
    }

    public static Integer g(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                return Integer.valueOf(telephonyManager.getNetworkType());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static synchronized f b() {
        if (i == null) {
            i = new f("XgStat");
            i.a(true);
        }
        return i;
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
            i.b(th);
            return System.currentTimeMillis() + 86400000;
        }
    }

    public static Long a(String str, String str2, int i2, int i3, Long l2) {
        if (str != null && str2 != null) {
            if (str2.equalsIgnoreCase(".") || str2.equalsIgnoreCase("|")) {
                str2 = "\\" + str2;
            }
            String[] strArrSplit = str.split(str2);
            if (strArrSplit.length == i3) {
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
            return l2;
        }
        return l2;
    }

    public static long a(String str) {
        return a(str, ".", 100, 3, 0L).longValue();
    }

    public static boolean b(String str) {
        return (str == null || str.trim().length() == 0) ? false : true;
    }

    public static String h(Context context) {
        String path;
        if (b(j)) {
            return j;
        }
        try {
            String externalStorageState = Environment.getExternalStorageState();
            if (externalStorageState != null && externalStorageState.equals("mounted") && (path = Environment.getExternalStorageDirectory().getPath()) != null) {
                StatFs statFs = new StatFs(path);
                j = String.valueOf((((long) statFs.getBlockSize()) * ((long) statFs.getAvailableBlocks())) / 1000000) + "/" + String.valueOf((((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize())) / 1000000);
                return j;
            }
        } catch (Throwable th) {
            i.b(th);
        }
        return null;
    }

    public static String i(Context context) {
        try {
            if (k != null) {
                return k;
            }
            int iMyPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == iMyPid) {
                    k = runningAppProcessInfo.processName;
                    break;
                }
            }
            return k;
        } catch (Throwable th) {
        }
    }

    public static String a(Context context, String str) {
        if (com.tencent.android.tpush.stat.c.e()) {
            if (k == null) {
                k = i(context);
            }
            if (k != null) {
                return str + "_" + k;
            }
            return str;
        }
        return str;
    }

    public static String d() {
        if (b(l)) {
            return l;
        }
        long jE = e() / 1000000;
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        l = String.valueOf((((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize())) / 1000000) + "/" + String.valueOf(jE);
        return l;
    }

    public static long e() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize());
    }

    public static String j(Context context) {
        try {
            return String.valueOf(n(context) / 1000000) + "/" + String.valueOf(g() / 1000000);
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static long n(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    private static long g() throws Throwable {
        BufferedReader bufferedReader;
        Throwable th;
        long jIntValue;
        if (m > 0) {
            return m;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            try {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        jIntValue = 1;
                    } else {
                        jIntValue = Integer.valueOf(line.split("\\s+")[1]).intValue() * WXMediaMessage.DESCRIPTION_LENGTH_LIMIT;
                    }
                    try {
                        bufferedReader.close();
                        com.tencent.android.tpush.common.e.a(bufferedReader);
                    } catch (Exception e2) {
                        com.tencent.android.tpush.common.e.a(bufferedReader);
                    }
                } catch (Exception e3) {
                    jIntValue = 1;
                }
            } catch (Throwable th2) {
                th = th2;
                com.tencent.android.tpush.common.e.a(bufferedReader);
                throw th;
            }
        } catch (Exception e4) {
            bufferedReader = null;
            jIntValue = 1;
        } catch (Throwable th3) {
            bufferedReader = null;
            th = th3;
        }
        m = jIntValue;
        return m;
    }

    public static boolean k(Context context) {
        if (n < 0) {
            n = g.a(context, "mta.qq.com.checktime", 0L);
        }
        return Math.abs(System.currentTimeMillis() - n) > 86400000;
    }

    public static void l(Context context) {
        n = System.currentTimeMillis();
        g.b(context, "mta.qq.com.checktime", n);
    }

    public static int a(Context context, boolean z) {
        if (z) {
            o = m(context);
        }
        return o;
    }

    public static int m(Context context) {
        return g.a(context, "mta.qq.com.difftime", 0);
    }

    public static void a(Context context, int i2) {
        o = i2;
        g.b(context, "mta.qq.com.difftime", i2);
    }
}
