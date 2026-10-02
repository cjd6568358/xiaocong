package com.alibaba.sdk.android.httpdns;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private static boolean a = false;
    private static int c = -1;

    public static void a(Throwable th) {
        if (!a || th == null) {
            return;
        }
        th.printStackTrace();
    }

    private static String b() {
        if (c == -1) {
            int i = 0;
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                if (stackTraceElement.getMethodName().equals("getTraceInfo")) {
                    c = i + 1;
                    break;
                }
                i++;
            }
        }
        StackTraceElement stackTraceElement2 = Thread.currentThread().getStackTrace()[c + 1];
        return stackTraceElement2.getFileName() + ":" + stackTraceElement2.getLineNumber() + " - [" + stackTraceElement2.getMethodName() + "]";
    }

    public static void d(String str) {
        if (!a || str == null) {
            return;
        }
        Log.d("HttpDnsSDK", Thread.currentThread().getId() + " - " + b() + " - " + str);
    }

    public static void e(String str) {
        if (!a || str == null) {
            return;
        }
        Log.i("HttpDnsSDK", Thread.currentThread().getId() + " - " + b() + " - " + str);
    }

    public static void f(String str) {
        if (!a || str == null) {
            return;
        }
        Log.e("HttpDnsSDK", Thread.currentThread().getId() + " - " + b() + " - " + str);
    }

    static synchronized void setLogEnabled(boolean z) {
        a = z;
    }
}
