package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.softAp;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddSoftApActivity$$Lambda$2 implements Runnable {
    private final DeviceAddSoftApActivity arg$1;
    private final String arg$2;
    private final String arg$3;
    private final int arg$4;

    private DeviceAddSoftApActivity$$Lambda$2(DeviceAddSoftApActivity deviceAddSoftApActivity, String str, String str2, int i) {
        this.arg$1 = deviceAddSoftApActivity;
        this.arg$2 = str;
        this.arg$3 = str2;
        this.arg$4 = i;
    }

    public static Runnable lambdaFactory$(DeviceAddSoftApActivity deviceAddSoftApActivity, String str, String str2, int i) {
        return new DeviceAddSoftApActivity$$Lambda$2(deviceAddSoftApActivity, str, str2, i);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$xconfigCoapCallback$1(this.arg$2, this.arg$3, this.arg$4);
    }
}
