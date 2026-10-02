package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsTriggerReportReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public long timeEnd;
    public long timeStart;

    static {
        $assertionsDisabled = !TpnsTriggerReportReq.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsTriggerReportReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsTriggerReportReq";
    }

    public long getTimeStart() {
        return this.timeStart;
    }

    public void setTimeStart(long j) {
        this.timeStart = j;
    }

    public long getTimeEnd() {
        return this.timeEnd;
    }

    public void setTimeEnd(long j) {
        this.timeEnd = j;
    }

    public TpnsTriggerReportReq() {
        this.timeStart = 0L;
        this.timeEnd = 0L;
    }

    public TpnsTriggerReportReq(long j, long j2) {
        this.timeStart = 0L;
        this.timeEnd = 0L;
        this.timeStart = j;
        this.timeEnd = j2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsTriggerReportReq tpnsTriggerReportReq = (TpnsTriggerReportReq) obj;
        return JceUtil.equals(this.timeStart, tpnsTriggerReportReq.timeStart) && JceUtil.equals(this.timeEnd, tpnsTriggerReportReq.timeEnd);
    }

    public int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            if ($assertionsDisabled) {
                return null;
            }
            throw new AssertionError();
        }
    }

    @Override // com.qq.taf.jce.JceStruct
    public void writeTo(JceOutputStream jceOutputStream) {
        jceOutputStream.write(this.timeStart, 0);
        jceOutputStream.write(this.timeEnd, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.timeStart = jceInputStream.read(this.timeStart, 0, true);
        this.timeEnd = jceInputStream.read(this.timeEnd, 1, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.timeStart, "timeStart");
        jceDisplayer.display(this.timeEnd, "timeEnd");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.timeStart, true);
        jceDisplayer.displaySimple(this.timeEnd, false);
    }
}
