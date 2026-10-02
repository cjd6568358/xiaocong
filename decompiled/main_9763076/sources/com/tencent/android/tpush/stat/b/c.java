package com.tencent.android.tpush.stat.b;

import android.content.Context;
import android.net.LocalServerSocket;
import com.tencent.android.tpush.common.Constants;
import java.io.IOException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static String b = null;
    private static long c = 0;
    static LocalServerSocket a = null;
    private static String d = null;

    public static boolean a() {
        try {
            a = new LocalServerSocket("com.tencent.teg.mid.sock.lock");
            return true;
        } catch (IOException e) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "socket Name:com.tencent.teg.mid.sock.lock is in use.");
            return false;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "something wrong while create LocalServerSocket.");
            return false;
        }
    }

    public static void b() {
        if (a != null) {
            try {
                a.close();
                com.tencent.android.tpush.a.a.c(Constants.LogTag, "close socket  mLocalServerSocket:" + a);
                a = null;
            } catch (Throwable th) {
            }
        }
    }

    public static long a(Context context) {
        if (c == 0 || c < 0) {
            synchronized (c.class) {
                if (c == 0 || c < 0) {
                    c = i.a(context).b().a();
                }
            }
        }
        return c;
    }

    public static String b(Context context) {
        if (!a(b)) {
            synchronized (c.class) {
                if (!a(b)) {
                    b = i.a(context).b().e();
                }
            }
        }
        return b;
    }

    public static void a(Context context, String str) {
        if (a(str)) {
            b = str;
            com.tencent.android.tpush.a.a.c(Constants.LogTag, "updateLocalMid:" + str);
            d dVar = new d();
            dVar.c(com.tencent.android.tpush.stat.a.h.e(context));
            dVar.e(com.tencent.android.tpush.stat.a.h.f(context));
            dVar.b(str);
            dVar.b(System.currentTimeMillis());
            i.a(context).d(dVar);
        }
    }

    public static void a(Context context, long j, String str) {
        com.tencent.android.tpush.a.a.c(Constants.LogTag, "updateLocalGuid:" + j);
        if (a(str)) {
            b = str;
            c = j;
            d dVar = new d();
            dVar.c(com.tencent.android.tpush.stat.a.h.e(context));
            dVar.e(com.tencent.android.tpush.stat.a.h.f(context));
            dVar.b(str);
            dVar.a(j);
            dVar.b(System.currentTimeMillis());
            i.a(context).d(dVar);
        }
    }

    public static boolean a(String str) {
        return str != null && str.trim().length() == 40;
    }
}
