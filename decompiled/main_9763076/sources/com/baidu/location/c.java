package com.baidu.location;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c extends Thread {
    final /* synthetic */ LocationClient a;

    c(LocationClient locationClient) {
        this.a = locationClient;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (this.a.C == null) {
            this.a.C = new com.baidu.location.a.c(this.a.f, this.a.d, this.a);
        }
        this.a.C.c();
    }
}
