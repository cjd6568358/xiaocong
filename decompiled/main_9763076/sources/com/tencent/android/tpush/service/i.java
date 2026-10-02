package com.tencent.android.tpush.service;

import android.content.Context;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.channel.exception.ChannelException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class i implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ Context e;
    final /* synthetic */ a f;

    i(a aVar, String str, String str2, String str3, String str4, Context context) {
        this.f = aVar;
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
        this.e = context;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) throws Throwable {
        if (i == 0) {
            com.tencent.android.tpush.common.t.a("bind OtherPushToken success ack with= " + this.a + "  token = " + this.b + " otherPushType = " + this.c + " otherPushToken = " + this.d, this.e);
            com.tencent.android.tpush.a.a.f(a.a, ">> bind OtherPushToken success ack with [accId = " + this.a + "  , rsp = " + i + "]  token = " + this.b + " otherPushType = " + this.c + " otherPushToken = " + this.d);
            com.tencent.android.tpush.service.e.h.b(this.e, this.a + "otherpush", this.b + ":" + this.d);
            com.tencent.android.tpush.service.e.h.b(this.e, this.a + "ts", System.currentTimeMillis());
            return;
        }
        com.tencent.android.tpush.common.t.a("updateOtherPushToken ack failed responseCode=" + i, this.e);
        com.tencent.android.tpush.a.a.j(a.a, ">> updateOtherPushToken ack failed responseCode=" + i);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.j(a.a, "@@ updateOtherPushToken onMessageSendFailed " + channelException.errorCode + "," + channelException.getMessage());
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
    }
}
