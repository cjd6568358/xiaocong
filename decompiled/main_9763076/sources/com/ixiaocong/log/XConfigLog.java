package com.ixiaocong.log;

import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class XConfigLog {
    private static XConfigLog log;
    private String mClassName;
    public static boolean OPEN_LOG = false;
    public static boolean DEBUG = true;

    private XConfigLog(String name) {
        this.mClassName = name;
    }

    private String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (!st.isNativeMethod() && !st.getClassName().equals(Thread.class.getName()) && !st.getClassName().equals(getClass().getName())) {
                return this.mClassName + "[ " + Thread.currentThread().getName() + ": " + st.getFileName() + ":" + st.getLineNumber() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + st.getMethodName() + " ]";
            }
        }
        return null;
    }

    public static void d(String tag, Object str) {
        print(3, tag, str);
    }

    public static void w(String tag, Object str) {
        print(5, tag, str);
    }

    public static void e(String tag, Object str) {
        print(6, tag, str);
    }

    private static void print(int index, String tag, Object str) {
        if (OPEN_LOG) {
            if (log == null) {
                log = new XConfigLog("#xconfig#");
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
