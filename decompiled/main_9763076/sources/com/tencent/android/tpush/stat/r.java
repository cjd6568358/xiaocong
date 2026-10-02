package com.tencent.android.tpush.stat;

import android.content.Context;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.tencent.android.tpush.service.channel.protocol.TpnsClickClientReport;
import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
final class r implements Runnable {
    final /* synthetic */ ArrayList a;
    final /* synthetic */ Context b;

    r(ArrayList arrayList, Context context) {
        this.a = arrayList;
        this.b = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            ArrayList arrayList = new ArrayList(this.a.size());
            for (TpnsClickClientReport tpnsClickClientReport : this.a) {
                long j = tpnsClickClientReport.type;
                long j2 = tpnsClickClientReport.timestamp;
                long j3 = tpnsClickClientReport.broadcastId;
                long j4 = tpnsClickClientReport.msgId;
                long j5 = tpnsClickClientReport.accessId;
                long j6 = tpnsClickClientReport.action;
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("type", Constants.MAIN_VERSION_TAG + j);
                jSONObject.put(MessageKey.MSG_BUSI_MSG_ID, Constants.MAIN_VERSION_TAG + j3);
                jSONObject.put(MessageKey.MSG_ID, Constants.MAIN_VERSION_TAG + j4);
                jSONObject.put("action", Constants.MAIN_VERSION_TAG + j6);
                com.tencent.android.tpush.stat.event.a aVar = new com.tencent.android.tpush.stat.event.a(this.b, h.b(this.b, j5), "Action", j5, j2);
                aVar.a().c = jSONObject;
                arrayList.add(aVar);
            }
            h.a(arrayList);
        } catch (Throwable th) {
            h.g.b(th);
        }
    }
}
