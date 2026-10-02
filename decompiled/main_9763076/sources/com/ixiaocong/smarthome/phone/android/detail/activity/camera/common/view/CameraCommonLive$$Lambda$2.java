package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CameraCommonLive$$Lambda$2 implements CommonTypeCallback {
    private final CameraCommonLive arg$1;

    private CameraCommonLive$$Lambda$2(CameraCommonLive cameraCommonLive) {
        this.arg$1 = cameraCommonLive;
    }

    public static CommonTypeCallback lambdaFactory$(CameraCommonLive cameraCommonLive) {
        return new CameraCommonLive$$Lambda$2(cameraCommonLive);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    @LambdaForm.Hidden
    public void resultTypeCalllback(int i) {
        this.arg$1.resultTypeCalllback(i);
    }
}
