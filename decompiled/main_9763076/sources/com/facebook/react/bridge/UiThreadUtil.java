package com.facebook.react.bridge;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class UiThreadUtil {
    private static Handler sMainHandler;

    public static boolean isOnUiThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public static void assertOnUiThread() {
        SoftAssertions.assertCondition(isOnUiThread(), "Expected to run on UI thread!");
    }

    public static void runOnUiThread(Runnable runnable) {
        synchronized (UiThreadUtil.class) {
            if (sMainHandler == null) {
                sMainHandler = new Handler(Looper.getMainLooper());
            }
        }
        sMainHandler.post(runnable);
    }
}
