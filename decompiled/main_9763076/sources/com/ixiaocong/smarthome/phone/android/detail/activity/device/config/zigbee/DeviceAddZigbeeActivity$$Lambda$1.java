package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.zigbee;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddZigbeeActivity$$Lambda$1 implements View.OnClickListener {
    private final DeviceAddZigbeeActivity arg$1;

    private DeviceAddZigbeeActivity$$Lambda$1(DeviceAddZigbeeActivity deviceAddZigbeeActivity) {
        this.arg$1 = deviceAddZigbeeActivity;
    }

    public static View.OnClickListener lambdaFactory$(DeviceAddZigbeeActivity deviceAddZigbeeActivity) {
        return new DeviceAddZigbeeActivity$$Lambda$1(deviceAddZigbeeActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
