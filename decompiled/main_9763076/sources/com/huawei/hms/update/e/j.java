package com.huawei.hms.update.e;

import android.content.DialogInterface;

/* JADX INFO: compiled from: InstallConfirm.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j implements DialogInterface.OnClickListener {
    final /* synthetic */ i a;

    j(i iVar) {
        this.a = iVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.e();
    }
}
