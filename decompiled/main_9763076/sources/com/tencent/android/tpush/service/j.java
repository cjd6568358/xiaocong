package com.tencent.android.tpush.service;

import android.content.Context;
import android.content.Intent;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.channel.exception.ChannelException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class j implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ String c;
    final /* synthetic */ Intent d;
    final /* synthetic */ a e;

    j(a aVar, String str, Context context, String str2, Intent intent) {
        this.e = aVar;
        this.a = str;
        this.b = context;
        this.c = str2;
        this.d = intent;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        if (i == 0) {
            com.tencent.android.tpush.a.a.f(a.a, ">> sendCommReportMessage ack with [accId = " + this.a + "  , rsp = " + i + "]");
            com.tencent.android.tpush.a.d(this.b, this.c, this.d.toURI());
        } else {
            com.tencent.android.tpush.a.a.j(a.a, ">> sendCommReportMessage ack failed responseCode=" + i);
        }
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.j(a.a, "@@ sendCommReportMessage onMessageSendFailed " + channelException.errorCode + "," + channelException.getMessage());
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
    }
}
