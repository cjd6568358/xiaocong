package com.ixiaocong.smarthome.phone.android.detail.activity.ifttt;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class WorkdayIftttSettingActivity$$Lambda$3 implements View.OnClickListener {
    private final WorkdayIftttSettingActivity arg$1;

    private WorkdayIftttSettingActivity$$Lambda$3(WorkdayIftttSettingActivity workdayIftttSettingActivity) {
        this.arg$1 = workdayIftttSettingActivity;
    }

    public static View.OnClickListener lambdaFactory$(WorkdayIftttSettingActivity workdayIftttSettingActivity) {
        return new WorkdayIftttSettingActivity$$Lambda$3(workdayIftttSettingActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$2(view);
    }
}
