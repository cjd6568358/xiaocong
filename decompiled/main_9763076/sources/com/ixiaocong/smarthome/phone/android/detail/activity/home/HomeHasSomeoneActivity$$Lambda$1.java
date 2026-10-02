package com.ixiaocong.smarthome.phone.android.detail.activity.home;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class HomeHasSomeoneActivity$$Lambda$1 implements View.OnClickListener {
    private final HomeHasSomeoneActivity arg$1;

    private HomeHasSomeoneActivity$$Lambda$1(HomeHasSomeoneActivity homeHasSomeoneActivity) {
        this.arg$1 = homeHasSomeoneActivity;
    }

    public static View.OnClickListener lambdaFactory$(HomeHasSomeoneActivity homeHasSomeoneActivity) {
        return new HomeHasSomeoneActivity$$Lambda$1(homeHasSomeoneActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
