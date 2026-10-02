package com.meizu.cloud.pushsdk.common.b;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class c {
    private static a b;
    private static Handler a = new e(Looper.getMainLooper());
    private static LinkedList<b> c = new LinkedList<>();
    private static a.EnumC0033a d = a.EnumC0033a.DEBUG;
    private static a.EnumC0033a e = a.EnumC0033a.DEBUG;
    private static C0034c f = new C0034c();

    public interface a {

        /* JADX INFO: renamed from: com.meizu.cloud.pushsdk.common.b.c$a$a, reason: collision with other inner class name */
        public enum EnumC0033a {
            DEBUG,
            INFO,
            WARN,
            ERROR,
            NULL
        }

        void a(EnumC0033a enumC0033a, String str, String str2);
    }

    /* JADX INFO: renamed from: com.meizu.cloud.pushsdk.common.b.c$c, reason: collision with other inner class name */
    public static class C0034c {
        int a = 100;
        int b = 120000;
    }

    public enum d {
        CONSOLE,
        FILE
    }

    public static void a(d dVar, a.EnumC0033a enumC0033a) {
        if (dVar == d.CONSOLE) {
            d = enumC0033a;
        } else if (dVar == d.FILE) {
            e = enumC0033a;
        }
    }

    public static void a(a aVar) {
        b = aVar;
    }

    public static void a() {
        synchronized (c) {
            a.removeMessages(1);
            a.obtainMessage(1).sendToTarget();
        }
    }

    private static void a(a.EnumC0033a enumC0033a, String str, String str2) {
        if (b != null && e.ordinal() <= enumC0033a.ordinal()) {
            synchronized (c) {
                c.addLast(new b(enumC0033a, str, str2));
                if (c.size() >= f.a || f.b <= 0) {
                    a();
                } else if (!a.hasMessages(1)) {
                    a.sendMessageDelayed(a.obtainMessage(1), f.b);
                }
            }
        }
    }

    public static void a(String str, String str2) {
        if (d.ordinal() <= a.EnumC0033a.DEBUG.ordinal()) {
            Log.d(str, str2);
        }
        a(a.EnumC0033a.DEBUG, str, str2);
    }

    public static void b(String str, String str2) {
        if (d.ordinal() <= a.EnumC0033a.INFO.ordinal()) {
            Log.i(str, str2);
        }
        a(a.EnumC0033a.INFO, str, str2);
    }

    public static void c(String str, String str2) {
        if (d.ordinal() <= a.EnumC0033a.WARN.ordinal()) {
            Log.w(str, str2);
        }
        a(a.EnumC0033a.WARN, str, str2);
    }

    public static void d(String str, String str2) {
        if (d.ordinal() <= a.EnumC0033a.ERROR.ordinal()) {
            Log.e(str, str2);
        }
        a(a.EnumC0033a.ERROR, str, str2);
    }

    public static void a(String str, Throwable th) {
        d(str, Log.getStackTraceString(th));
    }

    private static class b {
        static SimpleDateFormat a = new SimpleDateFormat("MM-dd HH:mm:ss ");
        static String b = String.valueOf(Process.myPid());
        a.EnumC0033a c;
        String d;
        String e;

        b(a.EnumC0033a enumC0033a, String str, String str2) {
            this.c = enumC0033a;
            this.d = a.format(new Date()) + b + "-" + String.valueOf(Thread.currentThread().getId()) + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + str;
            this.e = str2;
        }
    }

    private static class e extends Handler {
        e(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (c.b != null) {
                g.a(new g.a() { // from class: com.meizu.cloud.pushsdk.common.b.c.e.1
                    @Override // com.meizu.cloud.pushsdk.common.b.g.a
                    public void a() {
                        LinkedList<b> linkedList;
                        synchronized (c.c) {
                            linkedList = new LinkedList(c.c);
                            c.c.clear();
                        }
                        for (b bVar : linkedList) {
                            c.b.a(bVar.c, bVar.d, bVar.e);
                        }
                    }
                });
            }
        }
    }
}
