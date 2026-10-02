package com.facebook.react.modules.datepicker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;
import com.tencent.android.tpush.common.MessageKey;
import java.util.Calendar;
import java.util.Locale;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@SuppressLint({"ValidFragment"})
public class DatePickerDialogFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private DialogInterface.OnDismissListener mOnDismissListener;

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        return createDialog(args, getActivity(), this.mOnDateSetListener);
    }

    static Dialog createDialog(Bundle args, Context activityContext, DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar c = Calendar.getInstance();
        if (args != null && args.containsKey(MessageKey.MSG_DATE)) {
            c.setTimeInMillis(args.getLong(MessageKey.MSG_DATE));
        }
        int year = c.get(1);
        int month = c.get(2);
        int day = c.get(5);
        DatePickerMode mode = DatePickerMode.DEFAULT;
        if (args != null && args.getString("mode", null) != null) {
            mode = DatePickerMode.valueOf(args.getString("mode").toUpperCase(Locale.US));
        }
        DatePickerDialog dialog = null;
        if (Build.VERSION.SDK_INT >= 21) {
            switch (mode) {
                case CALENDAR:
                    dialog = new DismissableDatePickerDialog(activityContext, activityContext.getResources().getIdentifier("CalendarDatePickerDialog", "style", activityContext.getPackageName()), onDateSetListener, year, month, day);
                    break;
                case SPINNER:
                    dialog = new DismissableDatePickerDialog(activityContext, activityContext.getResources().getIdentifier("SpinnerDatePickerDialog", "style", activityContext.getPackageName()), onDateSetListener, year, month, day);
                    break;
                case DEFAULT:
                    dialog = new DismissableDatePickerDialog(activityContext, onDateSetListener, year, month, day);
                    break;
            }
        } else {
            dialog = new DismissableDatePickerDialog(activityContext, onDateSetListener, year, month, day);
            switch (mode) {
                case CALENDAR:
                    dialog.getDatePicker().setCalendarViewShown(true);
                    dialog.getDatePicker().setSpinnersShown(false);
                    break;
                case SPINNER:
                    dialog.getDatePicker().setCalendarViewShown(false);
                    break;
            }
        }
        DatePicker datePicker = dialog.getDatePicker();
        if (args != null && args.containsKey("minDate")) {
            c.setTimeInMillis(args.getLong("minDate"));
            c.set(11, 0);
            c.set(12, 0);
            c.set(13, 0);
            c.set(14, 0);
            datePicker.setMinDate(c.getTimeInMillis());
        } else {
            datePicker.setMinDate(-2208988800001L);
        }
        if (args != null && args.containsKey("maxDate")) {
            c.setTimeInMillis(args.getLong("maxDate"));
            c.set(11, 23);
            c.set(12, 59);
            c.set(13, 59);
            c.set(14, 999);
            datePicker.setMaxDate(c.getTimeInMillis());
        }
        return dialog;
    }

    @Override // android.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss(dialog);
        }
    }

    void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.mOnDateSetListener = onDateSetListener;
    }

    void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }
}
