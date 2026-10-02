package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonAlarmActivity$$Lambda$2 implements View.OnClickListener {
    private final AoniCommonAlarmActivity arg$1;

    private AoniCommonAlarmActivity$$Lambda$2(AoniCommonAlarmActivity aoniCommonAlarmActivity) {
        this.arg$1 = aoniCommonAlarmActivity;
    }

    public static View.OnClickListener lambdaFactory$(AoniCommonAlarmActivity aoniCommonAlarmActivity) {
        return new AoniCommonAlarmActivity$$Lambda$2(aoniCommonAlarmActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
