package com.baidu.mobstat;

import android.content.Context;
import android.util.Log;
import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class NativeCrashHandler {
    private static boolean a;
    private static Context b;

    private static native void nativeException();

    private static native void nativeInit(String str);

    private static native void nativeProcess(String str);

    private static native void nativeUnint();

    static {
        a = false;
        try {
            System.loadLibrary("crash_analysis");
            a = true;
        } catch (Throwable th) {
            Log.w("NativeCrashHandler", "Load library failed.");
        }
    }

    public static void init(Context context) {
        if (context != null) {
            b = context.getApplicationContext();
            if (a) {
                File cacheDir = context.getCacheDir();
                if (cacheDir.exists() && cacheDir.isDirectory()) {
                    try {
                        nativeInit(cacheDir.getAbsolutePath());
                    } catch (Throwable th) {
                        Log.w("NativeCrashHandler", "Invoke method nativeInit failed.");
                    }
                }
            }
        }
    }
}
