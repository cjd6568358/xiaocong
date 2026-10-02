package com.ixiaocong.smarthome.phone.android.detail.activity.device.config;

import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddNetworkActivity$$Lambda$1 implements CompoundButton.OnCheckedChangeListener {
    private final DeviceAddNetworkActivity arg$1;

    private DeviceAddNetworkActivity$$Lambda$1(DeviceAddNetworkActivity deviceAddNetworkActivity) {
        this.arg$1 = deviceAddNetworkActivity;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(DeviceAddNetworkActivity deviceAddNetworkActivity) {
        return new DeviceAddNetworkActivity$$Lambda$1(deviceAddNetworkActivity);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$addListener$0(compoundButton, z);
    }
}
