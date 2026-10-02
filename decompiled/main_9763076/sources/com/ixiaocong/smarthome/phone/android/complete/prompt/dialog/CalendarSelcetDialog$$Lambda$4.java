package com.ixiaocong.smarthome.phone.android.complete.prompt.dialog;

import com.xiaocong.smarthome.wheel.WheelView;
import java.lang.invoke.LambdaForm;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final /* synthetic */ class CalendarSelcetDialog$$Lambda$4 implements WheelView.OnItemSelectedListener {
    private final CalendarSelcetDialog arg$1;

    private CalendarSelcetDialog$$Lambda$4(CalendarSelcetDialog calendarSelcetDialog) {
        this.arg$1 = calendarSelcetDialog;
    }

    public static WheelView.OnItemSelectedListener lambdaFactory$(CalendarSelcetDialog calendarSelcetDialog) {
        return new CalendarSelcetDialog$$Lambda$4(calendarSelcetDialog);
    }

    @LambdaForm.Hidden
    public void onItemSelected(int i, String str) {
        this.arg$1.lambda$addListener$3(i, str);
    }
}
