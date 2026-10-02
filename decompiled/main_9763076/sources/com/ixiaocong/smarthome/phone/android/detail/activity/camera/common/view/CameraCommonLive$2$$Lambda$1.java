package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.xiaocong.smarthome.httplib.model.DeviceSdkCheckModel;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CameraCommonLive$2$$Lambda$1 implements HintDialogCallback {
    private final CameraCommonLive.AnonymousClass2 arg$1;
    private final DeviceSdkCheckModel arg$2;

    private CameraCommonLive$2$$Lambda$1(CameraCommonLive.AnonymousClass2 anonymousClass2, DeviceSdkCheckModel deviceSdkCheckModel) {
        this.arg$1 = anonymousClass2;
        this.arg$2 = deviceSdkCheckModel;
    }

    public static HintDialogCallback lambdaFactory$(CameraCommonLive.AnonymousClass2 anonymousClass2, DeviceSdkCheckModel deviceSdkCheckModel) {
        return new CameraCommonLive$2$$Lambda$1(anonymousClass2, deviceSdkCheckModel);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback
    @LambdaForm.Hidden
    public void hintDialogListener(boolean z) {
        this.arg$1.lambda$onComplete$0(this.arg$2, z);
    }
}
