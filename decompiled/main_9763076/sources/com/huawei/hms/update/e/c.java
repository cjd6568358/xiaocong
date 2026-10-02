package com.huawei.hms.update.e;

import android.content.DialogInterface;

/* JADX INFO: compiled from: AbstractDialog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class c implements DialogInterface.OnCancelListener {
    final /* synthetic */ b a;

    c(b bVar) {
        this.a = bVar;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        this.a.d();
    }
}
