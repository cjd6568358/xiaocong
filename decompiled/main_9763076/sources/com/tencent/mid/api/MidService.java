package com.tencent.mid.api;

import android.content.Context;
import com.tencent.mid.a.h;
import com.tencent.mid.b.g;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MidService {
    private static boolean a = true;

    public static void enableDebug(boolean z) {
        h.a(z);
    }

    public static long getGuid(Context context) {
        return h.c(context);
    }

    public static String getLocalMidOnly(Context context) {
        return g.a(context).f();
    }

    public static String getMid(Context context) {
        return h.b(context);
    }

    public static String getMidRequestHost() {
        return null;
    }

    public static String getMidRequestUrl() {
        return null;
    }

    public static String getNewMid(Context context) {
        return g.a(context).b();
    }

    public static boolean isEnableDebug() {
        return h.a();
    }

    public static boolean isEnableReportWifiList() {
        return a;
    }

    public static boolean isMidValid(String str) {
        return h.a(str);
    }

    public static void requestMid(Context context, MidCallback midCallback) {
        if (midCallback == null) {
            throw new IllegalArgumentException("error, callback is null!");
        }
        if (context == null) {
            midCallback.onFail(MidConstants.ERROR_ARGUMENT, "content is null!");
        } else {
            h.a(context.getApplicationContext(), midCallback);
        }
    }

    public static void setEnableReportWifiList(boolean z) {
        a = z;
    }

    public static void setMidRequestUrl(String str) {
    }
}
