package com.youzan.androidsdk.basic;

import android.content.Context;
import com.youzan.androidsdk.tool.AnalyticsUtil;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
class YouzanBrowser$1 implements Runnable {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    final /* synthetic */ Context f12;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    final /* synthetic */ YouzanBrowser f13;

    YouzanBrowser$1(YouzanBrowser this$0, Context context) {
        this.f13 = this$0;
        this.f12 = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        YouzanBrowser.ˊ(this.f13, true);
        AnalyticsUtil.statisticWebviewInit(this.f12);
    }
}
