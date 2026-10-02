package com.ixiaocong.smarthome.phone.android.detail.activity.ifttt;

import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class WorkdayIftttSettingActivity$1$$Lambda$1 implements CompoundButton.OnCheckedChangeListener {
    private final WorkdayIftttSettingActivity.AnonymousClass1 arg$1;
    private final int arg$2;

    private WorkdayIftttSettingActivity$1$$Lambda$1(WorkdayIftttSettingActivity.AnonymousClass1 anonymousClass1, int i) {
        this.arg$1 = anonymousClass1;
        this.arg$2 = i;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(WorkdayIftttSettingActivity.AnonymousClass1 anonymousClass1, int i) {
        return new WorkdayIftttSettingActivity$1$$Lambda$1(anonymousClass1, i);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$onSimpleItemChildClick$0(this.arg$2, compoundButton, z);
    }
}
