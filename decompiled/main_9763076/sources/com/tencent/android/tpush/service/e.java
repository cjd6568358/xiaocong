package com.tencent.android.tpush.service;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.channel.exception.ChannelException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        if (i == 0) {
            try {
                com.tencent.android.tpush.service.e.f.a(this.a.a, "com.tencent.android.tpush.action.next.applist.ts.V3", this.a.b + 86400000);
            } catch (Throwable th) {
            }
        }
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i(a.a, ">>> reportReq onMessageSendFailed(" + jceStruct + ", " + channelException + ", " + aVar + ")");
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i(a.a, ">>> reportReq onMessageDiscarded(" + jceStruct + ", " + aVar + ")");
    }
}
