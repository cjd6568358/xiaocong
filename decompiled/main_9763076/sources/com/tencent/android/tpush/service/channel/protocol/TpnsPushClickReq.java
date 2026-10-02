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
public final class TpnsPushClickReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static ArrayList cache_msgClickList;
    public ArrayList msgClickList;

    static {
        $assertionsDisabled = !TpnsPushClickReq.class.desiredAssertionStatus();
        cache_msgClickList = new ArrayList();
        cache_msgClickList.add(new TpnsClickClientReport());
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsPushClickReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsPushClickReq";
    }

    public ArrayList getMsgClickList() {
        return this.msgClickList;
    }

    public void setMsgClickList(ArrayList arrayList) {
        this.msgClickList = arrayList;
    }

    public TpnsPushClickReq() {
        this.msgClickList = null;
    }

    public TpnsPushClickReq(ArrayList arrayList) {
        this.msgClickList = null;
        this.msgClickList = arrayList;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return JceUtil.equals(this.msgClickList, ((TpnsPushClickReq) obj).msgClickList);
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
        jceOutputStream.write((Collection) this.msgClickList, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.msgClickList = (ArrayList) jceInputStream.read(cache_msgClickList, 1, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).display((Collection) this.msgClickList, "msgClickList");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).displaySimple((Collection) this.msgClickList, false);
    }
}
