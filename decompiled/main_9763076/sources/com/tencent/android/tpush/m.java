package com.tencent.android.tpush;

import android.content.DialogInterface;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class m implements DialogInterface.OnClickListener {
    final /* synthetic */ Intent a;
    final /* synthetic */ XGPushActivity b;

    m(XGPushActivity xGPushActivity, Intent intent) {
        this.b = xGPushActivity;
        this.a = intent;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.putExtra("action", 6);
        this.b.broadcastToTPushService(this.a);
        dialogInterface.dismiss();
        this.b.finish();
    }
}
