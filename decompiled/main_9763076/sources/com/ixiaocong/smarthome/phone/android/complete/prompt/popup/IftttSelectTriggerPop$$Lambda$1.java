package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class IftttSelectTriggerPop$$Lambda$1 implements View.OnClickListener {
    private final IftttSelectTriggerPop arg$1;

    private IftttSelectTriggerPop$$Lambda$1(IftttSelectTriggerPop iftttSelectTriggerPop) {
        this.arg$1 = iftttSelectTriggerPop;
    }

    public static View.OnClickListener lambdaFactory$(IftttSelectTriggerPop iftttSelectTriggerPop) {
        return new IftttSelectTriggerPop$$Lambda$1(iftttSelectTriggerPop);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showPop$0(view);
    }
}
