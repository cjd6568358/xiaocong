package com.tencent.android.tpush.service.channel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j extends BroadcastReceiver {
    final /* synthetic */ b a;

    j(b bVar) {
        this.a = bVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        b.a().k();
    }
}
