package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonHistoryVideoActivity$$Lambda$1 implements CommonTypeCallback {
    private final AoniCommonHistoryVideoActivity arg$1;

    private AoniCommonHistoryVideoActivity$$Lambda$1(AoniCommonHistoryVideoActivity aoniCommonHistoryVideoActivity) {
        this.arg$1 = aoniCommonHistoryVideoActivity;
    }

    public static CommonTypeCallback lambdaFactory$(AoniCommonHistoryVideoActivity aoniCommonHistoryVideoActivity) {
        return new AoniCommonHistoryVideoActivity$$Lambda$1(aoniCommonHistoryVideoActivity);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    @LambdaForm.Hidden
    public void resultTypeCalllback(int i) {
        this.arg$1.resultTypeCalllback(i);
    }
}
