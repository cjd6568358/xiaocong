package com.xiaocong.smarthome.util.log;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCLog {
    public static final boolean D;
    public static final boolean E;
    public static final boolean I;
    public static final boolean V;
    public static final boolean W;
    public static boolean printLog = false;

    static {
        V = printLog;
        D = printLog;
        I = printLog;
        W = printLog;
        E = printLog;
    }

    public static void i(String tag, String msg) {
        if (I) {
            Log.i(tag, msg + "");
        }
    }

    public static void e(String tag, String msg) {
        if (E) {
            Log.e(tag, msg + "");
        }
    }

    public static void e(Throwable tr) {
        if (E && tr != null) {
            tr.printStackTrace();
        }
    }
}
