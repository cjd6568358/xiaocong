package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;
import java.util.ArrayList;
import java.util.Collection;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsReconnectReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static ArrayList cache_msgClickList;
    static MutableInfo cache_mutableInfo;
    static ArrayList cache_recvMsgList;
    static ArrayList cache_unregInfoList;
    public long connVersion;
    public String deviceId;
    public short deviceType;
    public long guid;
    public ArrayList msgClickList;
    public MutableInfo mutableInfo;
    public short networkType;
    public ArrayList recvMsgList;
    public String reserved;
    public String sdkVersion;
    public String token;
    public ArrayList unregInfoList;

    static {
        $assertionsDisabled = !TpnsReconnectReq.class.desiredAssertionStatus();
        cache_unregInfoList = new ArrayList();
        cache_unregInfoList.add(new UnregInfo());
        cache_recvMsgList = new ArrayList();
        cache_recvMsgList.add(new TpnsPushClientReport());
        cache_mutableInfo = new MutableInfo();
        cache_msgClickList = new ArrayList();
        cache_msgClickList.add(new TpnsClickClientReport());
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsReconnectReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsReconnectReq";
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public short getNetworkType() {
        return this.networkType;
    }

    public void setNetworkType(short s) {
        this.networkType = s;
    }

    public ArrayList getUnregInfoList() {
        return this.unregInfoList;
    }

    public void setUnregInfoList(ArrayList arrayList) {
        this.unregInfoList = arrayList;
    }

    public ArrayList getRecvMsgList() {
        return this.recvMsgList;
    }

    public void setRecvMsgList(ArrayList arrayList) {
        this.recvMsgList = arrayList;
    }

    public MutableInfo getMutableInfo() {
        return this.mutableInfo;
    }

    public void setMutableInfo(MutableInfo mutableInfo) {
        this.mutableInfo = mutableInfo;
    }

    public short getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(short s) {
        this.deviceType = s;
    }

    public ArrayList getMsgClickList() {
        return this.msgClickList;
    }

    public void setMsgClickList(ArrayList arrayList) {
        this.msgClickList = arrayList;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public void setSdkVersion(String str) {
        this.sdkVersion = str;
    }

    public String getReserved() {
        return this.reserved;
    }

    public void setReserved(String str) {
        this.reserved = str;
    }

    public long getConnVersion() {
        return this.connVersion;
    }

    public void setConnVersion(long j) {
        this.connVersion = j;
    }

    public long getGuid() {
        return this.guid;
    }

    public void setGuid(long j) {
        this.guid = j;
    }

    public TpnsReconnectReq() {
        this.token = Constants.MAIN_VERSION_TAG;
        this.deviceId = Constants.MAIN_VERSION_TAG;
        this.networkType = (short) 0;
        this.unregInfoList = null;
        this.recvMsgList = null;
        this.mutableInfo = null;
        this.deviceType = (short) 0;
        this.msgClickList = null;
        this.sdkVersion = Constants.MAIN_VERSION_TAG;
        this.reserved = Constants.MAIN_VERSION_TAG;
        this.connVersion = 0L;
        this.guid = 0L;
    }

    public TpnsReconnectReq(String str, String str2, short s, ArrayList arrayList, ArrayList arrayList2, MutableInfo mutableInfo, short s2, ArrayList arrayList3, String str3, String str4, long j, long j2) {
        this.token = Constants.MAIN_VERSION_TAG;
        this.deviceId = Constants.MAIN_VERSION_TAG;
        this.networkType = (short) 0;
        this.unregInfoList = null;
        this.recvMsgList = null;
        this.mutableInfo = null;
        this.deviceType = (short) 0;
        this.msgClickList = null;
        this.sdkVersion = Constants.MAIN_VERSION_TAG;
        this.reserved = Constants.MAIN_VERSION_TAG;
        this.connVersion = 0L;
        this.guid = 0L;
        this.token = str;
        this.deviceId = str2;
        this.networkType = s;
        this.unregInfoList = arrayList;
        this.recvMsgList = arrayList2;
        this.mutableInfo = mutableInfo;
        this.deviceType = s2;
        this.msgClickList = arrayList3;
        this.sdkVersion = str3;
        this.reserved = str4;
        this.connVersion = j;
        this.guid = j2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsReconnectReq tpnsReconnectReq = (TpnsReconnectReq) obj;
        return JceUtil.equals(this.token, tpnsReconnectReq.token) && JceUtil.equals(this.deviceId, tpnsReconnectReq.deviceId) && JceUtil.equals(this.networkType, tpnsReconnectReq.networkType) && JceUtil.equals(this.unregInfoList, tpnsReconnectReq.unregInfoList) && JceUtil.equals(this.recvMsgList, tpnsReconnectReq.recvMsgList) && JceUtil.equals(this.mutableInfo, tpnsReconnectReq.mutableInfo) && JceUtil.equals(this.deviceType, tpnsReconnectReq.deviceType) && JceUtil.equals(this.msgClickList, tpnsReconnectReq.msgClickList) && JceUtil.equals(this.sdkVersion, tpnsReconnectReq.sdkVersion) && JceUtil.equals(this.reserved, tpnsReconnectReq.reserved) && JceUtil.equals(this.connVersion, tpnsReconnectReq.connVersion) && JceUtil.equals(this.guid, tpnsReconnectReq.guid);
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
        jceOutputStream.write(this.deviceId, 1);
        jceOutputStream.write(this.networkType, 2);
        if (this.unregInfoList != null) {
            jceOutputStream.write((Collection) this.unregInfoList, 3);
        }
        if (this.recvMsgList != null) {
            jceOutputStream.write((Collection) this.recvMsgList, 4);
        }
        if (this.mutableInfo != null) {
            jceOutputStream.write((JceStruct) this.mutableInfo, 5);
        }
        jceOutputStream.write(this.deviceType, 6);
        if (this.msgClickList != null) {
            jceOutputStream.write((Collection) this.msgClickList, 7);
        }
        if (this.sdkVersion != null) {
            jceOutputStream.write(this.sdkVersion, 8);
        }
        if (this.reserved != null) {
            jceOutputStream.write(this.reserved, 9);
        }
        jceOutputStream.write(this.connVersion, 10);
        jceOutputStream.write(this.guid, 11);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.token = jceInputStream.readString(0, true);
        this.deviceId = jceInputStream.readString(1, true);
        this.networkType = jceInputStream.read(this.networkType, 2, true);
        this.unregInfoList = (ArrayList) jceInputStream.read(cache_unregInfoList, 3, false);
        this.recvMsgList = (ArrayList) jceInputStream.read(cache_recvMsgList, 4, false);
        this.mutableInfo = (MutableInfo) jceInputStream.read((JceStruct) cache_mutableInfo, 5, false);
        this.deviceType = jceInputStream.read(this.deviceType, 6, false);
        this.msgClickList = (ArrayList) jceInputStream.read(cache_msgClickList, 7, false);
        this.sdkVersion = jceInputStream.readString(8, false);
        this.reserved = jceInputStream.readString(9, false);
        this.connVersion = jceInputStream.read(this.connVersion, 10, false);
        this.guid = jceInputStream.read(this.guid, 11, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.token, Constants.FLAG_TOKEN);
        jceDisplayer.display(this.deviceId, Constants.FLAG_DEVICE_ID);
        jceDisplayer.display(this.networkType, "networkType");
        jceDisplayer.display((Collection) this.unregInfoList, "unregInfoList");
        jceDisplayer.display((Collection) this.recvMsgList, "recvMsgList");
        jceDisplayer.display((JceStruct) this.mutableInfo, "mutableInfo");
        jceDisplayer.display(this.deviceType, "deviceType");
        jceDisplayer.display((Collection) this.msgClickList, "msgClickList");
        jceDisplayer.display(this.sdkVersion, "sdkVersion");
        jceDisplayer.display(this.reserved, "reserved");
        jceDisplayer.display(this.connVersion, "connVersion");
        jceDisplayer.display(this.guid, "guid");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.token, true);
        jceDisplayer.displaySimple(this.deviceId, true);
        jceDisplayer.displaySimple(this.networkType, true);
        jceDisplayer.displaySimple((Collection) this.unregInfoList, true);
        jceDisplayer.displaySimple((Collection) this.recvMsgList, true);
        jceDisplayer.displaySimple((JceStruct) this.mutableInfo, true);
        jceDisplayer.displaySimple(this.deviceType, true);
        jceDisplayer.displaySimple((Collection) this.msgClickList, true);
        jceDisplayer.displaySimple(this.sdkVersion, true);
        jceDisplayer.displaySimple(this.reserved, true);
        jceDisplayer.displaySimple(this.connVersion, true);
        jceDisplayer.displaySimple(this.guid, false);
    }
}
