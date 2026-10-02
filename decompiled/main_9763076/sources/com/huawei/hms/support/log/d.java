package com.huawei.hms.support.log;

import android.os.Process;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* JADX INFO: compiled from: LogRecord.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d {
    private String a;
    private String b;
    private int c;
    private String f;
    private int g;
    private int h;
    private int i;
    private long d = 0;
    private long e = 0;
    private final StringBuilder j = new StringBuilder();

    d(int i, String str, int i2, String str2) {
        this.a = null;
        this.b = "HMS";
        this.c = 0;
        this.i = 0;
        this.i = i;
        this.a = str;
        this.c = i2;
        if (str2 != null) {
            this.b = str2;
        }
        c();
    }

    private d c() {
        this.d = System.currentTimeMillis();
        Thread threadCurrentThread = Thread.currentThread();
        this.e = threadCurrentThread.getId();
        this.g = Process.myPid();
        StackTraceElement[] stackTrace = threadCurrentThread.getStackTrace();
        if (stackTrace.length > this.i) {
            StackTraceElement stackTraceElement = stackTrace[this.i];
            this.f = stackTraceElement.getFileName();
            this.h = stackTraceElement.getLineNumber();
        }
        return this;
    }

    public <T> d a(T t) {
        this.j.append(t);
        return this;
    }

    public d a(Throwable th) {
        a('\n').a(Log.getStackTraceString(th));
        return this;
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        a(sb);
        return sb.toString();
    }

    private StringBuilder a(StringBuilder sb) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        sb.append('[');
        sb.append(simpleDateFormat.format(Long.valueOf(this.d)));
        sb.append(' ').append(a(this.c)).append('/').append(this.b).append('/').append(this.a);
        sb.append(' ').append(this.g).append(':').append(this.e);
        sb.append(' ').append(this.f).append(':').append(this.h);
        sb.append(']');
        return sb;
    }

    public static String a(int i) {
        switch (i) {
            case 3:
                return "D";
            case 4:
                return "I";
            case 5:
                return "W";
            case 6:
                return "E";
            default:
                return String.valueOf(i);
        }
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        b(sb);
        return sb.toString();
    }

    private StringBuilder b(StringBuilder sb) {
        sb.append(' ').append(this.j.toString());
        return sb;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        a(sb);
        b(sb);
        return sb.toString();
    }
}
