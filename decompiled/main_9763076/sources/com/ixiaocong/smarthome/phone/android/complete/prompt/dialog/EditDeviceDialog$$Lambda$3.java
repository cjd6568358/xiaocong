package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.content.Context;
import android.view.View;
import com.ixiaocong.smarthome.phone.rn.callback.RNParameterRenameCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class EditDeviceDialog$$Lambda$3 implements View.OnClickListener {
    private final PromptDialog arg$1;
    private final RNParameterRenameCallback arg$2;
    private final Context arg$3;

    private EditDeviceDialog$$Lambda$3(PromptDialog promptDialog, RNParameterRenameCallback rNParameterRenameCallback, Context context) {
        this.arg$1 = promptDialog;
        this.arg$2 = rNParameterRenameCallback;
        this.arg$3 = context;
    }

    public static View.OnClickListener lambdaFactory$(PromptDialog promptDialog, RNParameterRenameCallback rNParameterRenameCallback, Context context) {
        return new EditDeviceDialog$$Lambda$3(promptDialog, rNParameterRenameCallback, context);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        EditDeviceDialog.lambda$renameParameterDialog$2(this.arg$1, this.arg$2, this.arg$3, view);
    }
}
