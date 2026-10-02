package com.facebook.react.modules.datepicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DismissableDatePickerDialog extends DatePickerDialog {
    public DismissableDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callback, int year, int monthOfYear, int dayOfMonth) {
        super(context, callback, year, monthOfYear, dayOfMonth);
    }

    public DismissableDatePickerDialog(Context context, int theme, DatePickerDialog.OnDateSetListener callback, int year, int monthOfYear, int dayOfMonth) {
        super(context, theme, callback, year, monthOfYear, dayOfMonth);
    }

    @Override // android.app.Dialog
    protected void onStop() {
        if (Build.VERSION.SDK_INT > 19) {
            super.onStop();
        }
    }
}
