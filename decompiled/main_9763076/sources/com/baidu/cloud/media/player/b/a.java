package com.baidu.cloud.media.player.b;

import android.util.Log;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    public static boolean a = true;
    public static boolean b = false;
    public static boolean c = true;
    public static boolean d = false;
    public static boolean e = false;

    public static void a(String str, String str2) {
        if (a) {
            Log.e(str, str2);
        }
    }

    public static void a(String str, String str2, Object... objArr) {
        if (b) {
            Log.i(str, String.format(Locale.US, str2, objArr));
        }
    }

    public static void a(boolean z) {
        if (z) {
            a = true;
            c = true;
            b = true;
            d = true;
            e = true;
            return;
        }
        a = true;
        c = true;
        b = false;
        d = false;
        e = false;
    }

    public static void b(String str, String str2) {
        if (b) {
            Log.i(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (c) {
            Log.w(str, str2);
        }
    }

    public static void d(String str, String str2) {
        if (d) {
            Log.d(str, str2);
        }
    }
}
