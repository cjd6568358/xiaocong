package com.huawei.hms.b;

import android.content.DialogInterface;

/* JADX INFO: compiled from: AbstractDialog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d implements DialogInterface.OnCancelListener {
    final /* synthetic */ a a;

    d(a aVar) {
        this.a = aVar;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        this.a.c();
    }
}
