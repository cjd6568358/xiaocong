package com.meizu.cloud.pushinternal;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.meizu.cloud.pushsdk.common.b.h;
import com.meizu.cloud.pushsdk.common.base.c;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DebugLogger {
    private static c encryptLogger;
    public static boolean isOnDebug = false;
    public static boolean debug = false;

    public static void initDebugLogger(Context context) {
        if (encryptLogger == null) {
            encryptLogger = new c(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdk/" + context.getPackageName());
            com.meizu.cloud.pushsdk.common.b.c.a(encryptLogger);
            debug = h.b(context);
            Log.e("DebugLogger", "isOnDebug " + isOnDebug + " debugConfig " + debug + " isDebuggable " + isDebuggable());
            if (!isDebuggable()) {
                com.meizu.cloud.pushsdk.common.b.c.a(com.meizu.cloud.pushsdk.common.b.c.d.CONSOLE, com.meizu.cloud.pushsdk.common.b.c.a.EnumC0033a.NULL);
            }
        }
    }

    public static boolean isDebuggable() {
        return isOnDebug || debug;
    }

    public static void i(String str, String str2) {
        com.meizu.cloud.pushsdk.common.b.c.b(str, str2);
    }

    public static void d(String str, String str2) {
        com.meizu.cloud.pushsdk.common.b.c.a(str, str2);
    }

    public static void e(String str, String str2) {
        com.meizu.cloud.pushsdk.common.b.c.d(str, str2);
    }
}
