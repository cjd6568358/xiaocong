package com.hzy.tvmao;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* JADX INFO: compiled from: TmAppThread.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class z {
    private static Handler a;
    private static Handler b;
    private static boolean c;

    public static void a() {
        if (!c) {
            c = true;
            HandlerThread handlerThread = new HandlerThread("bkgdThread", 10);
            handlerThread.start();
            a = new Handler(Looper.getMainLooper());
            b = new Handler(handlerThread.getLooper());
        }
    }

    public static final void a(Runnable runnable) {
        a("TRK Thread posting..", runnable, 0L);
    }

    public static final void a(String str, Runnable runnable, long j) {
        a(b, str, runnable, j);
    }

    private static final void a(Handler handler, String str, Runnable runnable, long j) {
        if (handler != null) {
            handler.postDelayed(new aa(str, runnable), j);
        }
    }
}
