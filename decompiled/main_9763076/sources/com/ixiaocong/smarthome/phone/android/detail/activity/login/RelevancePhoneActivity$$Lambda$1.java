package com.ixiaocong.smarthome.phone.android.detail.activity.login;

import android.widget.CompoundButton;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class RelevancePhoneActivity$$Lambda$1 implements CompoundButton.OnCheckedChangeListener {
    private final RelevancePhoneActivity arg$1;

    private RelevancePhoneActivity$$Lambda$1(RelevancePhoneActivity relevancePhoneActivity) {
        this.arg$1 = relevancePhoneActivity;
    }

    public static CompoundButton.OnCheckedChangeListener lambdaFactory$(RelevancePhoneActivity relevancePhoneActivity) {
        return new RelevancePhoneActivity$$Lambda$1(relevancePhoneActivity);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    @LambdaForm.Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$addListener$0(compoundButton, z);
    }
}
