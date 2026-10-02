package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.softAp;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddSoftApActivity$$Lambda$1 implements Runnable {
    private final DeviceAddSoftApActivity arg$1;
    private final int arg$2;

    private DeviceAddSoftApActivity$$Lambda$1(DeviceAddSoftApActivity deviceAddSoftApActivity, int i) {
        this.arg$1 = deviceAddSoftApActivity;
        this.arg$2 = i;
    }

    public static Runnable lambdaFactory$(DeviceAddSoftApActivity deviceAddSoftApActivity, int i) {
        return new DeviceAddSoftApActivity$$Lambda$1(deviceAddSoftApActivity, i);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$startAnimation$0(this.arg$2);
    }
}
