package com.xiaocong.smarthome.greendao.model.insert;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class RNVersionDB {
    private String version;
    private long xcId;

    public RNVersionDB(long xcId, String version) {
        this.xcId = xcId;
        this.version = version;
    }

    public RNVersionDB() {
    }

    public long getXcId() {
        return this.xcId;
    }

    public void setXcId(long xcId) {
        this.xcId = xcId;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
