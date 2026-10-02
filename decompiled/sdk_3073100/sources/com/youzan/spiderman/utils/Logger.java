package com.youzan.spiderman.utils;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class Logger {
    private static boolean mIsLogEnabled = false;
    private static long sLastTimeStamp = 0;
    private static final ThreadLocal<SimpleDateFormat> sDateFormatter = new ThreadLocal<SimpleDateFormat>() { // from class: com.youzan.spiderman.utils.Logger.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm:ss.SSS");
        }
    };

    private static String getTag(String tag) {
        return String.format(":Spider:%s:(%s):%s:", sDateFormatter.get().format((Date) new java.sql.Date(System.currentTimeMillis())), Thread.currentThread().getName(), tag);
    }

    private static String getMessage(String msg, Object... params) {
        if (params != null && params.length != 0) {
            try {
                return String.format(msg, params);
            } catch (Throwable e) {
                StringBuilder builder = new StringBuilder(msg);
                for (Object param : params) {
                    builder.append(", ").append(param);
                }
                builder.append("\n").append(Log.getStackTraceString(e));
                return builder.toString();
            }
        }
        return msg;
    }

    public static boolean isLogEnabled() {
        return mIsLogEnabled;
    }

    public static int i(String tag, String msg, Object... params) {
        if (isLogEnabled()) {
            return Log.i(getTag(tag), getMessage(msg, params));
        }
        return 0;
    }

    public static int d(String tag, String msg, Object... params) {
        if (isLogEnabled()) {
            return Log.d(getTag(tag), getMessage(msg, params));
        }
        return 0;
    }

    public static int e(String tag, String msg, Object... params) {
        if (isLogEnabled()) {
            return Log.e(getTag(tag), getMessage(msg, params));
        }
        return 0;
    }

    public static int e(String tag, Throwable e) {
        if (isLogEnabled()) {
            return Log.e(getTag(tag), Log.getStackTraceString(e));
        }
        return 0;
    }
}
