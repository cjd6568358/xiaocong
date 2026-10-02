package com.youzan.spiderman.utils;

import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class PermissionUtil {
    public static boolean hasExtStroragePermision(Context context) {
        return Build.VERSION.SDK_INT < 23 || (context.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0 && context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0);
    }

    public static boolean hasReadPhoneStatePermission(Context context) {
        return Build.VERSION.SDK_INT < 23 || context.checkSelfPermission("android.permission.READ_PHONE_STATE") == 0;
    }
}
