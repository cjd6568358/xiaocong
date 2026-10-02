package com.tencent.android.tpush.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class m extends BroadcastReceiver {
    final /* synthetic */ a a;

    private m(a aVar) {
        this.a = aVar;
    }

    /* synthetic */ m(a aVar, b bVar) {
        this(aVar);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent != null && context != null) {
            com.tencent.android.tpush.common.g.a().a(new l(this.a, context, intent));
        }
    }
}
