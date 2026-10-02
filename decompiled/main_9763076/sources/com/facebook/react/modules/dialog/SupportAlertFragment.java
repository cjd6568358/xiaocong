package com.facebook.react.modules.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SupportAlertFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private final DialogModule.AlertFragmentListener mListener;

    public SupportAlertFragment() {
        this.mListener = null;
    }

    public SupportAlertFragment(DialogModule.AlertFragmentListener listener, Bundle arguments) {
        this.mListener = listener;
        setArguments(arguments);
    }

    @Override // android.support.v4.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return AlertFragment.createDialog(getActivity(), getArguments(), this);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        if (this.mListener != null) {
            this.mListener.onClick(dialog, which);
        }
    }

    @Override // android.support.v4.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.mListener != null) {
            this.mListener.onDismiss(dialog);
        }
    }
}
