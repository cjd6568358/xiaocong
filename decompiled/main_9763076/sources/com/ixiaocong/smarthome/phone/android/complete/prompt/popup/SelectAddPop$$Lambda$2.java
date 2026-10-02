package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SelectAddPop$$Lambda$2 implements View.OnClickListener {
    private final SelectAddPop arg$1;
    private final CommonPopCallback arg$2;

    private SelectAddPop$$Lambda$2(SelectAddPop selectAddPop, CommonPopCallback commonPopCallback) {
        this.arg$1 = selectAddPop;
        this.arg$2 = commonPopCallback;
    }

    public static View.OnClickListener lambdaFactory$(SelectAddPop selectAddPop, CommonPopCallback commonPopCallback) {
        return new SelectAddPop$$Lambda$2(selectAddPop, commonPopCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showSelectPop$1(this.arg$2, view);
    }
}
