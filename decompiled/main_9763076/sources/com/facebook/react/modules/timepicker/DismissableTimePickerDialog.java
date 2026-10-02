package com.facebook.react.modules.timepicker;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DismissableTimePickerDialog extends TimePickerDialog {
    public DismissableTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener callback, int hourOfDay, int minute, boolean is24HourView) {
        super(context, callback, hourOfDay, minute, is24HourView);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        if (Build.VERSION.SDK_INT > 19) {
            super.onStop();
        }
    }
}
