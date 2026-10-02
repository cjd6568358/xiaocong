package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class ConfigScenePop$$Lambda$3 implements View.OnClickListener {
    private final ConfigScenePop arg$1;

    private ConfigScenePop$$Lambda$3(ConfigScenePop configScenePop) {
        this.arg$1 = configScenePop;
    }

    public static View.OnClickListener lambdaFactory$(ConfigScenePop configScenePop) {
        return new ConfigScenePop$$Lambda$3(configScenePop);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showConfigScenePopup$2(view);
    }
}
