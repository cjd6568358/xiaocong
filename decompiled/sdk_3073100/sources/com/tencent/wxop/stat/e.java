package com.tencent.wxop.stat;

import java.util.TimerTask;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class e extends TimerTask {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (StatConfig.isDebugEnable()) {
            com.tencent.wxop.stat.common.l.b().i("TimerTask run");
        }
        StatServiceImpl.e(this.a.c);
        cancel();
        this.a.a();
    }
}
