package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CommonBottomSecondPop$$Lambda$3 implements View.OnClickListener {
    private final CommonBottomSecondPop arg$1;

    private CommonBottomSecondPop$$Lambda$3(CommonBottomSecondPop commonBottomSecondPop) {
        this.arg$1 = commonBottomSecondPop;
    }

    public static View.OnClickListener lambdaFactory$(CommonBottomSecondPop commonBottomSecondPop) {
        return new CommonBottomSecondPop$$Lambda$3(commonBottomSecondPop);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showCommonBootomPopup$2(view);
    }
}
