package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsTokenTagReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public long accessId;
    public int flag;
    public String tag;

    static {
        $assertionsDisabled = !TpnsTokenTagReq.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsTokenTagReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsTokenTagReq";
    }

    public long getAccessId() {
        return this.accessId;
    }

    public void setAccessId(long j) {
        this.accessId = j;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String str) {
        this.tag = str;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int i) {
        this.flag = i;
    }

    public TpnsTokenTagReq() {
        this.accessId = 0L;
        this.tag = Constants.MAIN_VERSION_TAG;
        this.flag = 0;
    }

    public TpnsTokenTagReq(long j, String str, int i) {
        this.accessId = 0L;
        this.tag = Constants.MAIN_VERSION_TAG;
        this.flag = 0;
        this.accessId = j;
        this.tag = str;
        this.flag = i;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsTokenTagReq tpnsTokenTagReq = (TpnsTokenTagReq) obj;
        return JceUtil.equals(this.accessId, tpnsTokenTagReq.accessId) && JceUtil.equals(this.tag, tpnsTokenTagReq.tag) && JceUtil.equals(this.flag, tpnsTokenTagReq.flag);
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
        jceOutputStream.write(this.accessId, 0);
        jceOutputStream.write(this.tag, 1);
        jceOutputStream.write(this.flag, 2);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.accessId = jceInputStream.read(this.accessId, 0, true);
        this.tag = jceInputStream.readString(1, true);
        this.flag = jceInputStream.read(this.flag, 2, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.accessId, "accessId");
        jceDisplayer.display(this.tag, "tag");
        jceDisplayer.display(this.flag, "flag");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.accessId, true);
        jceDisplayer.displaySimple(this.tag, true);
        jceDisplayer.displaySimple(this.flag, false);
    }
}
