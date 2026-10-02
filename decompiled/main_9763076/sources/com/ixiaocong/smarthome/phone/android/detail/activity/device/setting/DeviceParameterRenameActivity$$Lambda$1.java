package com.ixiaocong.smarthome.phone.android.detail.activity.device.setting;

import android.view.View;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.PromptDialog;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class DeviceParameterRenameActivity$$Lambda$1 implements View.OnClickListener {
    private final DeviceParameterRenameActivity arg$1;
    private final PromptDialog arg$2;
    private final String arg$3;

    private DeviceParameterRenameActivity$$Lambda$1(DeviceParameterRenameActivity deviceParameterRenameActivity, PromptDialog promptDialog, String str) {
        this.arg$1 = deviceParameterRenameActivity;
        this.arg$2 = promptDialog;
        this.arg$3 = str;
    }

    public static View.OnClickListener lambdaFactory$(DeviceParameterRenameActivity deviceParameterRenameActivity, PromptDialog promptDialog, String str) {
        return new DeviceParameterRenameActivity$$Lambda$1(deviceParameterRenameActivity, promptDialog, str);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$renameParSetting$0(this.arg$2, this.arg$3, view);
    }
}
