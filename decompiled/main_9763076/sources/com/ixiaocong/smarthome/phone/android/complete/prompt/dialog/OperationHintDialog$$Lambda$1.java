package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class OperationHintDialog$$Lambda$1 implements View.OnClickListener {
    private final HintDialogCallback arg$1;
    private final PromptDialog arg$2;

    private OperationHintDialog$$Lambda$1(HintDialogCallback hintDialogCallback, PromptDialog promptDialog) {
        this.arg$1 = hintDialogCallback;
        this.arg$2 = promptDialog;
    }

    public static View.OnClickListener lambdaFactory$(HintDialogCallback hintDialogCallback, PromptDialog promptDialog) {
        return new OperationHintDialog$$Lambda$1(hintDialogCallback, promptDialog);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        OperationHintDialog.lambda$showSelectDialog$0(this.arg$1, this.arg$2, view);
    }
}
