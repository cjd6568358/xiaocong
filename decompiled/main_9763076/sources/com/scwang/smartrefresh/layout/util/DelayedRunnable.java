package com.scwang.smartrefresh.layout.util;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DelayedRunnable implements Runnable {
    public long delayMillis;
    public Runnable runnable;

    public DelayedRunnable(Runnable runnable) {
        this.runnable = null;
        this.runnable = runnable;
    }

    public DelayedRunnable(Runnable runnable, long delayMillis) {
        this.runnable = null;
        this.runnable = runnable;
        this.delayMillis = delayMillis;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.runnable != null) {
                this.runnable.run();
                this.runnable = null;
            }
        } catch (Throwable e) {
            if (!(e instanceof NoClassDefFoundError)) {
                e.printStackTrace();
            }
        }
    }
}
