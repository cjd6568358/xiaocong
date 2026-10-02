package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddZigbeeActivity$ZigbeeAddCountDown$$Lambda$1 implements Runnable {
    private final DeviceAddZigbeeActivity.ZigbeeAddCountDown arg$1;
    private final long arg$2;

    private DeviceAddZigbeeActivity$ZigbeeAddCountDown$$Lambda$1(DeviceAddZigbeeActivity.ZigbeeAddCountDown zigbeeAddCountDown, long j) {
        this.arg$1 = zigbeeAddCountDown;
        this.arg$2 = j;
    }

    public static Runnable lambdaFactory$(DeviceAddZigbeeActivity.ZigbeeAddCountDown zigbeeAddCountDown, long j) {
        return new DeviceAddZigbeeActivity$ZigbeeAddCountDown$$Lambda$1(zigbeeAddCountDown, j);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$onTick$0(this.arg$2);
    }
}
