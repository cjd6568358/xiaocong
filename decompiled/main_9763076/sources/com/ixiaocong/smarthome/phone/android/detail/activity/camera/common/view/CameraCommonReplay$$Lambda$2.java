package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CameraCommonReplay$$Lambda$2 implements CompoundButton.OnCheckedChangeListener {
    private final CameraCommonReplay arg$1;

    private CameraCommonReplay$$Lambda$2(CameraCommonReplay cameraCommonReplay) {
        this.arg$1 = cameraCommonReplay;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(CameraCommonReplay cameraCommonReplay) {
        return new CameraCommonReplay$$Lambda$2(cameraCommonReplay);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$addListener$1(compoundButton, z);
    }
}
