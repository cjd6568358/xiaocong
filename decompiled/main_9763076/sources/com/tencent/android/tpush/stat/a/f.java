package com.tencent.android.tpush.stat.a;

import android.util.Log;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class f {
    private String a;
    private boolean b;
    private int c;

    public boolean a() {
        return this.b;
    }

    public void a(boolean z) {
        this.b = z;
    }

    public f() {
        this.a = "default";
        this.b = true;
        this.c = 2;
    }

    public f(String str) {
        this.a = "default";
        this.b = true;
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

    public void a(Object obj) {
        if (this.c <= 4) {
            String strB = b();
            Log.i(this.a, strB == null ? obj.toString() : strB + " - " + obj);
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

    public void a(Throwable th) {
        if (this.c <= 6) {
            Log.e(this.a, Constants.MAIN_VERSION_TAG, th);
        }
    }

    public void f(Object obj) {
        if (a()) {
            e(obj);
        }
    }

    public void b(Throwable th) {
        if (a()) {
            a(th);
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
