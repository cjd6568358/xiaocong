package com.huawei.hms.b;

import android.content.DialogInterface;

/* JADX INFO: compiled from: AbstractDialog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements DialogInterface.OnClickListener {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.d();
    }
}
