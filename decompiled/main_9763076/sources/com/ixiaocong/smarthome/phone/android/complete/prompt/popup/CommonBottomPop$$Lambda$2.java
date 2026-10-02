package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.CommonPopCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CommonBottomPop$$Lambda$2 implements View.OnClickListener {
    private final CommonBottomPop arg$1;
    private final CommonPopCallback arg$2;

    private CommonBottomPop$$Lambda$2(CommonBottomPop commonBottomPop, CommonPopCallback commonPopCallback) {
        this.arg$1 = commonBottomPop;
        this.arg$2 = commonPopCallback;
    }

    public static View.OnClickListener lambdaFactory$(CommonBottomPop commonBottomPop, CommonPopCallback commonPopCallback) {
        return new CommonBottomPop$$Lambda$2(commonBottomPop, commonPopCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showCommonBootomPopup$1(this.arg$2, view);
    }
}
