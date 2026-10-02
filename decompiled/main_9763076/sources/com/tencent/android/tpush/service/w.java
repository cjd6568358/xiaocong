package com.tencent.android.tpush.service;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.horse.DefaultServer;
import com.tencent.android.tpush.service.cache.CacheManager;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsGetApListRsp;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class w implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ s a;

    w(s sVar) {
        this.a = sVar;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "sendMessage onResponse request:" + jceStruct + ", responseCode:" + i + ", response:" + jceStruct2);
        if (i == 0) {
            com.tencent.android.tpush.a.a.c("PushServiceNetworkHandler", "sendMessage onResponse.apList:" + ((TpnsGetApListRsp) jceStruct2).apList);
            DefaultServer.a(((TpnsGetApListRsp) jceStruct2).apList);
            CacheManager.saveLoadIpTime(n.f(), System.currentTimeMillis());
            return;
        }
        com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", ">> loadIPList fail responseCode=" + i);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", "@@ loadIPList.onMessageSendFailed " + channelException.errorCode + "," + channelException.getMessage());
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
    }
}
