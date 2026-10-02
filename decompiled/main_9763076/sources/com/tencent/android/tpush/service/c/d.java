package com.tencent.android.tpush.service.c;

import android.content.Context;
import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.data.MessageId;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushVerifyReq;
import com.tencent.android.tpush.service.channel.t;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d implements t {
    final /* synthetic */ MessageId a;
    final /* synthetic */ Context b;
    final /* synthetic */ a c;

    d(a aVar, MessageId messageId, Context context) {
        this.c = aVar;
        this.a = messageId;
        this.b = context;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, com.tencent.android.tpush.service.channel.a aVar) {
        if (i != 0) {
            boolean unused = a.f = false;
            com.tencent.android.tpush.a.a.i("SrvMessageManager", ">> ServiceAcking ack onMessageSendFailed responseCode= " + i + " msgId = " + (this.a != null ? Long.valueOf(this.a.id) : null));
            return;
        }
        try {
            if (jceStruct instanceof TpnsPushVerifyReq) {
                TpnsPushVerifyReq tpnsPushVerifyReq = (TpnsPushVerifyReq) jceStruct;
                com.tencent.android.tpush.a.a.a(1, tpnsPushVerifyReq.msgReportList);
                if (tpnsPushVerifyReq.msgReportList == null || tpnsPushVerifyReq.msgReportList.size() == 0) {
                    com.tencent.android.tpush.a.a.i("SrvMessageManager", "ServiceAcking ack failed with null tReq.msgReportList rsp = " + aVar.c() + " msgId " + (this.a != null ? Long.valueOf(this.a.id) : null));
                }
                this.c.a(this.b, tpnsPushVerifyReq.msgReportList);
            } else {
                com.tencent.android.tpush.a.a.i("SrvMessageManager", "requestServiceAck -> Invalid ack callback");
            }
            com.tencent.android.tpush.common.g.a().a(1);
            com.tencent.android.tpush.common.g.a().a(new j(this.c, this.b, 1), 1, 3000L);
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("SrvMessageManager", "requestServiceAck -> Invalid ack callback");
        } finally {
            boolean unused2 = a.f = false;
        }
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i("SrvMessageManager", "ServiceAcking ack onMessageSendFailed  responseCode= " + channelException.errorCode + "," + channelException.getMessage());
        boolean unused = a.f = false;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, com.tencent.android.tpush.service.channel.a aVar) {
        com.tencent.android.tpush.a.a.i("SrvMessageManager", "ServiceAcking ack onMessageDiscarded msgId = " + (this.a == null ? null : Long.valueOf(this.a.id)));
        boolean unused = a.f = false;
    }
}
