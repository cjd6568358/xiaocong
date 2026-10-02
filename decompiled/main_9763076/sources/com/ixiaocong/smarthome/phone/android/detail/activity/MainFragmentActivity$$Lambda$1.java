package com.ixiaocong.smarthome.phone.android.detail.activity;

import android.widget.TabHost;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class MainFragmentActivity$$Lambda$1 implements TabHost.OnTabChangeListener {
    private final MainFragmentActivity arg$1;

    private MainFragmentActivity$$Lambda$1(MainFragmentActivity mainFragmentActivity) {
        this.arg$1 = mainFragmentActivity;
    }

    public static TabHost.OnTabChangeListener lambdaFactory$(MainFragmentActivity mainFragmentActivity) {
        return new MainFragmentActivity$$Lambda$1(mainFragmentActivity);
    }

    @Override // android.widget.TabHost.OnTabChangeListener
    @LambdaForm.Hidden
    public void onTabChanged(String str) {
        this.arg$1.lambda$addListener$0(str);
    }
}
