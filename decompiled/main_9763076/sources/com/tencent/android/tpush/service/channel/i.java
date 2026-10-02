package com.tencent.android.tpush.service.channel;

import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class i implements Runnable {
    final /* synthetic */ b a;

    i(b bVar) {
        this.a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (XGPushConfig.isForeiginPush(com.tencent.android.tpush.service.n.f())) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "isForeiginPush network is ok , switch to main service");
            com.tencent.android.tpush.service.n.a(com.tencent.android.tpush.service.n.f(), Constants.ACTION_SLVAE_2_MAIN, 0L);
        } else if (com.tencent.android.tpush.service.b.b.a(com.tencent.android.tpush.service.n.f()).b()) {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "network is ok , switch to main service");
            com.tencent.android.tpush.service.n.a(com.tencent.android.tpush.service.n.f(), Constants.ACTION_SLVAE_2_MAIN, 0L);
        } else {
            com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "network is error , go on  slave service");
        }
    }
}
