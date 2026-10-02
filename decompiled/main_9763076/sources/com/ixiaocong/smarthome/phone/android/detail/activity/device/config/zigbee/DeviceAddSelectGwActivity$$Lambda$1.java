package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddSelectGwActivity$$Lambda$1 implements View.OnClickListener {
    private final DeviceAddSelectGwActivity arg$1;

    private DeviceAddSelectGwActivity$$Lambda$1(DeviceAddSelectGwActivity deviceAddSelectGwActivity) {
        this.arg$1 = deviceAddSelectGwActivity;
    }

    public static View.OnClickListener lambdaFactory$(DeviceAddSelectGwActivity deviceAddSelectGwActivity) {
        return new DeviceAddSelectGwActivity$$Lambda$1(deviceAddSelectGwActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
