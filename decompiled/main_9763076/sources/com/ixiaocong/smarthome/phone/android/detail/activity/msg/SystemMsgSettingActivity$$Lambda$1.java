package com.ixiaocong.smarthome.phone.android.detail.activity.msg;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SystemMsgSettingActivity$$Lambda$1 implements View.OnClickListener {
    private final SystemMsgSettingActivity arg$1;

    private SystemMsgSettingActivity$$Lambda$1(SystemMsgSettingActivity systemMsgSettingActivity) {
        this.arg$1 = systemMsgSettingActivity;
    }

    public static View.OnClickListener lambdaFactory$(SystemMsgSettingActivity systemMsgSettingActivity) {
        return new SystemMsgSettingActivity$$Lambda$1(systemMsgSettingActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
