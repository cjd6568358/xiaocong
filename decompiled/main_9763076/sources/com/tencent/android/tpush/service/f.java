package com.tencent.android.tpush.service;

import android.content.Context;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsRegisterReq;
import com.tencent.android.tpush.service.channel.protocol.TpnsRegisterRsp;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class f implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ boolean c;
    final /* synthetic */ Context d;
    final /* synthetic */ a e;

    f(a aVar, String str, String str2, boolean z, Context context) {
        this.e = aVar;
        this.a = str;
        this.b = str2;
        this.c = z;
        this.d = context;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.service.channel.b.a().a(true, this.a);
        if (i == 0) {
            if (XGPushConfig.enableDebug) {
                com.tencent.android.tpush.a.a.c(a.a, ">> Register [accId = " + this.b + " , packName = " + this.a + " , rsp = " + aVar.c() + "]");
            }
            this.e.a(i, (TpnsRegisterRsp) jceStruct2, (TpnsRegisterReq) jceStruct, aVar, this.a, this.c);
            try {
                this.e.c(this.d);
                return;
            } catch (Throwable th) {
                com.tencent.android.tpush.a.a.c(a.a, "handler app info failed", th);
                return;
            }
        }
        com.tencent.android.tpush.a.a.i(a.a, ">> Register ack fail responseCode = " + i);
        this.e.a(i, "服务器处理失败，返回错误", (TpnsRegisterReq) jceStruct, aVar, this.a);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.j(a.a, "@@ TpnsMessage.IEventListener.onMessageSendFailed " + channelException.errorCode + "," + channelException.getMessage());
        this.e.a(channelException.errorCode, channelException.getMessage(), (TpnsRegisterReq) jceStruct, aVar, this.a);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
    }
}
