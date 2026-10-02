package com.tencent.android.tpush;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class k implements DialogInterface.OnClickListener {
    final /* synthetic */ Intent a;
    final /* synthetic */ XGPushActivity b;

    k(XGPushActivity xGPushActivity, Intent intent) {
        this.b = xGPushActivity;
        this.a = intent;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.putExtra("action", 3);
        this.b.broadcastToTPushService(this.a);
        try {
            this.b.startActivity(this.a);
        } catch (ActivityNotFoundException e) {
        }
        this.b.finish();
    }
}
