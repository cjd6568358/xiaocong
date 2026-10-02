package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsPushMsg extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public long accessId;
    public String appPkgName;
    public long busiMsgId;
    public String content;
    public String date;
    public long msgId;
    public long multiPkg;
    public long serverTime;
    public long timestamp;
    public String title;
    public int ttl;
    public long type;

    static {
        $assertionsDisabled = !TpnsPushMsg.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsPushMsg";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsPushMsg";
    }

    public long getMsgId() {
        return this.msgId;
    }

    public void setMsgId(long j) {
        this.msgId = j;
    }

    public long getAccessId() {
        return this.accessId;
    }

    public void setAccessId(long j) {
        this.accessId = j;
    }

    public long getBusiMsgId() {
        return this.busiMsgId;
    }

    public void setBusiMsgId(long j) {
        this.busiMsgId = j;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public long getType() {
        return this.type;
    }

    public void setType(long j) {
        this.type = j;
    }

    public String getAppPkgName() {
        return this.appPkgName;
    }

    public void setAppPkgName(String str) {
        this.appPkgName = str;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public long getMultiPkg() {
        return this.multiPkg;
    }

    public void setMultiPkg(long j) {
        this.multiPkg = j;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public long getServerTime() {
        return this.serverTime;
    }

    public void setServerTime(long j) {
        this.serverTime = j;
    }

    public int getTtl() {
        return this.ttl;
    }

    public void setTtl(int i) {
        this.ttl = i;
    }

    public TpnsPushMsg() {
        this.msgId = 0L;
        this.accessId = 0L;
        this.busiMsgId = 0L;
        this.title = Constants.MAIN_VERSION_TAG;
        this.content = Constants.MAIN_VERSION_TAG;
        this.type = 0L;
        this.appPkgName = Constants.MAIN_VERSION_TAG;
        this.timestamp = 0L;
        this.multiPkg = 0L;
        this.date = Constants.MAIN_VERSION_TAG;
        this.serverTime = 0L;
        this.ttl = 0;
    }

    public TpnsPushMsg(long j, long j2, long j3, String str, String str2, long j4, String str3, long j5, long j6, String str4, long j7, int i) {
        this.msgId = 0L;
        this.accessId = 0L;
        this.busiMsgId = 0L;
        this.title = Constants.MAIN_VERSION_TAG;
        this.content = Constants.MAIN_VERSION_TAG;
        this.type = 0L;
        this.appPkgName = Constants.MAIN_VERSION_TAG;
        this.timestamp = 0L;
        this.multiPkg = 0L;
        this.date = Constants.MAIN_VERSION_TAG;
        this.serverTime = 0L;
        this.ttl = 0;
        this.msgId = j;
        this.accessId = j2;
        this.busiMsgId = j3;
        this.title = str;
        this.content = str2;
        this.type = j4;
        this.appPkgName = str3;
        this.timestamp = j5;
        this.multiPkg = j6;
        this.date = str4;
        this.serverTime = j7;
        this.ttl = i;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsPushMsg tpnsPushMsg = (TpnsPushMsg) obj;
        return JceUtil.equals(this.msgId, tpnsPushMsg.msgId) && JceUtil.equals(this.accessId, tpnsPushMsg.accessId) && JceUtil.equals(this.busiMsgId, tpnsPushMsg.busiMsgId) && JceUtil.equals(this.title, tpnsPushMsg.title) && JceUtil.equals(this.content, tpnsPushMsg.content) && JceUtil.equals(this.type, tpnsPushMsg.type) && JceUtil.equals(this.appPkgName, tpnsPushMsg.appPkgName) && JceUtil.equals(this.timestamp, tpnsPushMsg.timestamp) && JceUtil.equals(this.multiPkg, tpnsPushMsg.multiPkg) && JceUtil.equals(this.date, tpnsPushMsg.date) && JceUtil.equals(this.serverTime, tpnsPushMsg.serverTime) && JceUtil.equals(this.ttl, tpnsPushMsg.ttl);
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
        jceOutputStream.write(this.msgId, 0);
        jceOutputStream.write(this.accessId, 1);
        jceOutputStream.write(this.busiMsgId, 2);
        jceOutputStream.write(this.title, 3);
        jceOutputStream.write(this.content, 4);
        jceOutputStream.write(this.type, 5);
        if (this.appPkgName != null) {
            jceOutputStream.write(this.appPkgName, 6);
        }
        jceOutputStream.write(this.timestamp, 7);
        jceOutputStream.write(this.multiPkg, 8);
        if (this.date != null) {
            jceOutputStream.write(this.date, 9);
        }
        jceOutputStream.write(this.serverTime, 10);
        jceOutputStream.write(this.ttl, 11);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.msgId = jceInputStream.read(this.msgId, 0, true);
        this.accessId = jceInputStream.read(this.accessId, 1, true);
        this.busiMsgId = jceInputStream.read(this.busiMsgId, 2, true);
        this.title = jceInputStream.readString(3, true);
        this.content = jceInputStream.readString(4, true);
        this.type = jceInputStream.read(this.type, 5, true);
        this.appPkgName = jceInputStream.readString(6, false);
        this.timestamp = jceInputStream.read(this.timestamp, 7, false);
        this.multiPkg = jceInputStream.read(this.multiPkg, 8, false);
        this.date = jceInputStream.readString(9, false);
        this.serverTime = jceInputStream.read(this.serverTime, 10, false);
        this.ttl = jceInputStream.read(this.ttl, 11, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.msgId, MessageKey.MSG_ID);
        jceDisplayer.display(this.accessId, "accessId");
        jceDisplayer.display(this.busiMsgId, MessageKey.MSG_BUSI_MSG_ID);
        jceDisplayer.display(this.title, "title");
        jceDisplayer.display(this.content, "content");
        jceDisplayer.display(this.type, "type");
        jceDisplayer.display(this.appPkgName, "appPkgName");
        jceDisplayer.display(this.timestamp, "timestamp");
        jceDisplayer.display(this.multiPkg, MessageKey.MSG_CREATE_MULTIPKG);
        jceDisplayer.display(this.date, MessageKey.MSG_DATE);
        jceDisplayer.display(this.serverTime, "serverTime");
        jceDisplayer.display(this.ttl, MessageKey.MSG_TTL);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.msgId, true);
        jceDisplayer.displaySimple(this.accessId, true);
        jceDisplayer.displaySimple(this.busiMsgId, true);
        jceDisplayer.displaySimple(this.title, true);
        jceDisplayer.displaySimple(this.content, true);
        jceDisplayer.displaySimple(this.type, true);
        jceDisplayer.displaySimple(this.appPkgName, true);
        jceDisplayer.displaySimple(this.timestamp, true);
        jceDisplayer.displaySimple(this.multiPkg, true);
        jceDisplayer.displaySimple(this.date, true);
        jceDisplayer.displaySimple(this.serverTime, true);
        jceDisplayer.displaySimple(this.ttl, false);
    }
}
