package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsClientReportReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static ArrayList cache_reportMsgs;
    public ArrayList reportMsgs;

    static {
        $assertionsDisabled = !TpnsClientReportReq.class.desiredAssertionStatus();
        cache_reportMsgs = new ArrayList();
        cache_reportMsgs.add(new TpnsClientReport());
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsClientReportReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsClientReportReq";
    }

    public ArrayList getReportMsgs() {
        return this.reportMsgs;
    }

    public void setReportMsgs(ArrayList arrayList) {
        this.reportMsgs = arrayList;
    }

    public TpnsClientReportReq() {
        this.reportMsgs = null;
    }

    public TpnsClientReportReq(ArrayList arrayList) {
        this.reportMsgs = null;
        this.reportMsgs = arrayList;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return JceUtil.equals(this.reportMsgs, ((TpnsClientReportReq) obj).reportMsgs);
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
        if (this.reportMsgs != null) {
            jceOutputStream.write((Collection) this.reportMsgs, 1);
        }
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.reportMsgs = (ArrayList) jceInputStream.read(cache_reportMsgs, 1, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).display((Collection) this.reportMsgs, "reportMsgs");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).displaySimple((Collection) this.reportMsgs, false);
    }
}
