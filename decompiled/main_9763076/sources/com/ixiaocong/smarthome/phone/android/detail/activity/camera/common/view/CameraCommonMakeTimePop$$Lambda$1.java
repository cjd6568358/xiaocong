package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CameraCommonMakeTimePop$$Lambda$1 implements View.OnClickListener {
    private final CameraCommonMakeTimePop arg$1;

    private CameraCommonMakeTimePop$$Lambda$1(CameraCommonMakeTimePop cameraCommonMakeTimePop) {
        this.arg$1 = cameraCommonMakeTimePop;
    }

    public static View.OnClickListener lambdaFactory$(CameraCommonMakeTimePop cameraCommonMakeTimePop) {
        return new CameraCommonMakeTimePop$$Lambda$1(cameraCommonMakeTimePop);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
