package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.xconfig;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddXConfigActivity$$Lambda$1 implements Runnable {
    private final DeviceAddXConfigActivity arg$1;
    private final int arg$2;

    private DeviceAddXConfigActivity$$Lambda$1(DeviceAddXConfigActivity deviceAddXConfigActivity, int i) {
        this.arg$1 = deviceAddXConfigActivity;
        this.arg$2 = i;
    }

    public static Runnable lambdaFactory$(DeviceAddXConfigActivity deviceAddXConfigActivity, int i) {
        return new DeviceAddXConfigActivity$$Lambda$1(deviceAddXConfigActivity, i);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$startAnimation$0(this.arg$2);
    }
}
