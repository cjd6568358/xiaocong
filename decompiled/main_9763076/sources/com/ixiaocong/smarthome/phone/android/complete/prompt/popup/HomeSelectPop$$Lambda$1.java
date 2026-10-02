package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.HomeSelectFamilyCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class HomeSelectPop$$Lambda$1 implements View.OnClickListener {
    private final HomeSelectPop arg$1;
    private final HomeSelectFamilyCallback arg$2;

    private HomeSelectPop$$Lambda$1(HomeSelectPop homeSelectPop, HomeSelectFamilyCallback homeSelectFamilyCallback) {
        this.arg$1 = homeSelectPop;
        this.arg$2 = homeSelectFamilyCallback;
    }

    public static View.OnClickListener lambdaFactory$(HomeSelectPop homeSelectPop, HomeSelectFamilyCallback homeSelectFamilyCallback) {
        return new HomeSelectPop$$Lambda$1(homeSelectPop, homeSelectFamilyCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showSelectPop$0(this.arg$2, view);
    }
}
