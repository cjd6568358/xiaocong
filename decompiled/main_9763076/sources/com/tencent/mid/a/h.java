package com.tencent.mid.a;

import android.content.Context;
import com.tencent.mid.api.MidCallback;
import com.tencent.mid.api.MidEntity;
import com.tencent.mid.util.Util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class h {
    private static com.tencent.mid.util.f a = Util.getLogger();

    public static MidEntity a(Context context) {
        return com.tencent.mid.b.g.a(context).h();
    }

    public static void a(Context context, MidCallback midCallback) {
        a.b("requestMid, callback=" + midCallback);
        b(context, new i(midCallback));
    }

    public static void a(boolean z) {
        Util.getLogger().a(z);
    }

    public static boolean a() {
        return Util.getLogger().a();
    }

    public static boolean a(String str) {
        return Util.isMidValid(str);
    }

    public static String b(Context context) {
        if (context == null) {
            a.f("context==null in getMid()");
            return null;
        }
        String strF = com.tencent.mid.b.g.a(context).f();
        if (Util.isMidValid(strF)) {
            return strF;
        }
        j jVar = new j();
        a.h("getMid -> request new mid entity.");
        n.a().a(new k(context, 1, jVar));
        return strF;
    }

    public static void b(Context context, MidCallback midCallback) {
        if (c(context, midCallback)) {
            MidEntity midEntityA = a(context);
            if (midEntityA == null || !midEntityA.isMidValid()) {
                a.b("requestMidEntity -> request new mid entity.");
                n.a().a(new k(context, 1, midCallback));
            } else {
                a.b("requestMidEntity -> get local mid entity:" + midEntityA.toString());
                midCallback.onSuccess(midEntityA.toString());
                n.a().a(new k(context, 2, midCallback));
            }
        }
    }

    public static long c(Context context) {
        if (context != null) {
            return com.tencent.mid.b.g.a(context).g();
        }
        a.f("context==null in getGuid()");
        return 0L;
    }

    private static boolean c(Context context, MidCallback midCallback) {
        return true;
    }

    public static String d(Context context) {
        if (context != null) {
            return com.tencent.mid.b.g.a(context).f();
        }
        a.f("context==null in getMid()");
        return null;
    }
}
