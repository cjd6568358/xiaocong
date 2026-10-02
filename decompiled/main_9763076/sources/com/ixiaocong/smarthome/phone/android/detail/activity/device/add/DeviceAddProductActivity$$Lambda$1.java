package com.ixiaocong.smarthome.phone.android.detail.activity.device.add;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceAddProductActivity$$Lambda$1 implements View.OnClickListener {
    private final DeviceAddProductActivity arg$1;

    private DeviceAddProductActivity$$Lambda$1(DeviceAddProductActivity deviceAddProductActivity) {
        this.arg$1 = deviceAddProductActivity;
    }

    public static View.OnClickListener lambdaFactory$(DeviceAddProductActivity deviceAddProductActivity) {
        return new DeviceAddProductActivity$$Lambda$1(deviceAddProductActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
