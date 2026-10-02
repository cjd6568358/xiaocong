package com.baidu.mobstat;

import android.util.Log;
import com.tencent.android.tpush.common.Constants;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class db {
    public static int a = 7;

    public static void a(String str) {
        a(3, str);
    }

    public static void a(Throwable th) {
        a(3, d(th));
    }

    public static void a(String str, Throwable th) {
        a(3, str + '\n' + d(th));
    }

    public static void b(String str) {
        a(5, str);
    }

    public static void b(Throwable th) {
        a(5, d(th));
    }

    public static void c(String str) {
        a(6, str);
    }

    public static void c(Throwable th) {
        a(6, d(th));
    }

    private static String d(Throwable th) {
        if (th == null) {
            return Constants.MAIN_VERSION_TAG;
        }
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
            if (cause instanceof UnknownHostException) {
                return Constants.MAIN_VERSION_TAG;
            }
        }
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private static void a(int i, String str) {
        if (a(i)) {
            Log.println(i, a(), str);
        }
    }

    private static boolean a(int i) {
        return i >= a;
    }

    private static String a() {
        return "sdkstat";
    }
}
