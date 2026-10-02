package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonSettingActivty$$Lambda$1 implements View.OnClickListener {
    private final AoniCommonSettingActivty arg$1;

    private AoniCommonSettingActivty$$Lambda$1(AoniCommonSettingActivty aoniCommonSettingActivty) {
        this.arg$1 = aoniCommonSettingActivty;
    }

    public static View.OnClickListener lambdaFactory$(AoniCommonSettingActivty aoniCommonSettingActivty) {
        return new AoniCommonSettingActivty$$Lambda$1(aoniCommonSettingActivty);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
