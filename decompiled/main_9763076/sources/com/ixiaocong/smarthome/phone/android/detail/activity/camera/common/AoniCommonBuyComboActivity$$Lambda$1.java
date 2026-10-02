package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonBuyComboActivity$$Lambda$1 implements View.OnClickListener {
    private final AoniCommonBuyComboActivity arg$1;

    private AoniCommonBuyComboActivity$$Lambda$1(AoniCommonBuyComboActivity aoniCommonBuyComboActivity) {
        this.arg$1 = aoniCommonBuyComboActivity;
    }

    public static View.OnClickListener lambdaFactory$(AoniCommonBuyComboActivity aoniCommonBuyComboActivity) {
        return new AoniCommonBuyComboActivity$$Lambda$1(aoniCommonBuyComboActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
