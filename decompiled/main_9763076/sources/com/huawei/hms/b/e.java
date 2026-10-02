package com.huawei.hms.b;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* JADX INFO: compiled from: AbstractDialog.java */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e implements DialogInterface.OnKeyListener {
    final /* synthetic */ a a;

    e(a aVar) {
        this.a = aVar;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        if (4 != i || keyEvent.getAction() != 1) {
            return false;
        }
        this.a.a();
        return true;
    }
}
