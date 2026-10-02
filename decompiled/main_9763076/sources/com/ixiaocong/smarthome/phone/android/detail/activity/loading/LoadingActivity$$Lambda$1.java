package com.ixiaocong.smarthome.phone.android.detail.activity.loading;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class LoadingActivity$$Lambda$1 implements Runnable {
    private final LoadingActivity arg$1;

    private LoadingActivity$$Lambda$1(LoadingActivity loadingActivity) {
        this.arg$1 = loadingActivity;
    }

    public static Runnable lambdaFactory$(LoadingActivity loadingActivity) {
        return new LoadingActivity$$Lambda$1(loadingActivity);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$requestClient$0();
    }
}
