package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.api.MidEntity;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class MutableInfo extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    public String bssid;
    public String mac;
    public String ssid;
    public String wflist;

    static {
        $assertionsDisabled = !MutableInfo.class.desiredAssertionStatus();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.MutableInfo";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.MutableInfo";
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public String getBssid() {
        return this.bssid;
    }

    public void setBssid(String str) {
        this.bssid = str;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public String getWflist() {
        return this.wflist;
    }

    public void setWflist(String str) {
        this.wflist = str;
    }

    public MutableInfo() {
        this.ssid = Constants.MAIN_VERSION_TAG;
        this.bssid = Constants.MAIN_VERSION_TAG;
        this.mac = Constants.MAIN_VERSION_TAG;
        this.wflist = Constants.MAIN_VERSION_TAG;
    }

    public MutableInfo(String str, String str2, String str3, String str4) {
        this.ssid = Constants.MAIN_VERSION_TAG;
        this.bssid = Constants.MAIN_VERSION_TAG;
        this.mac = Constants.MAIN_VERSION_TAG;
        this.wflist = Constants.MAIN_VERSION_TAG;
        this.ssid = str;
        this.bssid = str2;
        this.mac = str3;
        this.wflist = str4;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        MutableInfo mutableInfo = (MutableInfo) obj;
        return JceUtil.equals(this.ssid, mutableInfo.ssid) && JceUtil.equals(this.bssid, mutableInfo.bssid) && JceUtil.equals(this.mac, mutableInfo.mac) && JceUtil.equals(this.wflist, mutableInfo.wflist);
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
        if (this.ssid != null) {
            jceOutputStream.write(this.ssid, 0);
        }
        if (this.bssid != null) {
            jceOutputStream.write(this.bssid, 1);
        }
        if (this.mac != null) {
            jceOutputStream.write(this.mac, 2);
        }
        if (this.wflist != null) {
            jceOutputStream.write(this.wflist, 3);
        }
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.ssid = jceInputStream.readString(0, false);
        this.bssid = jceInputStream.readString(1, false);
        this.mac = jceInputStream.readString(2, false);
        this.wflist = jceInputStream.readString(3, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display(this.ssid, "ssid");
        jceDisplayer.display(this.bssid, "bssid");
        jceDisplayer.display(this.mac, MidEntity.TAG_MAC);
        jceDisplayer.display(this.wflist, "wflist");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple(this.ssid, true);
        jceDisplayer.displaySimple(this.bssid, true);
        jceDisplayer.displaySimple(this.mac, true);
        jceDisplayer.displaySimple(this.wflist, false);
    }
}
