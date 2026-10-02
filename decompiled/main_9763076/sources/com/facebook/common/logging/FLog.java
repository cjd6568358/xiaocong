package com.facebook.common.logging;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class FLog {
    private static LoggingDelegate sHandler = FLogDefaultLoggingDelegate.getInstance();

    public static boolean isLoggable(int level) {
        return sHandler.isLoggable(level);
    }

    public static void v(Class<?> cls, String msg) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(getTag(cls), msg);
        }
    }

    public static void v(Class<?> cls, String msg, Object arg1) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(getTag(cls), formatString(msg, arg1));
        }
    }

    public static void v(Class<?> cls, String msg, Object arg1, Object arg2) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(getTag(cls), formatString(msg, arg1, arg2));
        }
    }

    public static void v(Class<?> cls, String msg, Object arg1, Object arg2, Object arg3) {
        if (isLoggable(2)) {
            v(cls, formatString(msg, arg1, arg2, arg3));
        }
    }

    public static void v(Class<?> cls, String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(getTag(cls), formatString(msg, arg1, arg2, arg3, arg4));
        }
    }

    public static void v(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(tag, formatString(msg, args));
        }
    }

    public static void v(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(2)) {
            sHandler.v(getTag(cls), formatString(msg, args));
        }
    }

    public static void d(String tag, String msg) {
        if (sHandler.isLoggable(3)) {
            sHandler.d(tag, msg);
        }
    }

    public static void d(Class<?> cls, String msg, Object arg1) {
        if (sHandler.isLoggable(3)) {
            sHandler.d(getTag(cls), formatString(msg, arg1));
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(3)) {
            sHandler.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (sHandler.isLoggable(4)) {
            sHandler.i(tag, msg);
        }
    }

    public static void i(Class<?> cls, String msg) {
        if (sHandler.isLoggable(4)) {
            sHandler.i(getTag(cls), msg);
        }
    }

    public static void w(String tag, String msg) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(tag, msg);
        }
    }

    public static void w(Class<?> cls, String msg) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(getTag(cls), msg);
        }
    }

    public static void w(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(tag, formatString(msg, args));
        }
    }

    public static void w(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(getTag(cls), formatString(msg, args));
        }
    }

    public static void w(Class<?> cls, Throwable tr, String msg, Object... args) {
        if (isLoggable(5)) {
            w(cls, formatString(msg, args), tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(tag, msg, tr);
        }
    }

    public static void w(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(5)) {
            sHandler.w(getTag(cls), msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(tag, msg);
        }
    }

    public static void e(Class<?> cls, String msg) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(getTag(cls), msg);
        }
    }

    public static void e(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(getTag(cls), formatString(msg, args));
        }
    }

    public static void e(Class<?> cls, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(getTag(cls), formatString(msg, args), tr);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(tag, msg, tr);
        }
    }

    public static void e(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(6)) {
            sHandler.e(getTag(cls), msg, tr);
        }
    }

    public static void wtf(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(tag, formatString(msg, args));
        }
    }

    public static void wtf(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(getTag(cls), formatString(msg, args));
        }
    }

    public static void wtf(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(getTag(cls), msg, tr);
        }
    }

    private static String formatString(String str, Object... args) {
        return String.format(null, str, args);
    }

    private static String getTag(Class<?> clazz) {
        return clazz.getSimpleName();
    }
}
