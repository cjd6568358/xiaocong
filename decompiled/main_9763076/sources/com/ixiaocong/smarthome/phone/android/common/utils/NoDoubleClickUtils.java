package com.ixiaocong.smarthome.phone.android.common.utils;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class NoDoubleClickUtils {
    private static long lastClickTime;

    public static synchronized boolean isDoubleClick() {
        boolean isClick2;
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > 500) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }
}
