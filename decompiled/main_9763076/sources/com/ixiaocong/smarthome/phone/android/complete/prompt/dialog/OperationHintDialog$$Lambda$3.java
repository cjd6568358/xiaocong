package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.content.Context;
import android.view.View;
import com.ixiaocong.smarthome.phone.android.event.callback.EditDialogCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class OperationHintDialog$$Lambda$3 implements View.OnClickListener {
    private final PromptDialog arg$1;
    private final Context arg$2;
    private final EditDialogCallback arg$3;

    private OperationHintDialog$$Lambda$3(PromptDialog promptDialog, Context context, EditDialogCallback editDialogCallback) {
        this.arg$1 = promptDialog;
        this.arg$2 = context;
        this.arg$3 = editDialogCallback;
    }

    public static View.OnClickListener lambdaFactory$(PromptDialog promptDialog, Context context, EditDialogCallback editDialogCallback) {
        return new OperationHintDialog$$Lambda$3(promptDialog, context, editDialogCallback);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        OperationHintDialog.lambda$showEditDialog$2(this.arg$1, this.arg$2, this.arg$3, view);
    }
}
