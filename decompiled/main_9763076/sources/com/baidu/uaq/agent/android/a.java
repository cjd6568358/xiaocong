package com.baidu.uaq.agent.android;

import com.meizu.cloud.pushsdk.constants.PushConstants;

/* JADX INFO: compiled from: Agent.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class a {
    private static final b g = new e();
    private static final Object h = new Object();
    private static b i = g;

    public static void a(b impl2) {
        synchronized (h) {
            try {
                if (impl2 == null) {
                    i = g;
                } else {
                    i = impl2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static b a() {
        b bVar;
        synchronized (h) {
            bVar = i;
        }
        return bVar;
    }

    public static String getVersion() {
        return "4.3.0";
    }

    public static String b() {
        return PushConstants.PUSH_TYPE_NOTIFY;
    }

    public static void start() {
        a().start();
    }

    public static void shutdown() {
        a().shutdown();
    }

    public static String c() {
        return a().g();
    }

    public static String d() {
        return a().h();
    }

    public static com.baidu.uaq.agent.android.harvest.bean.c e() {
        return a().e();
    }

    public static com.baidu.uaq.agent.android.harvest.bean.a f() {
        return a().f();
    }
}
