package com.facebook.react.modules.timepicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SupportTimePickerDialogFragment extends DialogFragment {
    private DialogInterface.OnDismissListener mOnDismissListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;

    @Override // android.support.v4.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        return TimePickerDialogFragment.createDialog(args, getActivity(), this.mOnTimeSetListener);
    }

    @Override // android.support.v4.app.DialogFragment, android.content.DialogInterface.OnDismissListener
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
