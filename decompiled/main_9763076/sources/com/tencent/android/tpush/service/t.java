package com.tencent.android.tpush.service;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsReconnectReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsReconnectRsp;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class t implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ s a;

    t(s sVar) {
        this.a = sVar;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "reconnCallback onResponse request:" + jceStruct + ", responseCode:" + i + ", response:" + jceStruct2);
        if (i == 0) {
            if (jceStruct != null) {
                com.tencent.android.tpush.a.a.a(7, ((TpnsReconnectReq) jceStruct).recvMsgList);
                CacheManager.updateUnregUninList(n.f(), ((TpnsReconnectReq) jceStruct).unregInfoList);
                com.tencent.android.tpush.service.c.a.a().d(n.f(), ((TpnsReconnectReq) jceStruct).recvMsgList);
                com.tencent.android.tpush.service.c.a.a().b(n.f(), ((TpnsReconnectReq) jceStruct).msgClickList);
            }
            TpnsReconnectRsp tpnsReconnectRsp = (TpnsReconnectRsp) jceStruct2;
            if (tpnsReconnectRsp != null && tpnsReconnectRsp.appOfflinePushMsgList != null && tpnsReconnectRsp.appOfflinePushMsgList.size() > 0) {
                com.tencent.android.tpush.service.c.a.a().a(tpnsReconnectRsp.appOfflinePushMsgList, tpnsReconnectRsp.timeUs, aVar);
            }
            com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "reconnCallback onResponse rsp==null?:" + (tpnsReconnectRsp == null));
            if (tpnsReconnectRsp != null) {
                this.a.a(aVar.b(), tpnsReconnectRsp.confVersion);
                return;
            }
            return;
        }
        com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", ">> reconn failed responseCode=" + i);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
    }
}
