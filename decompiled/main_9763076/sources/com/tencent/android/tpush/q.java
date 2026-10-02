package com.tencent.android.tpush;

import android.content.DialogInterface;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class q implements DialogInterface.OnClickListener {
    final /* synthetic */ String a;
    final /* synthetic */ Intent b;
    final /* synthetic */ XGPushActivity c;

    q(XGPushActivity xGPushActivity, String str, Intent intent) {
        this.c = xGPushActivity;
        this.a = str;
        this.b = intent;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.c.openUrl(this.a, this.b);
        this.c.finish();
    }
}
