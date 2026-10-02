package com.facebook.react.modules.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AlertFragment extends DialogFragment implements DialogInterface.OnClickListener {
    private final DialogModule.AlertFragmentListener mListener;

    public AlertFragment() {
        this.mListener = null;
    }

    public AlertFragment(DialogModule.AlertFragmentListener listener, Bundle arguments) {
        this.mListener = listener;
        setArguments(arguments);
    }

    public static Dialog createDialog(Context activityContext, Bundle arguments, DialogInterface.OnClickListener fragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext).setTitle(arguments.getString("title"));
        if (arguments.containsKey("button_positive")) {
            builder.setPositiveButton(arguments.getString("button_positive"), fragment);
        }
        if (arguments.containsKey("button_negative")) {
            builder.setNegativeButton(arguments.getString("button_negative"), fragment);
        }
        if (arguments.containsKey("button_neutral")) {
            builder.setNeutralButton(arguments.getString("button_neutral"), fragment);
        }
        if (arguments.containsKey("message")) {
            builder.setMessage(arguments.getString("message"));
        }
        if (arguments.containsKey("items")) {
            builder.setItems(arguments.getCharSequenceArray("items"), fragment);
        }
        return builder.create();
    }

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialog(getActivity(), getArguments(), this);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        if (this.mListener != null) {
            this.mListener.onClick(dialog, which);
        }
    }

    @Override // android.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.mListener != null) {
            this.mListener.onDismiss(dialog);
        }
    }
}
