package com.tencent.wxop.stat.common;

import java.io.File;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class p {
    private static int a = -1;

    public static boolean a() {
        if (a == 1) {
            return true;
        }
        if (a == 0) {
            return false;
        }
        String[] strArr = {"/bin", "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        for (int i = 0; i < 6; i++) {
            try {
                if (new File(strArr[i] + "su").exists()) {
                    a = 1;
                    return true;
                }
            } catch (Exception e) {
            }
        }
        a = 0;
        return false;
    }
}
