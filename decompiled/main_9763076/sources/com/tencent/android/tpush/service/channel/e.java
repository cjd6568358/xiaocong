package com.tencent.android.tpush.service.channel;

import com.qq.taf.jce.JceStruct;
import com.tencent.android.tpush.service.channel.exception.ChannelException;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class e implements t {
    final /* synthetic */ b a;

    e(b bVar) {
        this.a = bVar;
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, int i, JceStruct jceStruct2, a aVar) {
        if (i == 0) {
            com.tencent.android.tpush.service.d.a.a();
            if (b.a >= 3) {
            }
            b.a++;
        }
        b.b++;
        com.tencent.android.tpush.a.a.c("TpnsChannel", "heartbeat success rsp = " + aVar.c() + " heartbeattimes = " + b.a);
        b.i();
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, ChannelException channelException, a aVar) {
        b.c++;
        try {
            com.tencent.android.tpush.a.a.i("TpnsChannel", "heartbeat failed onMessageSendFailed " + channelException.errorCode + "," + channelException.getMessage());
            if (b.h == null) {
                b.h = new JSONArray();
            }
            if (channelException != null && b.h != null && b.h.length() < 15) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("errorCode", channelException.errorCode);
                if (com.tencent.android.tpush.service.n.f() != null) {
                    jSONObject.put("np", (int) com.tencent.android.tpush.service.e.m.k(com.tencent.android.tpush.service.n.f()));
                }
                b.h.put(jSONObject);
            }
        } catch (Throwable th) {
            com.tencent.android.tpush.a.a.i("TpnsChannel", "Add reprot error");
        }
        b.i();
    }

    @Override // com.tencent.android.tpush.service.channel.t
    public void a(JceStruct jceStruct, a aVar) {
        com.tencent.android.tpush.a.a.i("TpnsChannel", "heartbeat failed TpnsMessage.IEventListener.onMessageDiscarded");
    }
}
