package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsRegisterRsp extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public long confVersion;
    public long guid;
    public String token;

    static {
        $assertionsDisabled = !TpnsRegisterRsp.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsRegisterRsp";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsRegisterRsp";
    }

    public long getConfVersion() {
        return this.confVersion;
    }

    public void setConfVersion(long j) {
        this.confVersion = j;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public long getGuid() {
        return this.guid;
    }

    public void setGuid(long j) {
        this.guid = j;
    }

    public TpnsRegisterRsp() {
        this.confVersion = 0L;
        this.token = Constants.MAIN_VERSION_TAG;
        this.guid = 0L;
    }

    public TpnsRegisterRsp(long j, String str, long j2) {
        this.confVersion = 0L;
        this.token = Constants.MAIN_VERSION_TAG;
        this.guid = 0L;
        this.confVersion = j;
        this.token = str;
        this.guid = j2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsRegisterRsp tpnsRegisterRsp = (TpnsRegisterRsp) obj;
        return JceUtil.equals(this.confVersion, tpnsRegisterRsp.confVersion) && JceUtil.equals(this.token, tpnsRegisterRsp.token) && JceUtil.equals(this.guid, tpnsRegisterRsp.guid);
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
        jceOutputStream.write(this.token, 1);
        jceOutputStream.write(this.guid, 2);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.confVersion = jceInputStream.read(this.confVersion, 0, true);
        this.token = jceInputStream.readString(1, true);
        this.guid = jceInputStream.read(this.guid, 2, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.confVersion, "confVersion");
        jceDisplayer.display(this.token, Constants.FLAG_TOKEN);
        jceDisplayer.display(this.guid, "guid");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.confVersion, true);
        jceDisplayer.displaySimple(this.token, true);
        jceDisplayer.displaySimple(this.guid, false);
    }
}
