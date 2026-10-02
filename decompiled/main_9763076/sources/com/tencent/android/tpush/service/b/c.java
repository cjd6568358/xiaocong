package com.tencent.android.tpush.service.b;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements Runnable {
    private String a;
    private String b;

    public c(String str) {
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            a(b.i(this.a));
        } catch (Exception e) {
        }
    }

    public synchronized void a(String str) {
        this.b = str;
    }

    public synchronized String a() {
        return this.b;
    }
}
