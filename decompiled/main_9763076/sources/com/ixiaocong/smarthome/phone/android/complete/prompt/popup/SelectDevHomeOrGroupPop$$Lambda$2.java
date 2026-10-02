package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SelectDevHomeOrGroupPop$$Lambda$2 implements View.OnClickListener {
    private final SelectDevHomeOrGroupPop arg$1;

    private SelectDevHomeOrGroupPop$$Lambda$2(SelectDevHomeOrGroupPop selectDevHomeOrGroupPop) {
        this.arg$1 = selectDevHomeOrGroupPop;
    }

    public static View.OnClickListener lambdaFactory$(SelectDevHomeOrGroupPop selectDevHomeOrGroupPop) {
        return new SelectDevHomeOrGroupPop$$Lambda$2(selectDevHomeOrGroupPop);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showPop$1(view);
    }
}
