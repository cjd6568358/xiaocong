package com.ixiaocong.smarthome.phone.android.detail.activity.device.config;

import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddStateActivity$2$$Lambda$1 implements CommonTypeCallback {
    private final DeviceAddStateActivity arg$1;

    private DeviceAddStateActivity$2$$Lambda$1(DeviceAddStateActivity deviceAddStateActivity) {
        this.arg$1 = deviceAddStateActivity;
    }

    public static CommonTypeCallback lambdaFactory$(DeviceAddStateActivity deviceAddStateActivity) {
        return new DeviceAddStateActivity$2$$Lambda$1(deviceAddStateActivity);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    @LambdaForm.Hidden
    public void resultTypeCalllback(int i) {
        this.arg$1.resultTypeCalllback(i);
    }
}
