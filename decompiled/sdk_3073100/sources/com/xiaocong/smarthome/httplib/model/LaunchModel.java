package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class LaunchModel {
    private String clientId;
    private String clientKey;
    private String liveServerUrl;
    private long timestamp;
    private String uid;

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getLiveServerUrl() {
        return this.liveServerUrl;
    }

    public void setLiveServerUrl(String liveServerUrl) {
        this.liveServerUrl = liveServerUrl;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClientKey() {
        return this.clientKey;
    }

    public String getClientId() {
        return this.clientId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public String getUid() {
        return this.uid;
    }

    public String toString() {
        return "LaunchModel{clientKey='" + this.clientKey + "', live='" + this.liveServerUrl + "', cliendId='" + this.clientId + "', timestamp='" + this.timestamp + "', uid='" + this.uid + "'}";
    }
}
