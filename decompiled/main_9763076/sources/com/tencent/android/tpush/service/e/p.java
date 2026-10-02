package com.tencent.android.tpush.service.e;

import java.io.File;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class p {
    private static int a = -1;

    public static boolean a() {
        if (a == 1) {
            return true;
        }
        if (a == 0) {
            return false;
        }
        for (String str : new String[]{"/bin", "/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"}) {
            try {
                File file = new File(str + "su");
                if (file != null && file.exists()) {
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
