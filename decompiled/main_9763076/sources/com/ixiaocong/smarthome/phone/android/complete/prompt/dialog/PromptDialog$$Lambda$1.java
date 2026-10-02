package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class PromptDialog$$Lambda$1 implements Runnable {
    private final PromptDialog arg$1;

    private PromptDialog$$Lambda$1(PromptDialog promptDialog) {
        this.arg$1 = promptDialog;
    }

    public static Runnable lambdaFactory$(PromptDialog promptDialog) {
        return new PromptDialog$$Lambda$1(promptDialog);
    }

    @Override // java.lang.Runnable
    @LambdaForm.Hidden
    public void run() {
        this.arg$1.lambda$initLayout$0();
    }
}
