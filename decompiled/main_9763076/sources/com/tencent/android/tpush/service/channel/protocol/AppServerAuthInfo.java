package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class AppServerAuthInfo extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public long accessId;
    public String secretKey;

    static {
        $assertionsDisabled = !AppServerAuthInfo.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.AppServerAuthInfo";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.AppServerAuthInfo";
    }

    public long getAccessId() {
        return this.accessId;
    }

    public void setAccessId(long j) {
        this.accessId = j;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String str) {
        this.secretKey = str;
    }

    public AppServerAuthInfo() {
        this.accessId = 0L;
        this.secretKey = Constants.MAIN_VERSION_TAG;
    }

    public AppServerAuthInfo(long j, String str) {
        this.accessId = 0L;
        this.secretKey = Constants.MAIN_VERSION_TAG;
        this.accessId = j;
        this.secretKey = str;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        AppServerAuthInfo appServerAuthInfo = (AppServerAuthInfo) obj;
        return JceUtil.equals(this.accessId, appServerAuthInfo.accessId) && JceUtil.equals(this.secretKey, appServerAuthInfo.secretKey);
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
        jceOutputStream.write(this.secretKey, 1);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.accessId = jceInputStream.read(this.accessId, 0, true);
        this.secretKey = jceInputStream.readString(1, true);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.accessId, "accessId");
        jceDisplayer.display(this.secretKey, "secretKey");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.accessId, true);
        jceDisplayer.displaySimple(this.secretKey, false);
    }
}
