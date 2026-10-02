package com.ixiaocong.smarthome.phone.android.detail.activity.device.config.xconfig;

import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddXConfigActivity$1$$Lambda$1 implements HintDialogCallback {
    private final DeviceAddXConfigActivity arg$1;

    private DeviceAddXConfigActivity$1$$Lambda$1(DeviceAddXConfigActivity deviceAddXConfigActivity) {
        this.arg$1 = deviceAddXConfigActivity;
    }

    public static HintDialogCallback lambdaFactory$(DeviceAddXConfigActivity deviceAddXConfigActivity) {
        return new DeviceAddXConfigActivity$1$$Lambda$1(deviceAddXConfigActivity);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    @LambdaForm.Hidden
    public void hintDialogListener(boolean z) {
        this.arg$1.hintDialogListener(z);
    }
}
