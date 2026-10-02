package com.hzy.tvmao.utils;

import android.annotation.TargetApi;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class LogUtil {
    public static boolean DEBUG;
    public static boolean allowD;
    public static boolean allowE;
    public static boolean allowI;
    public static boolean allowV;
    public static boolean allowW;
    public static boolean allowWrite;
    public static boolean allowWtf;
    public static a customLogger;
    public static String customTagPrefix = Constants.MAIN_VERSION_TAG;
    private static FileWriter mFileWriter;
    private static String mLogPath;

    public interface a {
        void a(String str, String str2);

        void a(String str, String str2, Throwable th);

        void a(String str, Throwable th);

        void b(String str, String str2);

        void b(String str, String str2, Throwable th);

        void b(String str, Throwable th);

        void c(String str, String str2);

        void c(String str, String str2, Throwable th);

        void d(String str, String str2);

        void d(String str, String str2, Throwable th);

        void e(String str, String str2);

        void e(String str, String str2, Throwable th);

        void f(String str, String str2);

        void f(String str, String str2, Throwable th);
    }

    static {
        mLogPath = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            mLogPath = String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()) + File.separator + "log.txt";
        }
        DEBUG = false;
        allowD = DEBUG;
        allowE = DEBUG;
        allowI = DEBUG;
        allowV = DEBUG;
        allowW = DEBUG;
        allowWtf = DEBUG;
        allowWrite = DEBUG;
    }

    private LogUtil() {
    }

    public static void setDebugMode(boolean z) {
        DEBUG = false;
        allowD = z;
        allowE = z;
        allowI = z;
        allowV = z;
        allowW = z;
        allowWtf = z;
        allowWrite = z;
    }

    private static String generateTag(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        String str = String.format("%s.%s(L:%d)", className.substring(className.lastIndexOf(".") + 1), stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber()));
        if (!TextUtils.isEmpty(customTagPrefix)) {
            return String.valueOf(customTagPrefix) + ":" + str;
        }
        return str;
    }

    public static void d(String str) {
        if (allowD) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.a(strGenerateTag, str);
            } else {
                Log.d(strGenerateTag, str);
            }
        }
    }

    public static void d(String str, Throwable th) {
        if (allowD) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.a(strGenerateTag, str, th);
            } else {
                Log.d(strGenerateTag, str, th);
            }
        }
    }

    public static void e(String str) {
        if (allowE) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.b(strGenerateTag, str);
            } else {
                Log.e(strGenerateTag, str);
            }
        }
    }

    public static void e(String str, Throwable th) {
        if (allowE) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.b(strGenerateTag, str, th);
            } else {
                Log.e(strGenerateTag, str, th);
            }
        }
    }

    public static void i(String str) {
        if (allowI) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.c(strGenerateTag, str);
            } else {
                Log.i(strGenerateTag, str);
            }
        }
    }

    public static void i(String str, Throwable th) {
        if (allowI) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.c(strGenerateTag, str, th);
            } else {
                Log.i(strGenerateTag, str, th);
            }
        }
    }

    public static void v(String str) {
        if (allowV) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.d(strGenerateTag, str);
            } else {
                Log.v(strGenerateTag, str);
            }
        }
    }

    public static void v(String str, Throwable th) {
        if (allowV) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.d(strGenerateTag, str, th);
            } else {
                Log.v(strGenerateTag, str, th);
            }
        }
    }

    public static void w(String str) {
        if (allowW) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.e(strGenerateTag, str);
            } else {
                Log.w(strGenerateTag, str);
            }
        }
    }

    public static void w(String str, Throwable th) {
        if (allowW) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.e(strGenerateTag, str, th);
            } else {
                Log.w(strGenerateTag, str, th);
            }
        }
    }

    public static void w(Throwable th) {
        if (allowW) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.a(strGenerateTag, th);
            } else {
                Log.w(strGenerateTag, th);
            }
        }
    }

    @TargetApi(8)
    public static void wtf(String str) {
        if (allowWtf) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.f(strGenerateTag, str);
            } else {
                Log.wtf(strGenerateTag, str);
            }
        }
    }

    @TargetApi(8)
    public static void wtf(String str, Throwable th) {
        if (allowWtf) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.f(strGenerateTag, str, th);
            } else {
                Log.wtf(strGenerateTag, str, th);
            }
        }
    }

    @TargetApi(8)
    public static void wtf(Throwable th) {
        if (allowWtf) {
            String strGenerateTag = generateTag(getCallerStackTraceElement());
            if (customLogger != null) {
                customLogger.b(strGenerateTag, th);
            } else {
                Log.wtf(strGenerateTag, th);
            }
        }
    }

    public static void write(String str) {
        if (allowWrite) {
            Log.e(generateTag(getCallerStackTraceElement()), str);
            writeLog(str);
        }
    }

    private static void writeLog(String str) {
        try {
            if (mFileWriter == null) {
                mFileWriter = new FileWriter(new File(mLogPath));
            }
            mFileWriter.write(str);
            mFileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
}
