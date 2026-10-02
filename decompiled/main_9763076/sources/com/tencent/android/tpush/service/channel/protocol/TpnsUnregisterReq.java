package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsUnregisterReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static UnregInfo cache_unregInfo;
    public short deviceType;
    public UnregInfo unregInfo;

    static {
        $assertionsDisabled = !TpnsUnregisterReq.class.desiredAssertionStatus();
        cache_unregInfo = new UnregInfo();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsUnregisterReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsUnregisterReq";
    }

    public UnregInfo getUnregInfo() {
        return this.unregInfo;
    }

    public void setUnregInfo(UnregInfo unregInfo) {
        this.unregInfo = unregInfo;
    }

    public short getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(short s) {
        this.deviceType = s;
    }

    public TpnsUnregisterReq() {
        this.unregInfo = null;
        this.deviceType = (short) 0;
    }

    public TpnsUnregisterReq(UnregInfo unregInfo, short s) {
        this.unregInfo = null;
        this.deviceType = (short) 0;
        this.unregInfo = unregInfo;
        this.deviceType = s;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsUnregisterReq tpnsUnregisterReq = (TpnsUnregisterReq) obj;
        return JceUtil.equals(this.unregInfo, tpnsUnregisterReq.unregInfo) && JceUtil.equals(this.deviceType, tpnsUnregisterReq.deviceType);
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
        jceOutputStream.write((JceStruct) this.unregInfo, 0);
        jceOutputStream.write(this.deviceType, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.unregInfo = (UnregInfo) jceInputStream.read((JceStruct) cache_unregInfo, 0, true);
        this.deviceType = jceInputStream.read(this.deviceType, 1, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display((JceStruct) this.unregInfo, "unregInfo");
        jceDisplayer.display(this.deviceType, "deviceType");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple((JceStruct) this.unregInfo, true);
        jceDisplayer.displaySimple(this.deviceType, false);
    }
}
