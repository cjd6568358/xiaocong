package com.facebook.react.views.switchview;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class ReactSwitch extends SwitchCompat {
    private boolean mAllowChange;

    public ReactSwitch(Context context) {
        super(context);
        this.mAllowChange = true;
    }

    @Override // android.support.v7.widget.SwitchCompat, android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean checked) {
        if (this.mAllowChange) {
            this.mAllowChange = false;
            super.setChecked(checked);
        }
    }

    void setOn(boolean on) {
        if (isChecked() != on) {
            super.setChecked(on);
        }
        this.mAllowChange = true;
    }
}
