package com.ixiaocong.smarthome.phone.android.detail.activity.ifttt;

import com.xiaocong.smarthome.wheel.WheelView;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class WorkdayIftttSettingActivity$$Lambda$7 implements WheelView.OnItemSelectedListener {
    private final WorkdayIftttSettingActivity arg$1;

    private WorkdayIftttSettingActivity$$Lambda$7(WorkdayIftttSettingActivity workdayIftttSettingActivity) {
        this.arg$1 = workdayIftttSettingActivity;
    }

    public static WheelView.OnItemSelectedListener lambdaFactory$(WorkdayIftttSettingActivity workdayIftttSettingActivity) {
        return new WorkdayIftttSettingActivity$$Lambda$7(workdayIftttSettingActivity);
    }

    @LambdaForm.Hidden
    public void onItemSelected(int i, String str) {
        this.arg$1.lambda$addListener$6(i, str);
    }
}
