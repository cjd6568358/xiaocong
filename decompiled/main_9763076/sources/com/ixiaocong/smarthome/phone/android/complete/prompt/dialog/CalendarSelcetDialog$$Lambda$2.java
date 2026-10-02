package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import android.view.View;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CalendarSelcetDialog$$Lambda$2 implements View.OnClickListener {
    private final CalendarSelcetDialog arg$1;

    private CalendarSelcetDialog$$Lambda$2(CalendarSelcetDialog calendarSelcetDialog) {
        this.arg$1 = calendarSelcetDialog;
    }

    public static View.OnClickListener lambdaFactory$(CalendarSelcetDialog calendarSelcetDialog) {
        return new CalendarSelcetDialog$$Lambda$2(calendarSelcetDialog);
    }

    @Override // android.view.View.OnClickListener
    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addListener$1(view);
    }
}
