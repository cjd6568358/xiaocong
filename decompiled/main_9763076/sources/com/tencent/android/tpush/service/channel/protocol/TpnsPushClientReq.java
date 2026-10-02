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
public final class TpnsPushClientReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static ArrayList cache_msgList;
    public ArrayList msgList;
    public long timeUs;

    static {
        $assertionsDisabled = !TpnsPushClientReq.class.desiredAssertionStatus();
        cache_msgList = new ArrayList();
        cache_msgList.add(new TpnsPushMsg());
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsPushClientReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsPushClientReq";
    }

    public ArrayList getMsgList() {
        return this.msgList;
    }

    public void setMsgList(ArrayList arrayList) {
        this.msgList = arrayList;
    }

    public long getTimeUs() {
        return this.timeUs;
    }

    public void setTimeUs(long j) {
        this.timeUs = j;
    }

    public TpnsPushClientReq() {
        this.msgList = null;
        this.timeUs = 0L;
    }

    public TpnsPushClientReq(ArrayList arrayList, long j) {
        this.msgList = null;
        this.timeUs = 0L;
        this.msgList = arrayList;
        this.timeUs = j;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsPushClientReq tpnsPushClientReq = (TpnsPushClientReq) obj;
        return JceUtil.equals(this.msgList, tpnsPushClientReq.msgList) && JceUtil.equals(this.timeUs, tpnsPushClientReq.timeUs);
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
        jceOutputStream.write((Collection) this.msgList, 0);
        jceOutputStream.write(this.timeUs, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.msgList = (ArrayList) jceInputStream.read(cache_msgList, 0, true);
        this.timeUs = jceInputStream.read(this.timeUs, 1, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display((Collection) this.msgList, "msgList");
        jceDisplayer.display(this.timeUs, "timeUs");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple((Collection) this.msgList, true);
        jceDisplayer.displaySimple(this.timeUs, false);
    }
}
