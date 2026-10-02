package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CheckRnModel {
    private String downloadUrl;
    private String fileName;
    private int upgrade;
    private String version;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getUpgrade() {
        return this.upgrade;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public String getVersion() {
        return this.version;
    }
}
