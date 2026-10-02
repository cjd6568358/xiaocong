package com.ixiaocong.smarthome.phone.android.detail.activity.login;

import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class LoginActivity$$Lambda$1 implements CompoundButton.OnCheckedChangeListener {
    private final LoginActivity arg$1;

    private LoginActivity$$Lambda$1(LoginActivity loginActivity) {
        this.arg$1 = loginActivity;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(LoginActivity loginActivity) {
        return new LoginActivity$$Lambda$1(loginActivity);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$addListener$0(compoundButton, z);
    }
}
