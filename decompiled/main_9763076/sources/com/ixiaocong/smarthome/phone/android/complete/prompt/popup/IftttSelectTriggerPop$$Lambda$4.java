package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class IftttSelectTriggerPop$$Lambda$4 implements View.OnClickListener {
    private final IftttSelectTriggerPop arg$1;
    private final Context arg$2;

    private IftttSelectTriggerPop$$Lambda$4(IftttSelectTriggerPop iftttSelectTriggerPop, Context context) {
        this.arg$1 = iftttSelectTriggerPop;
        this.arg$2 = context;
    }

    public static View.OnClickListener lambdaFactory$(IftttSelectTriggerPop iftttSelectTriggerPop, Context context) {
        return new IftttSelectTriggerPop$$Lambda$4(iftttSelectTriggerPop, context);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initPopListener$1(this.arg$2, view);
    }
}
