package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CommonBottomPop$$Lambda$3 implements View.OnClickListener {
    private final CommonBottomPop arg$1;

    private CommonBottomPop$$Lambda$3(CommonBottomPop commonBottomPop) {
        this.arg$1 = commonBottomPop;
    }

    public static View.OnClickListener lambdaFactory$(CommonBottomPop commonBottomPop) {
        return new CommonBottomPop$$Lambda$3(commonBottomPop);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showCommonBootomPopup$2(view);
    }
}
