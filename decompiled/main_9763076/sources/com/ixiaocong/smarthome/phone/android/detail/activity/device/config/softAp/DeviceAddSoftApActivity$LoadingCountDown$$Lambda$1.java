package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.softAp;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddSoftApActivity$LoadingCountDown$$Lambda$1 implements Runnable {
    private final DeviceAddSoftApActivity.LoadingCountDown arg$1;
    private final long arg$2;

    private DeviceAddSoftApActivity$LoadingCountDown$$Lambda$1(DeviceAddSoftApActivity.LoadingCountDown loadingCountDown, long j) {
        this.arg$1 = loadingCountDown;
        this.arg$2 = j;
    }

    public static Runnable lambdaFactory$(DeviceAddSoftApActivity.LoadingCountDown loadingCountDown, long j) {
        return new DeviceAddSoftApActivity$LoadingCountDown$$Lambda$1(loadingCountDown, j);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$onTick$0(this.arg$2);
    }
}
