package com.facebook.react.modules.timepicker;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import com.tencent.android.tpush.common.MessageKey;
import java.util.Calendar;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class TimePickerDialogFragment extends DialogFragment {
    private DialogInterface.OnDismissListener mOnDismissListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        return createDialog(args, getActivity(), this.mOnTimeSetListener);
    }

    static Dialog createDialog(Bundle args, Context activityContext, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        Calendar now = Calendar.getInstance();
        int hour = now.get(11);
        int minute = now.get(12);
        boolean is24hour = DateFormat.is24HourFormat(activityContext);
        if (args != null) {
            hour = args.getInt(MessageKey.MSG_ACCEPT_TIME_HOUR, now.get(11));
            minute = args.getInt("minute", now.get(12));
            is24hour = args.getBoolean("is24Hour", DateFormat.is24HourFormat(activityContext));
        }
        return new DismissableTimePickerDialog(activityContext, onTimeSetListener, hour, minute, is24hour);
    }

    @Override // android.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.mOnDismissListener != null) {
            this.mOnDismissListener.onDismiss(dialog);
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.mOnTimeSetListener = onTimeSetListener;
    }
}
