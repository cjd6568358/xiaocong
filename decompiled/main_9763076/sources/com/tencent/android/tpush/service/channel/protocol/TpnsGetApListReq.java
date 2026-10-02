package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsGetApListReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static NetworkInfo cache_netInfo;
    public NetworkInfo netInfo;

    static {
        $assertionsDisabled = !TpnsGetApListReq.class.desiredAssertionStatus();
        cache_netInfo = new NetworkInfo();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsGetApListReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsGetApListReq";
    }

    public NetworkInfo getNetInfo() {
        return this.netInfo;
    }

    public void setNetInfo(NetworkInfo networkInfo) {
        this.netInfo = networkInfo;
    }

    public TpnsGetApListReq() {
        this.netInfo = null;
    }

    public TpnsGetApListReq(NetworkInfo networkInfo) {
        this.netInfo = null;
        this.netInfo = networkInfo;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return JceUtil.equals(this.netInfo, ((TpnsGetApListReq) obj).netInfo);
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
        jceOutputStream.write((JceStruct) this.netInfo, 0);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.netInfo = (NetworkInfo) jceInputStream.read((JceStruct) cache_netInfo, 0, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).display((JceStruct) this.netInfo, "netInfo");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).displaySimple((JceStruct) this.netInfo, false);
    }
}
