package com.tencent.wxop.stat;

import android.content.Context;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
final class al implements Runnable {
    final /* synthetic */ StatAccount a;
    final /* synthetic */ Context b;
    final /* synthetic */ StatSpecifyReportedInfo c;

    al(StatAccount statAccount, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = statAccount;
        this.b = context;
        this.c = statSpecifyReportedInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.a == null || this.a.getAccount().trim().length() == 0) {
            StatServiceImpl.q.w("account is null or empty.");
        } else {
            StatConfig.setQQ(this.b, this.a.getAccount());
            StatServiceImpl.b(this.b, this.a, this.c);
        }
    }
}
