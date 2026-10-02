package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.content.Context;
import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class OperationHintDialog$$Lambda$2 implements View.OnClickListener {
    private final String arg$1;
    private final Context arg$2;
    private final HintDialogCallback arg$3;
    private final PromptDialog arg$4;

    private OperationHintDialog$$Lambda$2(String str, Context context, HintDialogCallback hintDialogCallback, PromptDialog promptDialog) {
        this.arg$1 = str;
        this.arg$2 = context;
        this.arg$3 = hintDialogCallback;
        this.arg$4 = promptDialog;
    }

    public static View.OnClickListener lambdaFactory$(String str, Context context, HintDialogCallback hintDialogCallback, PromptDialog promptDialog) {
        return new OperationHintDialog$$Lambda$2(str, context, hintDialogCallback, promptDialog);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        OperationHintDialog.lambda$showHintDialog$1(this.arg$1, this.arg$2, this.arg$3, this.arg$4, view);
    }
}
