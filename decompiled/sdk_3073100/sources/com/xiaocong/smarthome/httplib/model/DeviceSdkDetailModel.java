package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceSdkDetailModel {
    private String deviceVersion;
    private String sdkIntro;
    private String sdkVersion;
    private int status;

    public String getSdkIntro() {
        return this.sdkIntro;
    }

    public void setSdkIntro(String sdkIntro) {
        this.sdkIntro = sdkIntro;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public String getDeviceVersion() {
        return this.deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
