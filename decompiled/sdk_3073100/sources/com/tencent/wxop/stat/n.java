package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class n implements Runnable {
    final /* synthetic */ Context a;

    n(Context context) {
        this.a = context;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.a == null) {
            StatServiceImpl.q.error("The Context of StatService.onStop() can not be null!");
            return;
        }
        StatServiceImpl.flushDataToDB(this.a);
        if (StatServiceImpl.a()) {
            return;
        }
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (com.tencent.wxop.stat.common.l.z(this.a)) {
            if (StatConfig.isDebugEnable()) {
                StatServiceImpl.q.i("onStop isBackgroundRunning flushDataToDB");
            }
            StatServiceImpl.commitEvents(this.a, -1);
        }
    }
}
