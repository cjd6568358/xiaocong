package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonSettingActivty$$Lambda$4 implements CompoundButton.OnCheckedChangeListener {
    private final AoniCommonSettingActivty arg$1;

    private AoniCommonSettingActivty$$Lambda$4(AoniCommonSettingActivty aoniCommonSettingActivty) {
        this.arg$1 = aoniCommonSettingActivty;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(AoniCommonSettingActivty aoniCommonSettingActivty) {
        return new AoniCommonSettingActivty$$Lambda$4(aoniCommonSettingActivty);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$addListener$3(compoundButton, z);
    }
}
