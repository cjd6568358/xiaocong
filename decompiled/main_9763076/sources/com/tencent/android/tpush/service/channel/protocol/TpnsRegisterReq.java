package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;
import org.apache.http.cookie.ClientCookie;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class TpnsRegisterReq extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static DeviceInfo cache_deviceInfo;
    static MutableInfo cache_mutableInfo;
    public long accessId;
    public String accessKey;
    public String account;
    public String appCert;
    public String appVersion;
    public String clientid;
    public String deviceId;
    public DeviceInfo deviceInfo;
    public short deviceType;
    public long guid;
    public byte keyEncrypted;
    public MutableInfo mutableInfo;
    public String reserved;
    public String ticket;
    public short ticketType;
    public String token;
    public short updateAutoTag;
    public short version;

    static {
        $assertionsDisabled = !TpnsRegisterReq.class.desiredAssertionStatus();
        cache_deviceInfo = new DeviceInfo();
        cache_mutableInfo = new MutableInfo();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.TpnsRegisterReq";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.TpnsRegisterReq";
    }

    public long getAccessId() {
        return this.accessId;
    }

    public void setAccessId(long j) {
        this.accessId = j;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public void setAccessKey(String str) {
        this.accessKey = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getAppCert() {
        return this.appCert;
    }

    public void setAppCert(String str) {
        this.appCert = str;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String str) {
        this.account = str;
    }

    public String getTicket() {
        return this.ticket;
    }

    public void setTicket(String str) {
        this.ticket = str;
    }

    public short getTicketType() {
        return this.ticketType;
    }

    public void setTicketType(short s) {
        this.ticketType = s;
    }

    public short getDeviceType() {
        return this.deviceType;
    }

    public void setDeviceType(short s) {
        this.deviceType = s;
    }

    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public short getVersion() {
        return this.version;
    }

    public void setVersion(short s) {
        this.version = s;
    }

    public byte getKeyEncrypted() {
        return this.keyEncrypted;
    }

    public void setKeyEncrypted(byte b) {
        this.keyEncrypted = b;
    }

    public MutableInfo getMutableInfo() {
        return this.mutableInfo;
    }

    public void setMutableInfo(MutableInfo mutableInfo) {
        this.mutableInfo = mutableInfo;
    }

    public short getUpdateAutoTag() {
        return this.updateAutoTag;
    }

    public void setUpdateAutoTag(short s) {
        this.updateAutoTag = s;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String str) {
        this.appVersion = str;
    }

    public String getReserved() {
        return this.reserved;
    }

    public void setReserved(String str) {
        this.reserved = str;
    }

    public String getClientid() {
        return this.clientid;
    }

    public void setClientid(String str) {
        this.clientid = str;
    }

    public long getGuid() {
        return this.guid;
    }

    public void setGuid(long j) {
        this.guid = j;
    }

    public TpnsRegisterReq() {
        this.accessId = 0L;
        this.accessKey = Constants.MAIN_VERSION_TAG;
        this.deviceId = Constants.MAIN_VERSION_TAG;
        this.appCert = Constants.MAIN_VERSION_TAG;
        this.account = Constants.MAIN_VERSION_TAG;
        this.ticket = Constants.MAIN_VERSION_TAG;
        this.ticketType = (short) 0;
        this.deviceType = (short) 0;
        this.deviceInfo = null;
        this.token = Constants.MAIN_VERSION_TAG;
        this.version = (short) 0;
        this.keyEncrypted = (byte) 0;
        this.mutableInfo = null;
        this.updateAutoTag = (short) 0;
        this.appVersion = Constants.MAIN_VERSION_TAG;
        this.reserved = Constants.MAIN_VERSION_TAG;
        this.clientid = Constants.MAIN_VERSION_TAG;
        this.guid = 0L;
    }

    public TpnsRegisterReq(long j, String str, String str2, String str3, String str4, String str5, short s, short s2, DeviceInfo deviceInfo, String str6, short s3, byte b, MutableInfo mutableInfo, short s4, String str7, String str8, String str9, long j2) {
        this.accessId = 0L;
        this.accessKey = Constants.MAIN_VERSION_TAG;
        this.deviceId = Constants.MAIN_VERSION_TAG;
        this.appCert = Constants.MAIN_VERSION_TAG;
        this.account = Constants.MAIN_VERSION_TAG;
        this.ticket = Constants.MAIN_VERSION_TAG;
        this.ticketType = (short) 0;
        this.deviceType = (short) 0;
        this.deviceInfo = null;
        this.token = Constants.MAIN_VERSION_TAG;
        this.version = (short) 0;
        this.keyEncrypted = (byte) 0;
        this.mutableInfo = null;
        this.updateAutoTag = (short) 0;
        this.appVersion = Constants.MAIN_VERSION_TAG;
        this.reserved = Constants.MAIN_VERSION_TAG;
        this.clientid = Constants.MAIN_VERSION_TAG;
        this.guid = 0L;
        this.accessId = j;
        this.accessKey = str;
        this.deviceId = str2;
        this.appCert = str3;
        this.account = str4;
        this.ticket = str5;
        this.ticketType = s;
        this.deviceType = s2;
        this.deviceInfo = deviceInfo;
        this.token = str6;
        this.version = s3;
        this.keyEncrypted = b;
        this.mutableInfo = mutableInfo;
        this.updateAutoTag = s4;
        this.appVersion = str7;
        this.reserved = str8;
        this.clientid = str9;
        this.guid = j2;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        TpnsRegisterReq tpnsRegisterReq = (TpnsRegisterReq) obj;
        return JceUtil.equals(this.accessId, tpnsRegisterReq.accessId) && JceUtil.equals(this.accessKey, tpnsRegisterReq.accessKey) && JceUtil.equals(this.deviceId, tpnsRegisterReq.deviceId) && JceUtil.equals(this.appCert, tpnsRegisterReq.appCert) && JceUtil.equals(this.account, tpnsRegisterReq.account) && JceUtil.equals(this.ticket, tpnsRegisterReq.ticket) && JceUtil.equals(this.ticketType, tpnsRegisterReq.ticketType) && JceUtil.equals(this.deviceType, tpnsRegisterReq.deviceType) && JceUtil.equals(this.deviceInfo, tpnsRegisterReq.deviceInfo) && JceUtil.equals(this.token, tpnsRegisterReq.token) && JceUtil.equals(this.version, tpnsRegisterReq.version) && JceUtil.equals(this.keyEncrypted, tpnsRegisterReq.keyEncrypted) && JceUtil.equals(this.mutableInfo, tpnsRegisterReq.mutableInfo) && JceUtil.equals(this.updateAutoTag, tpnsRegisterReq.updateAutoTag) && JceUtil.equals(this.appVersion, tpnsRegisterReq.appVersion) && JceUtil.equals(this.reserved, tpnsRegisterReq.reserved) && JceUtil.equals(this.clientid, tpnsRegisterReq.clientid) && JceUtil.equals(this.guid, tpnsRegisterReq.guid);
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
        jceOutputStream.write(this.accessKey, 1);
        jceOutputStream.write(this.deviceId, 2);
        jceOutputStream.write(this.appCert, 3);
        if (this.account != null) {
            jceOutputStream.write(this.account, 4);
        }
        if (this.ticket != null) {
            jceOutputStream.write(this.ticket, 5);
        }
        jceOutputStream.write(this.ticketType, 6);
        jceOutputStream.write(this.deviceType, 7);
        if (this.deviceInfo != null) {
            jceOutputStream.write((JceStruct) this.deviceInfo, 8);
        }
        if (this.token != null) {
            jceOutputStream.write(this.token, 9);
        }
        jceOutputStream.write(this.version, 10);
        jceOutputStream.write(this.keyEncrypted, 11);
        if (this.mutableInfo != null) {
            jceOutputStream.write((JceStruct) this.mutableInfo, 12);
        }
        jceOutputStream.write(this.updateAutoTag, 13);
        if (this.appVersion != null) {
            jceOutputStream.write(this.appVersion, 14);
        }
        if (this.reserved != null) {
            jceOutputStream.write(this.reserved, 15);
        }
        if (this.clientid != null) {
            jceOutputStream.write(this.clientid, 16);
        }
        jceOutputStream.write(this.guid, 17);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.accessId = jceInputStream.read(this.accessId, 0, true);
        this.accessKey = jceInputStream.readString(1, true);
        this.deviceId = jceInputStream.readString(2, true);
        this.appCert = jceInputStream.readString(3, true);
        this.account = jceInputStream.readString(4, false);
        this.ticket = jceInputStream.readString(5, false);
        this.ticketType = jceInputStream.read(this.ticketType, 6, false);
        this.deviceType = jceInputStream.read(this.deviceType, 7, false);
        this.deviceInfo = (DeviceInfo) jceInputStream.read((JceStruct) cache_deviceInfo, 8, false);
        this.token = jceInputStream.readString(9, false);
        this.version = jceInputStream.read(this.version, 10, false);
        this.keyEncrypted = jceInputStream.read(this.keyEncrypted, 11, false);
        this.mutableInfo = (MutableInfo) jceInputStream.read((JceStruct) cache_mutableInfo, 12, false);
        this.updateAutoTag = jceInputStream.read(this.updateAutoTag, 13, false);
        this.appVersion = jceInputStream.readString(14, false);
        this.reserved = jceInputStream.readString(15, false);
        this.clientid = jceInputStream.readString(16, false);
        this.guid = jceInputStream.read(this.guid, 17, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.accessId, "accessId");
        jceDisplayer.display(this.accessKey, "accessKey");
        jceDisplayer.display(this.deviceId, Constants.FLAG_DEVICE_ID);
        jceDisplayer.display(this.appCert, "appCert");
        jceDisplayer.display(this.account, Constants.FLAG_ACCOUNT);
        jceDisplayer.display(this.ticket, Constants.FLAG_TICKET);
        jceDisplayer.display(this.ticketType, Constants.FLAG_TICKET_TYPE);
        jceDisplayer.display(this.deviceType, "deviceType");
        jceDisplayer.display((JceStruct) this.deviceInfo, "deviceInfo");
        jceDisplayer.display(this.token, Constants.FLAG_TOKEN);
        jceDisplayer.display(this.version, ClientCookie.VERSION_ATTR);
        jceDisplayer.display(this.keyEncrypted, "keyEncrypted");
        jceDisplayer.display((JceStruct) this.mutableInfo, "mutableInfo");
        jceDisplayer.display(this.updateAutoTag, "updateAutoTag");
        jceDisplayer.display(this.appVersion, "appVersion");
        jceDisplayer.display(this.reserved, "reserved");
        jceDisplayer.display(this.clientid, "clientid");
        jceDisplayer.display(this.guid, "guid");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.accessId, true);
        jceDisplayer.displaySimple(this.accessKey, true);
        jceDisplayer.displaySimple(this.deviceId, true);
        jceDisplayer.displaySimple(this.appCert, true);
        jceDisplayer.displaySimple(this.account, true);
        jceDisplayer.displaySimple(this.ticket, true);
        jceDisplayer.displaySimple(this.ticketType, true);
        jceDisplayer.displaySimple(this.deviceType, true);
        jceDisplayer.displaySimple((JceStruct) this.deviceInfo, true);
        jceDisplayer.displaySimple(this.token, true);
        jceDisplayer.displaySimple(this.version, true);
        jceDisplayer.displaySimple(this.keyEncrypted, true);
        jceDisplayer.displaySimple((JceStruct) this.mutableInfo, true);
        jceDisplayer.displaySimple(this.updateAutoTag, true);
        jceDisplayer.displaySimple(this.appVersion, true);
        jceDisplayer.displaySimple(this.reserved, true);
        jceDisplayer.displaySimple(this.clientid, true);
        jceDisplayer.displaySimple(this.guid, false);
    }
}
