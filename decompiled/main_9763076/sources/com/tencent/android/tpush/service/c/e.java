package com.tencent.android.tpush.service.c;

import android.content.Context;
import android.content.Intent;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.t;
import com.tencent.android.tpush.service.n;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e implements t {
    final /* synthetic */ ArrayList a;
    final /* synthetic */ Context b;
    final /* synthetic */ Intent c;
    final /* synthetic */ a d;

    e(a aVar, ArrayList arrayList, Context context, Intent intent) {
        this.d = aVar;
        this.a = arrayList;
        this.b = context;
        this.c = intent;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.service.d.a.c(this.a);
        if (i == 0) {
            this.d.b(n.f(), this.a);
            com.tencent.android.tpush.common.g.a().a(new f(this), 10000L);
        } else {
            com.tencent.android.tpush.a.a.i("SrvMessageManager", ">> msg ckicled ack failed responseCode=" + i);
        }
        boolean unused = a.h = false;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i("SrvMessageManager", "### msg ack onMessageSendFailed  responseCode=" + channelException.errorCode);
        boolean unused = a.h = false;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
        boolean unused = a.h = false;
    }
}
