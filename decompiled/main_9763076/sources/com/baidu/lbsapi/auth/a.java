package com.baidu.lbsapi.auth;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class a {
    public static boolean a = false;
    private static String b = "BaiduApiAuth";

    public static String a() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        return stackTraceElement.getFileName() + "[" + stackTraceElement.getLineNumber() + "]";
    }

    public static void a(String str) {
        if (!a || Thread.currentThread().getStackTrace().length == 0) {
            return;
        }
        Log.d(b, a() + ";" + str);
    }

    public static void b(String str) {
        if (Thread.currentThread().getStackTrace().length == 0) {
            return;
        }
        Log.i(b, str);
    }

    public static void c(String str) {
        if (!a || Thread.currentThread().getStackTrace().length == 0) {
            return;
        }
        Log.e(b, a() + ";" + str);
    }
}
