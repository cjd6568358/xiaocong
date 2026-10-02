package com.tencent.android.tpush.data;

import com.tencent.android.tpush.common.Constants;
import java.io.Serializable;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class MessageId implements Serializable {
    public static final short FLAG_ACK = 1;
    public static final short FLAG_UNACK = 0;
    private static final long serialVersionUID = 8708157897391765794L;
    public long accessId;
    public byte apn;
    public long host;
    public long id;
    public short isAck;
    public byte isp;
    public byte pact;
    public String pkgName;
    public int port;
    public long pushTime;
    public long receivedTime;
    public long serverTime;
    public String serviceHost;
    public long ttl;
    public long busiMsgId = 0;
    public long timestamp = 0;
    public long msgType = -1;
    public long multiPkg = 0;
    public String date = Constants.MAIN_VERSION_TAG;

    public boolean a() {
        return this.isAck == 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MessageId [id=").append(this.id).append(", isAck=").append((int) this.isAck).append(", isp=").append((int) this.isp).append(", apn=").append((int) this.apn).append(", accessId=").append(this.accessId).append(", pushTime=").append(this.pushTime).append(", receivedTime=").append(this.receivedTime).append(", pact=").append((int) this.pact).append(", host=").append(this.host).append(", port=").append(this.port).append(", serviceHost=").append(this.serviceHost).append(", pkgName=").append(this.pkgName).append(", busiMsgId=").append(this.busiMsgId).append(", timestamp=").append(this.timestamp).append(", msgType=").append(this.msgType).append(", multiPkg=").append(this.multiPkg).append(", date=").append(this.date).append(", serverTime=").append(this.serverTime).append(", ttl=").append(this.ttl).append("]");
        return sb.toString();
    }
}
