package com.hzy.tvmao;

import com.hzy.tvmao.utils.LogUtil;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: TmAppThread.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class aa implements Runnable {
    private final /* synthetic */ String a;
    private final /* synthetic */ Runnable b;

    aa(String str, Runnable runnable) {
        this.a = str;
        this.b = runnable;
    }

    @Override // java.lang.Runnable
    public void run() {
        long jNanoTime = System.nanoTime();
        try {
            this.b.run();
        } catch (Throwable th) {
            LogUtil.e(th.toString());
        } finally {
            LogUtil.d(String.valueOf(this.a == null ? Constants.MAIN_VERSION_TAG : this.a) + " (" + ((System.nanoTime() - jNanoTime) / 1000000) + "ms)");
        }
    }
}
