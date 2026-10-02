package com.tencent.mid.a;

import android.os.Handler;
import android.os.HandlerThread;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class n {
    private static n a = null;
    private Handler b;

    private n() {
        this.b = null;
        HandlerThread handlerThread = new HandlerThread("MidWorkThread");
        handlerThread.start();
        this.b = new Handler(handlerThread.getLooper());
    }

    public static n a() {
        if (a == null) {
            synchronized (n.class) {
                if (a == null) {
                    a = new n();
                }
            }
        }
        return a;
    }

    public void a(Runnable runnable) {
        if (this.b != null) {
            this.b.post(runnable);
        }
    }
}
