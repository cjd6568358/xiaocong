package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsTriggerReportRsp extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public byte padding;

    static {
        $assertionsDisabled = !TpnsTriggerReportRsp.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsTriggerReportRsp";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsTriggerReportRsp";
    }

    public byte getPadding() {
        return this.padding;
    }

    public void setPadding(byte b) {
        this.padding = b;
    }

    public TpnsTriggerReportRsp() {
        this.padding = (byte) 0;
    }

    public TpnsTriggerReportRsp(byte b) {
        this.padding = (byte) 0;
        this.padding = b;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return JceUtil.equals(this.padding, ((TpnsTriggerReportRsp) obj).padding);
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
        jceOutputStream.write(this.padding, 0);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.padding = jceInputStream.read(this.padding, 0, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).display(this.padding, "padding");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        new JceDisplayer(sb, i).displaySimple(this.padding, false);
    }
}
