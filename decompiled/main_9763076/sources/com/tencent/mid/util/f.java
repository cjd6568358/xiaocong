package com.tencent.mid.util;

import android.util.Log;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class f {
    private static boolean b = false;
    private String a;
    private int c;

    public f() {
        this.a = "default";
        this.c = 2;
    }

    public f(String str) {
        this.a = "default";
        this.c = 2;
        this.a = str;
    }

    private String b() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null) {
            return null;
        }
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(getClass().getName())) {
                return "[" + Thread.currentThread().getName() + "(" + Thread.currentThread().getId() + "): " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + "]";
            }
        }
        return null;
    }

    public void a(Exception exc) {
        if (this.c <= 6) {
            StringBuffer stringBuffer = new StringBuffer();
            String strB = b();
            StackTraceElement[] stackTrace = exc.getStackTrace();
            if (strB != null) {
                stringBuffer.append(strB + " - " + exc + "\r\n");
            } else {
                stringBuffer.append(exc + "\r\n");
            }
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement != null) {
                        stringBuffer.append("[ " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + " ]\r\n");
                    }
                }
            }
            Log.e(this.a, stringBuffer.toString());
        }
    }

    public void a(Object obj) {
        if (this.c <= 4) {
            String strB = b();
            Log.i(this.a, strB == null ? obj.toString() : strB + " - " + obj);
        }
    }

    public void a(boolean z) {
        b = z;
    }

    public boolean a() {
        return b;
    }

    public void b(Exception exc) {
        if (a()) {
            a(exc);
        }
    }

    public void b(Object obj) {
        if (a()) {
            a(obj);
        }
    }

    public void c(Object obj) {
        if (this.c <= 5) {
            String strB = b();
            Log.w(this.a, strB == null ? obj.toString() : strB + " - " + obj);
        }
    }

    public void d(Object obj) {
        if (a()) {
            c(obj);
        }
    }

    public void e(Object obj) {
        if (this.c <= 6) {
            String strB = b();
            Log.e(this.a, strB == null ? obj.toString() : strB + " - " + obj);
        }
    }

    public void f(Object obj) {
        if (a()) {
            e(obj);
        }
    }

    public void g(Object obj) {
        if (this.c <= 3) {
            String strB = b();
            Log.d(this.a, strB == null ? obj.toString() : strB + " - " + obj);
        }
    }

    public void h(Object obj) {
        if (a()) {
            g(obj);
        }
    }
}
