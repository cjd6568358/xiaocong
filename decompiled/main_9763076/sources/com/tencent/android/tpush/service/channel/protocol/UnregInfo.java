package com.tencent.android.tpush.service.channel.protocol;

import com.qq.taf.jce.JceDisplayer;
import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.qq.taf.jce.JceUtil;

/* JADX INFO: compiled from: ProGuard */
/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public final class UnregInfo extends JceStruct implements Cloneable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static AppInfo cache_appInfo;
    public AppInfo appInfo;
    public byte isUninstall;
    public long timestamp;

    static {
        $assertionsDisabled = !UnregInfo.class.desiredAssertionStatus();
        cache_appInfo = new AppInfo();
    }

    public String className() {
        return "TPNS_CLIENT_PROTOCOL.UnregInfo";
    }

    public String fullClassName() {
        return "com.tencent.android.tpush.service.channel.protocol.UnregInfo";
    }

    public AppInfo getAppInfo() {
        return this.appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public byte getIsUninstall() {
        return this.isUninstall;
    }

    public void setIsUninstall(byte b) {
        this.isUninstall = b;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public UnregInfo() {
        this.appInfo = null;
        this.isUninstall = (byte) 0;
        this.timestamp = 0L;
    }

    public UnregInfo(AppInfo appInfo, byte b, long j) {
        this.appInfo = null;
        this.isUninstall = (byte) 0;
        this.timestamp = 0L;
        this.appInfo = appInfo;
        this.isUninstall = b;
        this.timestamp = j;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        UnregInfo unregInfo = (UnregInfo) obj;
        return JceUtil.equals(this.appInfo, unregInfo.appInfo) && JceUtil.equals(this.isUninstall, unregInfo.isUninstall) && JceUtil.equals(this.timestamp, unregInfo.timestamp);
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
        jceOutputStream.write((JceStruct) this.appInfo, 0);
        jceOutputStream.write(this.isUninstall, 1);
        jceOutputStream.write(this.timestamp, 2);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void readFrom(JceInputStream jceInputStream) {
        this.appInfo = (AppInfo) jceInputStream.read((JceStruct) cache_appInfo, 0, true);
        this.isUninstall = jceInputStream.read(this.isUninstall, 1, true);
        this.timestamp = jceInputStream.read(this.timestamp, 2, false);
    }

    @Override // com.qq.taf.jce.JceStruct
    public void display(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.display((JceStruct) this.appInfo, "appInfo");
        jceDisplayer.display(this.isUninstall, "isUninstall");
        jceDisplayer.display(this.timestamp, "timestamp");
    }

    @Override // com.qq.taf.jce.JceStruct
    public void displaySimple(StringBuilder sb, int i) {
        JceDisplayer jceDisplayer = new JceDisplayer(sb, i);
        jceDisplayer.displaySimple((JceStruct) this.appInfo, true);
        jceDisplayer.displaySimple(this.isUninstall, true);
        jceDisplayer.displaySimple(this.timestamp, false);
    }
}
