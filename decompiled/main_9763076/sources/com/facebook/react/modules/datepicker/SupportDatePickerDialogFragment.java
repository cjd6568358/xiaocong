package com.facebook.react.modules.datepicker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@SuppressLint({"ValidFragment"})
public class SupportDatePickerDialogFragment extends DialogFragment {
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private DialogInterface.OnDismissListener mOnDismissListener;

    @Override // android.support.v4.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        return DatePickerDialogFragment.createDialog(args, getActivity(), this.mOnDateSetListener);
    }

    @Override // android.support.v4.app.DialogFragment, android.content.DialogInterface.OnDismissListener
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
