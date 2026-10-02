package com.tencent.android.tpush.service;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ac implements Runnable {
    final /* synthetic */ XGWatchdog a;

    ac(XGWatchdog xGWatchdog) {
        this.a = xGWatchdog;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            String strDirectSendContent = this.a.directSendContent("ver:");
            Integer numValueOf = 0;
            if (strDirectSendContent != null) {
                try {
                    numValueOf = Integer.valueOf(strDirectSendContent);
                } catch (NumberFormatException e) {
                }
            }
            if (numValueOf.intValue() <= 2) {
                this.a.directSendContent("exit:");
                this.a.directSendContent("exit1:");
                this.a.directSendContent("exit2:");
                Thread.sleep(5000L);
                this.a.directStartWatchdog();
            }
            this.a.isStarted = true;
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i(XGWatchdog.TAG, "jniStartWatchdog error:" + th.getMessage());
        }
    }
}
