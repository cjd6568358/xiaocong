package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.content.Context;
import android.view.View;
import com.facebook.react.bridge.Callback;
import com.ixiaocong.smarthome.phone.rn.callback.RNParameterCallback;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class EditDeviceDialog$$Lambda$2 implements View.OnClickListener {
    private final PromptDialog arg$1;
    private final Context arg$2;
    private final RNParameterCallback arg$3;
    private final Callback arg$4;
    private final String arg$5;

    private EditDeviceDialog$$Lambda$2(PromptDialog promptDialog, Context context, RNParameterCallback rNParameterCallback, Callback callback, String str) {
        this.arg$1 = promptDialog;
        this.arg$2 = context;
        this.arg$3 = rNParameterCallback;
        this.arg$4 = callback;
        this.arg$5 = str;
    }

    public static View.OnClickListener lambdaFactory$(PromptDialog promptDialog, Context context, RNParameterCallback rNParameterCallback, Callback callback, String str) {
        return new EditDeviceDialog$$Lambda$2(promptDialog, context, rNParameterCallback, callback, str);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        EditDeviceDialog.lambda$renameParSetting$1(this.arg$1, this.arg$2, this.arg$3, this.arg$4, this.arg$5, view);
    }
}
