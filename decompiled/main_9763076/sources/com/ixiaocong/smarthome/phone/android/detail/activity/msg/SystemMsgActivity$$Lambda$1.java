package com.ixiaocong.smarthome.phone.android.detail.activity.msg;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SystemMsgActivity$$Lambda$1 implements View.OnClickListener {
    private final SystemMsgActivity arg$1;

    private SystemMsgActivity$$Lambda$1(SystemMsgActivity systemMsgActivity) {
        this.arg$1 = systemMsgActivity;
    }

    public static View.OnClickListener lambdaFactory$(SystemMsgActivity systemMsgActivity) {
        return new SystemMsgActivity$$Lambda$1(systemMsgActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
