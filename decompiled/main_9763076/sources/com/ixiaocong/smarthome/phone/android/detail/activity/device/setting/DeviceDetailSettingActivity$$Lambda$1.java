package com.ixiaocong.smarthome.phone.android.detail.activity.device.setting;

import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceDetailSettingActivity$$Lambda$1 implements CompoundButton.OnCheckedChangeListener {
    private final DeviceDetailSettingActivity arg$1;

    private DeviceDetailSettingActivity$$Lambda$1(DeviceDetailSettingActivity deviceDetailSettingActivity) {
        this.arg$1 = deviceDetailSettingActivity;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(DeviceDetailSettingActivity deviceDetailSettingActivity) {
        return new DeviceDetailSettingActivity$$Lambda$1(deviceDetailSettingActivity);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$addListener$0(compoundButton, z);
    }
}
