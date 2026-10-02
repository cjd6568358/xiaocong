package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsConfigRsp extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public String confContent;
    public long confVersion;

    static {
        $assertionsDisabled = !TpnsConfigRsp.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsConfigRsp";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsConfigRsp";
    }

    public long getConfVersion() {
        return this.confVersion;
    }

    public void setConfVersion(long j) {
        this.confVersion = j;
    }

    public String getConfContent() {
        return this.confContent;
    }

    public void setConfContent(String str) {
        this.confContent = str;
    }

    public TpnsConfigRsp() {
        this.confVersion = 0L;
        this.confContent = Constants.MAIN_VERSION_TAG;
    }

    public TpnsConfigRsp(long j, String str) {
        this.confVersion = 0L;
        this.confContent = Constants.MAIN_VERSION_TAG;
        this.confVersion = j;
        this.confContent = str;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsConfigRsp tpnsConfigRsp = (TpnsConfigRsp) obj;
        return JceUtil.equals(this.confVersion, tpnsConfigRsp.confVersion) && JceUtil.equals(this.confContent, tpnsConfigRsp.confContent);
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
        jceOutputStream.write(this.confVersion, 0);
        jceOutputStream.write(this.confContent, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.confVersion = jceInputStream.read(this.confVersion, 0, true);
        this.confContent = jceInputStream.readString(1, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.confVersion, "confVersion");
        jceDisplayer.display(this.confContent, "confContent");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.confVersion, true);
        jceDisplayer.displaySimple(this.confContent, false);
    }
}
