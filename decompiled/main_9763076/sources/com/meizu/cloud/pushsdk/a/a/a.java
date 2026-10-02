package com.meizu.cloud.pushsdk.a.a;

import com.meizu.cloud.pushinternal.DebugLogger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static boolean a = false;
    private static String b = "AndroidNetworking";

    public static void a() {
        a = true;
    }

    public static void a(String str) {
        if (a) {
            DebugLogger.d(b, str);
        }
    }

    public static void b(String str) {
        if (a) {
            DebugLogger.i(b, str);
        }
    }
}
