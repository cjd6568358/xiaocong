package com.xiaocong.smarthome.xcnetwork.utils;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XcLogger {
    private static XcLogger log;
    private String mClassName;
    public static boolean OPEN_LOG = false;
    public static boolean DEBUG = true;

    private XcLogger(String name) {
        this.mClassName = name;
    }

    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (!st.isNativeMethod() && !st.getClassName().equals(Thread.class.getName()) && !st.getClassName().equals(getClass().getName())) {
                return this.mClassName + "[ " + Thread.currentThread().getName() + ": " + st.getFileName() + ":" + st.getLineNumber() + " " + st.getMethodName() + " ]";
            }
        }
        return null;
    }

    public static void e(String tag, Object str) {
        print(6, tag, str);
    }

    private static void print(int index, String tag, Object str) {
        if (OPEN_LOG) {
            if (log == null) {
                log = new XcLogger("@ixiaocong@");
            }
            String name = log.getFunctionName();
            if (name != null) {
                str = name + " - " + str;
            }
            if (DEBUG || index > 3) {
                switch (index) {
                    case 2:
                        Log.v(tag, str.toString());
                        break;
                    case 3:
                        Log.d(tag, str.toString());
                        break;
                    case 4:
                        Log.i(tag, str.toString());
                        break;
                    case 5:
                        Log.w(tag, str.toString());
                        break;
                    case 6:
                        Log.e(tag, str.toString());
                        break;
                }
            }
        }
    }
}
