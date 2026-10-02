package com.xiaocong.smarthome.sdk.openapi.bean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCConfigModel {
    private String clientId;
    private String clientKey;
    private String liveServerUrl;
    private long timestamp;
    private String uid;
    private String upgrade;
    private String upgradeDownloadUrl;
    private String upgradeIntro;
    private String upgradeMode;
    private String upgradeVersion;

    public String getUpgrade() {
        return this.upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    public String getUpgradeMode() {
        return this.upgradeMode;
    }

    public void setUpgradeMode(String upgradeMode) {
        this.upgradeMode = upgradeMode;
    }

    public String getUpgradeDownloadUrl() {
        return this.upgradeDownloadUrl;
    }

    public void setUpgradeDownloadUrl(String upgradeDownloadUrl) {
        this.upgradeDownloadUrl = upgradeDownloadUrl;
    }

    public String getUpgradeVersion() {
        return this.upgradeVersion;
    }

    public void setUpgradeVersion(String upgradeVersion) {
        this.upgradeVersion = upgradeVersion;
    }

    public String getUpgradeIntro() {
        return this.upgradeIntro;
    }

    public void setUpgradeIntro(String upgradeIntro) {
        this.upgradeIntro = upgradeIntro;
    }

    public String getClientKey() {
        return this.clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getLiveServerUrl() {
        return this.liveServerUrl;
    }

    public void setLiveServerUrl(String liveServerUrl) {
        this.liveServerUrl = liveServerUrl;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
