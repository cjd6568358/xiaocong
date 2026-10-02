package com.youzan.androidsdk;

import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class YouzanLog {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static boolean f8 = false;

    public static boolean isDebug() {
        return f8;
    }

    public static void isDebug(boolean isDebug) {
        f8 = isDebug;
    }

    public static void i(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.i("YZSDK", m5(msg));
        }
    }

    public static void d(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.d("YZSDK", m5(msg));
        }
    }

    public static void w(String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.w("YZSDK", m5(msg));
        }
    }

    public static void e(Object msg) {
        if (isDebug() && msg != null) {
            Log.e("YZSDK", m5(msg));
        }
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static String m5(Object msgObj) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 4) {
            String className = stackTrace[4].getFileName();
            String methodName = stackTrace[4].getMethodName();
            int lineNumber = stackTrace[4].getLineNumber();
            String methodName2 = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
            StringBuilder stringBuilder = new StringBuilder(24);
            stringBuilder.append("(").append(className).append(':').append(lineNumber).append(")->").append(methodName2).append(" : ");
            String msg = msgObj == null ? "CONTENT IS NONE" : msgObj.toString();
            stringBuilder.append(msg);
            return stringBuilder.toString();
        }
        return "CONTENT IS NONE";
    }
}
