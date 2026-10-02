package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common.view;

import java.lang.invoke.LambdaForm;
import org.json.JSONException;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CameraCommonLive$4$$Lambda$1 implements Runnable {
    private final CameraCommonLive.AnonymousClass4 arg$1;
    private final String arg$2;

    private CameraCommonLive$4$$Lambda$1(CameraCommonLive.AnonymousClass4 anonymousClass4, String str) {
        this.arg$1 = anonymousClass4;
        this.arg$2 = str;
    }

    public static Runnable lambdaFactory$(CameraCommonLive.AnonymousClass4 anonymousClass4, String str) {
        return new CameraCommonLive$4$$Lambda$1(anonymousClass4, str);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() throws JSONException {
        this.arg$1.lambda$onReceiveDeviceSnapshot$0(this.arg$2);
    }
}
