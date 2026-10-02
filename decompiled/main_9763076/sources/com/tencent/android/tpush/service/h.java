package com.tencent.android.tpush.service;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsUnregisterReq;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class h implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ a c;

    h(a aVar, String str, String str2) {
        this.c = aVar;
        this.a = str;
        this.b = str2;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        if (i == 0) {
            com.tencent.android.tpush.a.a.f(a.a, ">> UnRegister ack with [accId = " + this.a + " , packName = " + this.b + " , rsp = " + aVar.c() + "]");
            this.c.a(i, (TpnsUnregisterReq) jceStruct, aVar, this.b);
        } else {
            com.tencent.android.tpush.a.a.i(a.a, ">> unregeister ack failed responseCode=" + i);
            this.c.a(i, "服务器处理失败，返回错误", (TpnsUnregisterReq) jceStruct, aVar, this.b);
        }
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i(a.a, "@@ unregister onMessageSendFailed " + channelException.errorCode + "," + channelException.getMessage());
        this.c.a(channelException.errorCode, channelException.getMessage(), (TpnsUnregisterReq) jceStruct, aVar, this.b);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
    }
}
