package com.baidu.mobstat;

import android.content.Context;
import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class cb extends TimerTask {
    final /* synthetic */ Context a;
    final /* synthetic */ by b;

    cb(by byVar, Context context) {
        this.b = byVar;
        this.a = context;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (!DataCore.instance().isPartEmpty()) {
            this.b.c(this.a);
        }
    }
}
