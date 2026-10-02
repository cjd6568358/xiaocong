package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonAlarmActivity$$Lambda$1 implements CommonTypeCallback {
    private final AoniCommonAlarmActivity arg$1;

    private AoniCommonAlarmActivity$$Lambda$1(AoniCommonAlarmActivity aoniCommonAlarmActivity) {
        this.arg$1 = aoniCommonAlarmActivity;
    }

    public static CommonTypeCallback lambdaFactory$(AoniCommonAlarmActivity aoniCommonAlarmActivity) {
        return new AoniCommonAlarmActivity$$Lambda$1(aoniCommonAlarmActivity);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    @LambdaForm.Hidden
    public void resultTypeCalllback(int i) {
        this.arg$1.resultTypeCalllback(i);
    }
}
