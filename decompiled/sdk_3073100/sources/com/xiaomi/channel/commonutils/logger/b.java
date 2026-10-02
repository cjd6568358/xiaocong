package com.xiaomi.channel.commonutils.logger;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class b {
    private static int a = 2;
    private static LoggerInterface b = new a();
    private static final HashMap<Integer, Long> c = new HashMap<>();
    private static final HashMap<Integer, String> d = new HashMap<>();
    private static final Integer e = -1;
    private static AtomicInteger f = new AtomicInteger(1);

    public static int a() {
        return a;
    }

    public static void a(int i, String str) {
        if (i >= a) {
            b.log(str);
        }
    }

    public static void a(int i, String str, Throwable th) {
        if (i >= a) {
            b.log(str, th);
        }
    }

    public static void a(int i, Throwable th) {
        if (i >= a) {
            b.log("", th);
        }
    }

    public static void a(Integer num) {
        if (a > 1 || !c.containsKey(num)) {
            return;
        }
        long jLongValue = c.remove(num).longValue();
        b.log(d.remove(num) + " ends in " + (System.currentTimeMillis() - jLongValue) + " ms");
    }

    public static void a(String str) {
        a(2, "[Thread:" + Thread.currentThread().getId() + "] " + str);
    }

    public static void a(String str, Throwable th) {
        a(4, str, th);
    }

    public static void a(Throwable th) {
        a(4, th);
    }

    public static void b(String str) {
        a(0, str);
    }

    public static void c(String str) {
        a(1, "[Thread:" + Thread.currentThread().getId() + "] " + str);
    }

    public static void d(String str) {
        a(4, str);
    }

    public static Integer e(String str) {
        if (a > 1) {
            return e;
        }
        Integer numValueOf = Integer.valueOf(f.incrementAndGet());
        c.put(numValueOf, Long.valueOf(System.currentTimeMillis()));
        d.put(numValueOf, str);
        b.log(str + " starts");
        return numValueOf;
    }
}
