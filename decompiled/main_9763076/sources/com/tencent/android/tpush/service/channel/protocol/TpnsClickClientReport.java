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
public final class TpnsClickClientReport extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public long accessId;
    public long action;
    public long broadcastId;
    public long clickTime;
    public long msgId;
    public long timestamp;
    public long type;

    static {
        $assertionsDisabled = !TpnsClickClientReport.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsClickClientReport";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsClickClientReport";
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

    public long getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(long j) {
        this.broadcastId = j;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public long getType() {
        return this.type;
    }

    public void setType(long j) {
        this.type = j;
    }

    public long getClickTime() {
        return this.clickTime;
    }

    public void setClickTime(long j) {
        this.clickTime = j;
    }

    public long getAction() {
        return this.action;
    }

    public void setAction(long j) {
        this.action = j;
    }

    public TpnsClickClientReport() {
        this.msgId = 0L;
        this.accessId = 0L;
        this.broadcastId = 0L;
        this.timestamp = 0L;
        this.type = 0L;
        this.clickTime = 0L;
        this.action = 0L;
    }

    public TpnsClickClientReport(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
        this.msgId = 0L;
        this.accessId = 0L;
        this.broadcastId = 0L;
        this.timestamp = 0L;
        this.type = 0L;
        this.clickTime = 0L;
        this.action = 0L;
        this.msgId = j;
        this.accessId = j2;
        this.broadcastId = j3;
        this.timestamp = j4;
        this.type = j5;
        this.clickTime = j6;
        this.action = j7;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsClickClientReport tpnsClickClientReport = (TpnsClickClientReport) obj;
        return JceUtil.equals(this.msgId, tpnsClickClientReport.msgId) && JceUtil.equals(this.accessId, tpnsClickClientReport.accessId) && JceUtil.equals(this.broadcastId, tpnsClickClientReport.broadcastId) && JceUtil.equals(this.timestamp, tpnsClickClientReport.timestamp) && JceUtil.equals(this.type, tpnsClickClientReport.type) && JceUtil.equals(this.clickTime, tpnsClickClientReport.clickTime) && JceUtil.equals(this.action, tpnsClickClientReport.action);
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
        jceOutputStream.write(this.broadcastId, 2);
        jceOutputStream.write(this.timestamp, 3);
        jceOutputStream.write(this.type, 4);
        jceOutputStream.write(this.clickTime, 5);
        jceOutputStream.write(this.action, 6);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.msgId = jceInputStream.read(this.msgId, 0, true);
        this.accessId = jceInputStream.read(this.accessId, 1, true);
        this.broadcastId = jceInputStream.read(this.broadcastId, 2, false);
        this.timestamp = jceInputStream.read(this.timestamp, 3, false);
        this.type = jceInputStream.read(this.type, 4, false);
        this.clickTime = jceInputStream.read(this.clickTime, 5, false);
        this.action = jceInputStream.read(this.action, 6, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.msgId, MessageKey.MSG_ID);
        jceDisplayer.display(this.accessId, "accessId");
        jceDisplayer.display(this.broadcastId, "broadcastId");
        jceDisplayer.display(this.timestamp, "timestamp");
        jceDisplayer.display(this.type, "type");
        jceDisplayer.display(this.clickTime, Constants.FLAG_CLICK_TIME);
        jceDisplayer.display(this.action, "action");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.msgId, true);
        jceDisplayer.displaySimple(this.accessId, true);
        jceDisplayer.displaySimple(this.broadcastId, true);
        jceDisplayer.displaySimple(this.timestamp, true);
        jceDisplayer.displaySimple(this.type, true);
        jceDisplayer.displaySimple(this.clickTime, true);
        jceDisplayer.displaySimple(this.action, false);
    }
}
