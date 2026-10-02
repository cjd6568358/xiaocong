package com.youzan.jsbridge.util;

import android.text.TextUtils;
import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class Logger {
    private static boolean sIsDebug = false;

    static boolean isDebug() {
        return sIsDebug;
    }

    public static void d(String tag, String msg) {
        if (isDebug() && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        d("WVC_JsBridge", msg);
    }

    public static void w(String tag, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        w("WVC_JsBridge", msg);
    }

    public static void e(String tag, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        e("WVC_JsBridge", msg);
    }
}
