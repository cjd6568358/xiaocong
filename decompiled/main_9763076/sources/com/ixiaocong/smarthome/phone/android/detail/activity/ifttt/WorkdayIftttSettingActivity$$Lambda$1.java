package com.ixiaocong.smarthome.phone.android.detail.activity.ifttt;

import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class WorkdayIftttSettingActivity$$Lambda$1 implements CompoundButton.OnCheckedChangeListener {
    private final WorkdayIftttSettingActivity arg$1;

    private WorkdayIftttSettingActivity$$Lambda$1(WorkdayIftttSettingActivity workdayIftttSettingActivity) {
        this.arg$1 = workdayIftttSettingActivity;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(WorkdayIftttSettingActivity workdayIftttSettingActivity) {
        return new WorkdayIftttSettingActivity$$Lambda$1(workdayIftttSettingActivity);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$addHeader$0(compoundButton, z);
    }
}
