package com.ixiaocong.smarthome.phone.android.detail.activity.camera.common;

import com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class AoniCommonSettingActivty$$Lambda$5 implements CommonTypeCallback {
    private final AoniCommonSettingActivty arg$1;

    private AoniCommonSettingActivty$$Lambda$5(AoniCommonSettingActivty aoniCommonSettingActivty) {
        this.arg$1 = aoniCommonSettingActivty;
    }

    public static CommonTypeCallback lambdaFactory$(AoniCommonSettingActivty aoniCommonSettingActivty) {
        return new AoniCommonSettingActivty$$Lambda$5(aoniCommonSettingActivty);
    }

    @Override // com.ixiaocong.smarthome.phone.android.event.callback.CommonTypeCallback
    @LambdaForm.Hidden
    public void resultTypeCalllback(int i) {
        this.arg$1.resultTypeCalllback(i);
    }
}
