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
public final class TpnsPushVerifyReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static ArrayList cache_msgReportList;
    public ArrayList msgReportList;

    static {
        $assertionsDisabled = !TpnsPushVerifyReq.class.desiredAssertionStatus();
        cache_msgReportList = new ArrayList();
        cache_msgReportList.add(new TpnsPushClientReport());
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsPushVerifyReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsPushVerifyReq";
    }

    public ArrayList getMsgReportList() {
        return this.msgReportList;
    }

    public void setMsgReportList(ArrayList arrayList) {
        this.msgReportList = arrayList;
    }

    public TpnsPushVerifyReq() {
        this.msgReportList = null;
    }

    public TpnsPushVerifyReq(ArrayList arrayList) {
        this.msgReportList = null;
        this.msgReportList = arrayList;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return JceUtil.equals(this.msgReportList, ((TpnsPushVerifyReq) obj).msgReportList);
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
        jceOutputStream.write((Collection) this.msgReportList, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.msgReportList = (ArrayList) jceInputStream.read(cache_msgReportList, 1, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).display((Collection) this.msgReportList, "msgReportList");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).displaySimple((Collection) this.msgReportList, false);
    }
}
