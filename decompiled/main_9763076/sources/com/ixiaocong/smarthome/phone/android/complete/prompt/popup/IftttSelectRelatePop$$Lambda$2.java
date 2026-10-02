package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class IftttSelectRelatePop$$Lambda$2 implements View.OnClickListener {
    private final IftttSelectRelatePop arg$1;
    private final Context arg$2;

    private IftttSelectRelatePop$$Lambda$2(IftttSelectRelatePop iftttSelectRelatePop, Context context) {
        this.arg$1 = iftttSelectRelatePop;
        this.arg$2 = context;
    }

    public static View.OnClickListener lambdaFactory$(IftttSelectRelatePop iftttSelectRelatePop, Context context) {
        return new IftttSelectRelatePop$$Lambda$2(iftttSelectRelatePop, context);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initPopListener$1(this.arg$2, view);
    }
}
