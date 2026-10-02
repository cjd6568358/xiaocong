package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CameraCommonLive$4$$Lambda$2 implements Runnable {
    private final CameraCommonLive.AnonymousClass4 arg$1;
    private final int arg$2;

    private CameraCommonLive$4$$Lambda$2(CameraCommonLive.AnonymousClass4 anonymousClass4, int i) {
        this.arg$1 = anonymousClass4;
        this.arg$2 = i;
    }

    public static Runnable lambdaFactory$(CameraCommonLive.AnonymousClass4 anonymousClass4, int i) {
        return new CameraCommonLive$4$$Lambda$2(anonymousClass4, i);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$onReceiveDeviceStatus$1(this.arg$2);
    }
}
