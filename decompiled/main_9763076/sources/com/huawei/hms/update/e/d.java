package com.huawei.hms.update.e;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import com.huawei.android.hms.base.R;

/* JADX INFO: compiled from: CheckProgress.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class d extends b {
    @Override // com.huawei.hms.update.e.b
    public AlertDialog a() {
        ProgressDialog progressDialog = new ProgressDialog(f(), g());
        progressDialog.setMessage(f().getString(R.string.hms_checking));
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
}
