package com.xiaomi.stats;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.ak;
import com.xiaomi.xmpush.thrift.aq;
import java.util.Hashtable;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class h {
    private static final int a = com.xiaomi.push.thrift.a.PING_RTT.a();

    static class a {
        static Hashtable<Integer, Long> a = new Hashtable<>();
    }

    public static void a() {
        a(0, a);
    }

    public static void a(int i) {
        com.xiaomi.push.thrift.b bVarF = f.a().f();
        bVarF.a(com.xiaomi.push.thrift.a.CHANNEL_STATS_COUNTER.a());
        bVarF.c(i);
        f.a().a(bVarF);
    }

    public static synchronized void a(int i, int i2) {
        try {
            if (i2 < 16777215) {
                a.a.put(Integer.valueOf((i << 24) | i2), Long.valueOf(System.currentTimeMillis()));
            } else {
                com.xiaomi.channel.commonutils.logger.b.d("stats key should less than 16777215");
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void a(int i, int i2, int i3, String str, int i4) {
        com.xiaomi.push.thrift.b bVarF = f.a().f();
        bVarF.a((byte) i);
        bVarF.a(i2);
        bVarF.b(i3);
        bVarF.b(str);
        bVarF.c(i4);
        f.a().a(bVarF);
    }

    public static synchronized void a(int i, int i2, String str, int i3) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i4 = (i << 24) | i2;
        if (a.a.containsKey(Integer.valueOf(i4))) {
            com.xiaomi.push.thrift.b bVarF = f.a().f();
            bVarF.a(i2);
            bVarF.b((int) (jCurrentTimeMillis - a.a.get(Integer.valueOf(i4)).longValue()));
            bVarF.b(str);
            if (i3 > -1) {
                bVarF.c(i3);
            }
            f.a().a(bVarF);
            a.a.remove(Integer.valueOf(i2));
        } else {
            com.xiaomi.channel.commonutils.logger.b.d("stats key not found");
        }
    }

    public static void a(XMPushService xMPushService, ak.b bVar) {
        new com.xiaomi.stats.a(xMPushService, bVar).a();
    }

    public static void a(String str, int i, Exception exc) {
        com.xiaomi.push.thrift.b bVarF = f.a().f();
        if (i > 0) {
            bVarF.a(com.xiaomi.push.thrift.a.GSLB_REQUEST_SUCCESS.a());
            bVarF.b(str);
            bVarF.b(i);
            f.a().a(bVarF);
            return;
        }
        try {
            d.a aVarA = d.a(exc);
            bVarF.a(aVarA.a.a());
            bVarF.c(aVarA.b);
            bVarF.b(str);
            f.a().a(bVarF);
        } catch (NullPointerException e) {
        }
    }

    public static void a(String str, Exception exc) {
        try {
            d.a aVarB = d.b(exc);
            com.xiaomi.push.thrift.b bVarF = f.a().f();
            bVarF.a(aVarB.a.a());
            bVarF.c(aVarB.b);
            bVarF.b(str);
            f.a().a(bVarF);
        } catch (NullPointerException e) {
        }
    }

    public static void b() {
        a(0, a, null, -1);
    }

    public static void b(String str, Exception exc) {
        try {
            d.a aVarD = d.d(exc);
            com.xiaomi.push.thrift.b bVarF = f.a().f();
            bVarF.a(aVarD.a.a());
            bVarF.c(aVarD.b);
            bVarF.b(str);
            f.a().a(bVarF);
        } catch (NullPointerException e) {
        }
    }

    public static byte[] c() {
        com.xiaomi.push.thrift.c cVarE = f.a().e();
        if (cVarE != null) {
            return aq.a(cVarE);
        }
        return null;
    }
}
