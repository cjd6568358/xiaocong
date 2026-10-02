package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonBuyHistoryActivity$$Lambda$1 implements View.OnClickListener {
    private final AoniCommonBuyHistoryActivity arg$1;

    private AoniCommonBuyHistoryActivity$$Lambda$1(AoniCommonBuyHistoryActivity aoniCommonBuyHistoryActivity) {
        this.arg$1 = aoniCommonBuyHistoryActivity;
    }

    public static View.OnClickListener lambdaFactory$(AoniCommonBuyHistoryActivity aoniCommonBuyHistoryActivity) {
        return new AoniCommonBuyHistoryActivity$$Lambda$1(aoniCommonBuyHistoryActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
