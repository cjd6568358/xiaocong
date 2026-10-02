package com.ixiaocong.smarthome.phone.android.detail.activity.system;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AboutXiaocongActivity$$Lambda$1 implements View.OnClickListener {
    private final AboutXiaocongActivity arg$1;

    private AboutXiaocongActivity$$Lambda$1(AboutXiaocongActivity aboutXiaocongActivity) {
        this.arg$1 = aboutXiaocongActivity;
    }

    public static View.OnClickListener lambdaFactory$(AboutXiaocongActivity aboutXiaocongActivity) {
        return new AboutXiaocongActivity$$Lambda$1(aboutXiaocongActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
