package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsCheckMsgReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public long guid;
    public String token;

    static {
        $assertionsDisabled = !TpnsCheckMsgReq.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsCheckMsgReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsCheckMsgReq";
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

    public TpnsCheckMsgReq() {
        this.token = Constants.MAIN_VERSION_TAG;
        this.guid = 0L;
    }

    public TpnsCheckMsgReq(String str, long j) {
        this.token = Constants.MAIN_VERSION_TAG;
        this.guid = 0L;
        this.token = str;
        this.guid = j;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsCheckMsgReq tpnsCheckMsgReq = (TpnsCheckMsgReq) obj;
        return JceUtil.equals(this.token, tpnsCheckMsgReq.token) && JceUtil.equals(this.guid, tpnsCheckMsgReq.guid);
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
        jceOutputStream.write(this.token, 0);
        jceOutputStream.write(this.guid, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.token = jceInputStream.readString(0, true);
        this.guid = jceInputStream.read(this.guid, 1, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.token, Constants.FLAG_TOKEN);
        jceDisplayer.display(this.guid, "guid");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.token, true);
        jceDisplayer.displaySimple(this.guid, false);
    }
}
