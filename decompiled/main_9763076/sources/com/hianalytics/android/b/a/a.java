package com.hianalytics.android.b.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import bsh.ParserConstants;
import com.tencent.android.tpush.common.Constants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DeflaterOutputStream;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class a {
    private static HandlerThread h;
    private static HandlerThread i;
    private static Handler j;
    private static Handler k;
    private static boolean b = true;
    private static Long c = 30L;
    private static Long d = 86400L;
    private static Long e = 1000L;
    private static Long f = 1800L;
    private static int g = Integer.MAX_VALUE;
    static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    static {
        HandlerThread handlerThread = new HandlerThread("HiAnalytics_messageThread");
        h = handlerThread;
        handlerThread.start();
        HandlerThread handlerThread2 = new HandlerThread("HiAnalytics_sessionThread");
        i = handlerThread2;
        handlerThread2.start();
    }

    public static long a(String str) {
        long time = 0;
        try {
            Date date = new SimpleDateFormat("yyyyMMddHHmmss").parse(str);
            if (date != null) {
                time = date.getTime();
            }
        } catch (ParseException e2) {
            e2.toString();
        }
        return time / 1000;
    }

    public static Long a() {
        return c;
    }

    public static String a(Context context) {
        String string;
        Object obj;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), ParserConstants.LSHIFTASSIGN);
            string = (applicationInfo == null || (obj = applicationInfo.metaData.get("APPKEY")) == null) ? Constants.MAIN_VERSION_TAG : obj.toString();
        } catch (Exception e2) {
            e2.getMessage();
        }
        return (string == null || string.trim().length() == 0) ? context.getPackageName() : string;
    }

    public static void a(int i2) {
        g = i2;
    }

    public static void a(Long l) {
        c = l;
    }

    public static void a(boolean z) {
        b = z;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x003b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    public static byte[] a(byte[] bArr) throws Throwable {
        DeflaterOutputStream deflaterOutputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        byte[] byteArray = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
                try {
                    try {
                        deflaterOutputStream.write(bArr);
                        deflaterOutputStream.close();
                        byteArray = byteArrayOutputStream.toByteArray();
                        try {
                            deflaterOutputStream.close();
                            byteArrayOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    } catch (Exception e3) {
                        e = e3;
                        e.printStackTrace();
                        if (deflaterOutputStream != null) {
                            try {
                                deflaterOutputStream.close();
                                byteArrayOutputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (deflaterOutputStream != null) {
                        try {
                            deflaterOutputStream.close();
                            byteArrayOutputStream.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                deflaterOutputStream = null;
            } catch (Throwable th3) {
                deflaterOutputStream = null;
                th = th3;
                if (deflaterOutputStream != null) {
                    deflaterOutputStream.close();
                    byteArrayOutputStream.close();
                }
                throw th;
            }
        } catch (Exception e7) {
            e = e7;
            deflaterOutputStream = null;
            byteArrayOutputStream = null;
        } catch (Throwable th4) {
            deflaterOutputStream = null;
            byteArrayOutputStream = null;
            th = th4;
        }
        return byteArray;
    }

    public static Long b() {
        return d;
    }

    public static String b(Context context) {
        Object obj;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), ParserConstants.LSHIFTASSIGN);
            return (applicationInfo == null || applicationInfo.metaData == null || (obj = applicationInfo.metaData.get("CHANNEL")) == null) ? "Unknown" : obj.toString();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "Unknown";
        }
    }

    public static String b(String str) {
        return (str == null || str.equals(Constants.MAIN_VERSION_TAG)) ? "000000000000000" : str;
    }

    public static String b(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b2 : bArr) {
            sb.append(a[(b2 & 240) >> 4]).append(a[b2 & 15]);
        }
        return sb.toString();
    }

    public static void b(Long l) {
        d = l;
    }

    public static Long c() {
        return f;
    }

    public static void c(Long l) {
        e = l;
    }

    public static String[] c(Context context) {
        String[] strArr = {"Unknown", "Unknown"};
        if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            strArr[0] = "Unknown";
            return strArr;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            strArr[0] = "Unknown";
            return strArr;
        }
        if (connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            strArr[0] = "Wi-Fi";
            return strArr;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
        if (networkInfo.getState() != NetworkInfo.State.CONNECTED) {
            return strArr;
        }
        strArr[0] = "2G/3G/4G";
        strArr[1] = networkInfo.getSubtypeName();
        return strArr;
    }

    public static int d() {
        return g;
    }

    public static void d(Long l) {
        f = l;
    }

    public static boolean d(Context context) {
        if (e.longValue() < 0) {
            return false;
        }
        return new File(context.getFilesDir(), new StringBuilder("../shared_prefs/").append(new StringBuilder("hianalytics_state_").append(context.getPackageName()).append(".xml").toString()).toString()).length() > e.longValue();
    }

    public static String e(Context context) {
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e2) {
            return "unknown";
        }
    }

    public static boolean e() {
        return b;
    }

    public static Handler f() {
        if (j == null) {
            Looper looper = h.getLooper();
            if (looper == null) {
                return null;
            }
            j = new Handler(looper);
        }
        return j;
    }

    public static boolean f(Context context) {
        SharedPreferences sharedPreferencesA = c.a(context, "flag");
        String str = Build.DISPLAY;
        String string = sharedPreferencesA.getString("rom_version", Constants.MAIN_VERSION_TAG);
        return Constants.MAIN_VERSION_TAG.equals(string) || !string.equals(str);
    }

    public static Handler g() {
        if (k == null) {
            Looper looper = i.getLooper();
            if (looper == null) {
                return null;
            }
            k = new Handler(looper);
        }
        return k;
    }

    public static void h() {
    }

    public static String i() {
        return "http://data.hicloud.com:8089/sdkv2";
    }
}
