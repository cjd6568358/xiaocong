package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CameraCommonReplay$$Lambda$3 implements View.OnClickListener {
    private final CameraCommonReplay arg$1;

    private CameraCommonReplay$$Lambda$3(CameraCommonReplay cameraCommonReplay) {
        this.arg$1 = cameraCommonReplay;
    }

    public static View.OnClickListener lambdaFactory$(CameraCommonReplay cameraCommonReplay) {
        return new CameraCommonReplay$$Lambda$3(cameraCommonReplay);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$2(view);
    }
}
