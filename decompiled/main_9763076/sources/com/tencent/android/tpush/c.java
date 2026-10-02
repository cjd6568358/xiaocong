package com.tencent.android.tpush;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements Runnable {
    Message a;
    final /* synthetic */ XGDownloadService b;
    private Intent c;
    private int d;

    public c(XGDownloadService xGDownloadService, Intent intent, int i) {
        this.b = xGDownloadService;
        this.a = this.b.j.obtainMessage();
        this.c = intent;
        this.d = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.what = 0;
        this.a.arg1 = this.d;
        new Bundle();
        this.a.setData(this.c.getExtras());
        try {
            if (!this.b.d.exists()) {
                this.b.d.mkdirs();
            }
            if (!this.b.e.exists()) {
                this.b.e.createNewFile();
            }
            if (this.b.a(this.b.b, this.b.e, this.d) > 0) {
                this.b.j.sendMessage(this.a);
            }
        } catch (Exception e) {
            com.tencent.android.tpush.a.a.c(XGDownloadService.c, "downloadRunnable", e);
            this.b.j.sendMessage(this.a);
        }
    }
}
