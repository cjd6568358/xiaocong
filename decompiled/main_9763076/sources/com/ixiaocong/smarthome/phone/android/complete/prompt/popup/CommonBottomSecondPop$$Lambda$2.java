package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonSecondPopCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CommonBottomSecondPop$$Lambda$2 implements View.OnClickListener {
    private final CommonBottomSecondPop arg$1;
    private final CommonSecondPopCallback arg$2;

    private CommonBottomSecondPop$$Lambda$2(CommonBottomSecondPop commonBottomSecondPop, CommonSecondPopCallback commonSecondPopCallback) {
        this.arg$1 = commonBottomSecondPop;
        this.arg$2 = commonSecondPopCallback;
    }

    public static View.OnClickListener lambdaFactory$(CommonBottomSecondPop commonBottomSecondPop, CommonSecondPopCallback commonSecondPopCallback) {
        return new CommonBottomSecondPop$$Lambda$2(commonBottomSecondPop, commonSecondPopCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showCommonBootomPopup$1(this.arg$2, view);
    }
}
