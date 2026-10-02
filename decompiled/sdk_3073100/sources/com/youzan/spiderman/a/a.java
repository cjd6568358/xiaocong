package com.youzan.spiderman.a;

/* JADX INFO: compiled from: Job.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class a implements Runnable {
    public abstract void a() throws Throwable;

    public abstract void a(Throwable th);

    @Override // java.lang.Runnable
    public void run() {
        try {
            a();
        } catch (Throwable throwable) {
            a(throwable);
        }
    }
}
