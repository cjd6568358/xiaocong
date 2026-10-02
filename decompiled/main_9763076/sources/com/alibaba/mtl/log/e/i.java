package com.alibaba.mtl.log.e;

import android.os.Process;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: Logger.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class i {
    private static String af = "UTAnalytics:";
    private static boolean J = false;
    private static boolean K = false;

    public static boolean k() {
        return J;
    }

    public static boolean l() {
        return K;
    }

    public static void d(boolean z) {
        K = z;
    }

    public static void a(String str, Object... objArr) {
        if (K) {
            String str2 = af + str;
            StringBuilder sb = new StringBuilder();
            sb.append("pid:").append(Process.myPid()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            if (objArr != null) {
                for (int i = 0; i < objArr.length; i++) {
                    if (objArr[i] != null) {
                        String string = objArr[i].toString();
                        if (string.endsWith(":") || string.endsWith(": ")) {
                            sb.append(string);
                        } else {
                            sb.append(string).append(",");
                        }
                    }
                }
            }
            Log.d(str2, sb.toString());
        }
    }

    public static void a(String str, Object obj, Throwable th) {
        if (l() || k()) {
            Log.w(str + af, obj + Constants.MAIN_VERSION_TAG, th);
        }
    }

    public static void a(String str, Object obj) {
        if (l() || k()) {
            Log.w(str + af, obj + Constants.MAIN_VERSION_TAG);
        }
    }

    public static void a(String str, String... strArr) {
        if (K) {
            String str2 = af + str;
            StringBuilder sb = new StringBuilder();
            sb.append("pid:").append(Process.myPid()).append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            if (strArr != null) {
                for (int i = 0; i < strArr.length; i++) {
                    if (strArr[i] != null) {
                        String str3 = strArr[i];
                        if (str3.endsWith(":") || str3.endsWith(": ")) {
                            sb.append(str3);
                        } else {
                            sb.append(str3).append(",");
                        }
                    }
                }
            }
            Log.i(str2, sb.toString());
        }
    }
}
