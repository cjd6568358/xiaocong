package com.tencent.android.tpush;

import android.content.DialogInterface;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class o implements DialogInterface.OnCancelListener {
    final /* synthetic */ Intent a;
    final /* synthetic */ XGPushActivity b;

    o(XGPushActivity xGPushActivity, Intent intent) {
        this.b = xGPushActivity;
        this.a = intent;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        this.a.putExtra("action", 6);
        this.b.broadcastToTPushService(this.a);
        this.b.finish();
    }
}
