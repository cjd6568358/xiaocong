package com.tencent.android.tpush.stat;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.service.channel.protocol.TpnsPushMsg;
import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class p implements Runnable {
    final /* synthetic */ ArrayList a;
    final /* synthetic */ Context b;

    p(ArrayList arrayList, Context context) {
        this.a = arrayList;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            ArrayList arrayList = new ArrayList(this.a.size());
            for (TpnsPushMsg tpnsPushMsg : this.a) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", Constants.MAIN_VERSION_TAG + tpnsPushMsg.type);
                jSONObject.put(MessageKey.MSG_BUSI_MSG_ID, Constants.MAIN_VERSION_TAG + tpnsPushMsg.busiMsgId);
                jSONObject.put(MessageKey.MSG_ID, Constants.MAIN_VERSION_TAG + tpnsPushMsg.msgId);
                com.tencent.android.tpush.stat.event.a aVar = new com.tencent.android.tpush.stat.event.a(this.b, h.b(this.b, tpnsPushMsg.accessId), "SrvAck", tpnsPushMsg.accessId, tpnsPushMsg.timestamp);
                aVar.a().c = jSONObject;
                arrayList.add(aVar);
            }
            h.a(arrayList);
        } catch (Throwable th) {
            h.g.b(th);
        }
    }
}
