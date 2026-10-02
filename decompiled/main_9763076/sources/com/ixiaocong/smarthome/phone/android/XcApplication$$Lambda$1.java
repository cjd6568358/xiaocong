package com.ixiaocong.smarthome.phone.android;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class XcApplication$$Lambda$1 implements Runnable {
    private final XcApplication arg$1;

    private XcApplication$$Lambda$1(XcApplication xcApplication) {
        this.arg$1 = xcApplication;
    }

    public static Runnable lambdaFactory$(XcApplication xcApplication) {
        return new XcApplication$$Lambda$1(xcApplication);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$onCreate$0();
    }
}
