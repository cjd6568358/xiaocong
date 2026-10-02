package com.ixiaocong.smarthome.phone.android.detail.activity.system;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class UseAppHelpActivity$$Lambda$1 implements View.OnClickListener {
    private final UseAppHelpActivity arg$1;

    private UseAppHelpActivity$$Lambda$1(UseAppHelpActivity useAppHelpActivity) {
        this.arg$1 = useAppHelpActivity;
    }

    public static View.OnClickListener lambdaFactory$(UseAppHelpActivity useAppHelpActivity) {
        return new UseAppHelpActivity$$Lambda$1(useAppHelpActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
