package com.tencent.android.tpush.service.c;

import android.content.Context;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushVerifyReq;
import com.tencent.android.tpush.service.channel.t;
import com.tencent.android.tpush.service.n;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class b implements t {
    final /* synthetic */ ArrayList a;
    final /* synthetic */ Context b;
    final /* synthetic */ a c;

    b(a aVar, ArrayList arrayList, Context context) {
        this.c = aVar;
        this.a = arrayList;
        this.b = context;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        boolean unused = a.e = false;
        if (i == 0) {
            com.tencent.android.tpush.a.a.a(6, this.a);
            ArrayList arrayList = ((TpnsPushVerifyReq) jceStruct).msgReportList;
            com.tencent.android.tpush.a.a.a(7, arrayList);
            com.tencent.android.tpush.service.d.a.b(this.a);
            if (arrayList == null || arrayList.size() == 0) {
                com.tencent.android.tpush.a.a.i("SrvMessageManager", "requestAck ack failed with null tReq.msgReportList rsp = " + aVar.c());
            }
            this.c.d(n.f(), arrayList);
            com.tencent.android.tpush.common.g.a().a(2);
            com.tencent.android.tpush.common.g.a().a(new j(this.c, this.b, 2), 2, 3000L);
            return;
        }
        com.tencent.android.tpush.a.a.i("SrvMessageManager", ">> msg ack onMessageSendFailed  responseCode=" + i);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "@@ TpnsMessage.IEventListener.onMessageSendFailed " + channelException.errorCode + "," + channelException.getMessage());
        boolean unused = a.e = false;
        com.tencent.android.tpush.a.a.a(8, this.a);
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
        boolean unused = a.e = false;
        com.tencent.android.tpush.a.a.i(Constants.ServiceLogTag, "@@ TpnsMessage.IEventListener.onMessageDiscarded ");
    }
}
