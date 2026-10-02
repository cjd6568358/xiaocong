package com.tencent.android.tpush.service;

import android.content.Context;
import com.tencent.android.tpush.service.channel.protocol.TpnsClientReport;
import com.tencent.android.tpush.service.channel.protocol.TpnsClientReportReq;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
class d implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ long b;
    final /* synthetic */ a c;

    d(a aVar, Context context, long j) {
        this.c = aVar;
        this.a = context;
        this.b = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        TpnsClientReport tpnsClientReport = new TpnsClientReport();
        tpnsClientReport.commandId = 0;
        tpnsClientReport.signal = com.tencent.android.tpush.service.e.m.t(this.a).toString();
        TpnsClientReportReq tpnsClientReportReq = new TpnsClientReportReq();
        tpnsClientReportReq.reportMsgs = new ArrayList();
        tpnsClientReportReq.reportMsgs.add(tpnsClientReport);
        com.tencent.android.tpush.service.channel.b.a().a(tpnsClientReportReq, new e(this));
    }
}
