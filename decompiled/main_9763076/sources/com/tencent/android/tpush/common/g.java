package com.tencent.android.tpush.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class g {
    private static HandlerThread a = null;
    private static Handler b = null;

    private g() {
    }

    public static g a() {
        c();
        return i.a;
    }

    public boolean a(Runnable runnable) {
        if (b != null) {
            return b.post(runnable);
        }
        return false;
    }

    public boolean a(Runnable runnable, long j) {
        if (b != null) {
            return b.postDelayed(runnable, j);
        }
        return false;
    }

    public boolean a(Runnable runnable, int i, long j) {
        if (b != null) {
            return b.postAtTime(runnable, Integer.valueOf(i), SystemClock.uptimeMillis() + j);
        }
        return false;
    }

    public void a(int i) {
        if (b != null) {
            b.removeCallbacksAndMessages(Integer.valueOf(i));
        }
    }

    public Handler b() {
        return b;
    }

    private static void c() {
        try {
            if (a == null || !a.isAlive() || a.isInterrupted() || a.getState() == Thread.State.TERMINATED) {
                a = new HandlerThread("tpush.working.thread");
                a.start();
                Looper looper = a.getLooper();
                if (looper != null) {
                    b = new Handler(looper);
                } else {
                    com.tencent.android.tpush.a.a.i("CommonWorkingThread", ">>> Create new working thread false, cause thread.getLooper()==null");
                }
            }
        } catch (Throwable th) {
        }
    }
}
