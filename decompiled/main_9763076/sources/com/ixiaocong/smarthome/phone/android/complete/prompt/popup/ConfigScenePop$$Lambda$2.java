package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.view.View;
import java.lang.invoke.LambdaForm;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class ConfigScenePop$$Lambda$2 implements View.OnClickListener {
    private final ConfigScenePop arg$1;
    private final List arg$2;
    private final int arg$3;
    private final Context arg$4;

    private ConfigScenePop$$Lambda$2(ConfigScenePop configScenePop, List list, int i, Context context) {
        this.arg$1 = configScenePop;
        this.arg$2 = list;
        this.arg$3 = i;
        this.arg$4 = context;
    }

    public static View.OnClickListener lambdaFactory$(ConfigScenePop configScenePop, List list, int i, Context context) {
        return new ConfigScenePop$$Lambda$2(configScenePop, list, i, context);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showConfigScenePopup$1(this.arg$2, this.arg$3, this.arg$4, view);
    }
}
