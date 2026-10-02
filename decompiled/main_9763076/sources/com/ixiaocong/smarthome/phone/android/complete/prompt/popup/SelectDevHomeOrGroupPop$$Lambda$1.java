package com.ixiaocong.smarthome.phone.android.complete.prompt.popup;

import android.content.Context;
import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class SelectDevHomeOrGroupPop$$Lambda$1 implements View.OnClickListener {
    private final SelectDevHomeOrGroupPop arg$1;
    private final boolean arg$2;
    private final Context arg$3;
    private final String arg$4;

    private SelectDevHomeOrGroupPop$$Lambda$1(SelectDevHomeOrGroupPop selectDevHomeOrGroupPop, boolean z, Context context, String str) {
        this.arg$1 = selectDevHomeOrGroupPop;
        this.arg$2 = z;
        this.arg$3 = context;
        this.arg$4 = str;
    }

    public static View.OnClickListener lambdaFactory$(SelectDevHomeOrGroupPop selectDevHomeOrGroupPop, boolean z, Context context, String str) {
        return new SelectDevHomeOrGroupPop$$Lambda$1(selectDevHomeOrGroupPop, z, context, str);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$showPop$0(this.arg$2, this.arg$3, this.arg$4, view);
    }
}
