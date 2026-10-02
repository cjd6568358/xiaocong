package com.tencent.android.tpush.service;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.channel.exception.ChannelException;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class g implements com.tencent.android.tpush.service.channel.t {
    final /* synthetic */ long a;
    final /* synthetic */ int b;
    final /* synthetic */ String c;
    final /* synthetic */ String d;
    final /* synthetic */ a e;

    g(a aVar, long j, int i, String str, String str2) {
        this.e = aVar;
        this.a = j;
        this.b = i;
        this.c = str;
        this.d = str2;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        if (i == 0) {
            com.tencent.android.tpush.a.a.f(a.a, "Set tag ack success  [accId = " + this.a + " , tagtype = " + this.b + " , tagName = " + this.c + ", packName = " + this.d + " , rsp = " + aVar.c() + "]");
        } else {
            com.tencent.android.tpush.a.a.j(a.a, "Set tag ack failed with responseCode = " + i + " , tagName = " + this.c);
        }
        this.e.a(i, this.c, this.b, this.d);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        if (channelException != null) {
            this.e.a(channelException.errorCode, this.c, this.b, this.d);
        }
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.h(a.a, "Set tag onMessageDiscarded  , tagName = " + this.c);
    }
}
