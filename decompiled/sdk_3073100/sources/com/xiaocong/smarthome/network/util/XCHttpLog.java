package com.xiaocong.smarthome.network.util;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCHttpLog {
    public static boolean printLog = false;

    public static void e(String msg) {
        if (printLog) {
            Log.e("XCHttpLog", msg + "");
        }
    }
}
