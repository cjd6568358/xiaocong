package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsGetApListRsp extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static ApList cache_apList;
    public ApList apList;

    static {
        $assertionsDisabled = !TpnsGetApListRsp.class.desiredAssertionStatus();
        cache_apList = new ApList();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsGetApListRsp";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsGetApListRsp";
    }

    public ApList getApList() {
        return this.apList;
    }

    public void setApList(ApList apList) {
        this.apList = apList;
    }

    public TpnsGetApListRsp() {
        this.apList = null;
    }

    public TpnsGetApListRsp(ApList apList) {
        this.apList = null;
        this.apList = apList;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return JceUtil.equals(this.apList, ((TpnsGetApListRsp) obj).apList);
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
        jceOutputStream.write((JceStruct) this.apList, 0);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.apList = (ApList) jceInputStream.read((JceStruct) cache_apList, 0, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).display((JceStruct) this.apList, "apList");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).displaySimple((JceStruct) this.apList, false);
    }
}
