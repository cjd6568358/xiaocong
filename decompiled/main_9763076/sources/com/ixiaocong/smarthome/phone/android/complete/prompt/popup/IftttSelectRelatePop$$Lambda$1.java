package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class IftttSelectRelatePop$$Lambda$1 implements View.OnClickListener {
    private final IftttSelectRelatePop arg$1;

    private IftttSelectRelatePop$$Lambda$1(IftttSelectRelatePop iftttSelectRelatePop) {
        this.arg$1 = iftttSelectRelatePop;
    }

    public static View.OnClickListener lambdaFactory$(IftttSelectRelatePop iftttSelectRelatePop) {
        return new IftttSelectRelatePop$$Lambda$1(iftttSelectRelatePop);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$selectSceneOrDevice$0(view);
    }
}
