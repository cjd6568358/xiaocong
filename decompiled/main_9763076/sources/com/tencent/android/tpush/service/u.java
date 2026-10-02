package com.tencent.android.tpush.service;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsConfigRsp;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class u implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ s a;

    u(s sVar) {
        this.a = sVar;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        if (i == 0) {
            com.tencent.android.tpush.service.a.a.a(n.f()).a(((TpnsConfigRsp) jceStruct2).confContent);
        } else {
            com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", ">> loadConfig fail responseCode=" + i);
            this.a.a(i, Constants.MAIN_VERSION_TAG, aVar);
        }
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i("PushServiceNetworkHandler", "@@ loadConfiguration.onMessageSendFailed " + channelException.errorCode + "," + channelException.getMessage());
        this.a.a(channelException.errorCode, channelException.getMessage(), aVar);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
    }
}
