package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.HomeEidtSelectPopCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class HomeEidtSelectPop$$Lambda$2 implements View.OnClickListener {
    private final HomeEidtSelectPop arg$1;
    private final HomeEidtSelectPopCallback arg$2;

    private HomeEidtSelectPop$$Lambda$2(HomeEidtSelectPop homeEidtSelectPop, HomeEidtSelectPopCallback homeEidtSelectPopCallback) {
        this.arg$1 = homeEidtSelectPop;
        this.arg$2 = homeEidtSelectPopCallback;
    }

    public static View.OnClickListener lambdaFactory$(HomeEidtSelectPop homeEidtSelectPop, HomeEidtSelectPopCallback homeEidtSelectPopCallback) {
        return new HomeEidtSelectPop$$Lambda$2(homeEidtSelectPop, homeEidtSelectPopCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showSelectPop$1(this.arg$2, view);
    }
}
