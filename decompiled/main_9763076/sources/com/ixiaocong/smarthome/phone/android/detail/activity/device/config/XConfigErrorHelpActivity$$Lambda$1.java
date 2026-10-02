package com.ixiaocong.smarthome.phone.android.detail.activity.device.config;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class XConfigErrorHelpActivity$$Lambda$1 implements View.OnClickListener {
    private final XConfigErrorHelpActivity arg$1;

    private XConfigErrorHelpActivity$$Lambda$1(XConfigErrorHelpActivity xConfigErrorHelpActivity) {
        this.arg$1 = xConfigErrorHelpActivity;
    }

    public static View.OnClickListener lambdaFactory$(XConfigErrorHelpActivity xConfigErrorHelpActivity) {
        return new XConfigErrorHelpActivity$$Lambda$1(xConfigErrorHelpActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
