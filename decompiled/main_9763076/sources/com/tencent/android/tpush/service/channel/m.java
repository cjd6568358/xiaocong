package com.tencent.android.tpush.service.channel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class m extends BroadcastReceiver {
    final /* synthetic */ b a;

    private m(b bVar) {
        this.a = bVar;
    }

    /* synthetic */ m(b bVar, c cVar) {
        this(bVar);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (this.a.K == 0 || jCurrentTimeMillis - this.a.K > 20000) {
            b.a().l();
            this.a.K = jCurrentTimeMillis;
        } else {
            com.tencent.android.tpush.a.a.e("TpnsChannel", "give up heartbeatSlave ");
        }
    }
}
