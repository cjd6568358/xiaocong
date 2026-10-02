package com.ixiaocong.smarthome.phone.android;

import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class XcApplication$$Lambda$2 implements XGPushNotifactionCallback {
    private static final XcApplication$$Lambda$2 instance = new XcApplication$$Lambda$2();

    private XcApplication$$Lambda$2() {
    }

    @Override // com.tencent.android.tpush.XGPushNotifactionCallback
    @LambdaForm.Hidden
    public void handleNotify(XGNotifaction xGNotifaction) {
        XcApplication.lambda$initXgPush$1(xGNotifaction);
    }
}
