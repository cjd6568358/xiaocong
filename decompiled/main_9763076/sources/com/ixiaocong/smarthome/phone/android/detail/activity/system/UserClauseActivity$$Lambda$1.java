package com.ixiaocong.smarthome.phone.android.detail.activity.system;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class UserClauseActivity$$Lambda$1 implements View.OnClickListener {
    private final UserClauseActivity arg$1;

    private UserClauseActivity$$Lambda$1(UserClauseActivity userClauseActivity) {
        this.arg$1 = userClauseActivity;
    }

    public static View.OnClickListener lambdaFactory$(UserClauseActivity userClauseActivity) {
        return new UserClauseActivity$$Lambda$1(userClauseActivity);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$0(view);
    }
}
