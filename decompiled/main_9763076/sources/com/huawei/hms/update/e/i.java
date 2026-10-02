package com.huawei.hms.update.e;

import android.app.AlertDialog;
import com.huawei.android.hms.base.R;

/* JADX INFO: compiled from: InstallConfirm.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class i extends b {
    @Override // com.huawei.hms.update.e.b
    protected AlertDialog a() {
        int i = R.string.hms_update_message_new;
        int i2 = R.string.hms_install;
        AlertDialog.Builder builder = new AlertDialog.Builder(f(), g());
        builder.setMessage(f().getString(i, new Object[]{f().getString(R.string.hms_update_title)}));
        builder.setPositiveButton(i2, new j(this));
        builder.setNegativeButton(R.string.hms_cancel, new k(this));
        return builder.create();
    }
}
