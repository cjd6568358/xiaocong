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
public final class TpnsReconnectRsp extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static ArrayList cache_appOfflinePushMsgList;
    public ArrayList appOfflinePushMsgList;
    public long confVersion;
    public long timeUs;

    static {
        $assertionsDisabled = !TpnsReconnectRsp.class.desiredAssertionStatus();
        cache_appOfflinePushMsgList = new ArrayList();
        cache_appOfflinePushMsgList.add(new TpnsPushMsg());
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsReconnectRsp";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsReconnectRsp";
    }

    public long getConfVersion() {
        return this.confVersion;
    }

    public void setConfVersion(long j) {
        this.confVersion = j;
    }

    public ArrayList getAppOfflinePushMsgList() {
        return this.appOfflinePushMsgList;
    }

    public void setAppOfflinePushMsgList(ArrayList arrayList) {
        this.appOfflinePushMsgList = arrayList;
    }

    public long getTimeUs() {
        return this.timeUs;
    }

    public void setTimeUs(long j) {
        this.timeUs = j;
    }

    public TpnsReconnectRsp() {
        this.confVersion = 0L;
        this.appOfflinePushMsgList = null;
        this.timeUs = 0L;
    }

    public TpnsReconnectRsp(long j, ArrayList arrayList, long j2) {
        this.confVersion = 0L;
        this.appOfflinePushMsgList = null;
        this.timeUs = 0L;
        this.confVersion = j;
        this.appOfflinePushMsgList = arrayList;
        this.timeUs = j2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsReconnectRsp tpnsReconnectRsp = (TpnsReconnectRsp) obj;
        return JceUtil.equals(this.confVersion, tpnsReconnectRsp.confVersion) && JceUtil.equals(this.appOfflinePushMsgList, tpnsReconnectRsp.appOfflinePushMsgList) && JceUtil.equals(this.timeUs, tpnsReconnectRsp.timeUs);
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
        if (this.appOfflinePushMsgList != null) {
            jceOutputStream.write((Collection) this.appOfflinePushMsgList, 1);
        }
        jceOutputStream.write(this.timeUs, 2);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.confVersion = jceInputStream.read(this.confVersion, 0, true);
        this.appOfflinePushMsgList = (ArrayList) jceInputStream.read(cache_appOfflinePushMsgList, 1, false);
        this.timeUs = jceInputStream.read(this.timeUs, 2, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.confVersion, "confVersion");
        jceDisplayer.display((Collection) this.appOfflinePushMsgList, "appOfflinePushMsgList");
        jceDisplayer.display(this.timeUs, "timeUs");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.confVersion, true);
        jceDisplayer.displaySimple((Collection) this.appOfflinePushMsgList, true);
        jceDisplayer.displaySimple(this.timeUs, false);
    }
}
