package com.huawei.hms.core.aidl;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RequestHeader implements IMessageEntity {

    @com.huawei.hms.core.aidl.a.a
    private String appId;

    @com.huawei.hms.core.aidl.a.a
    private String packageName;

    @com.huawei.hms.core.aidl.a.a
    private int sdkVersion;

    @com.huawei.hms.core.aidl.a.a
    private String sessionId;

    public RequestHeader() {
    }

    public RequestHeader(String str, String str2, int i, String str3) {
        this.appId = str;
        this.packageName = str2;
        this.sdkVersion = i;
        this.sessionId = str3;
    }

    public void setAppID(String str) {
        this.appId = str;
    }

    public String getAppID() {
        return this.appId;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setSdkVersion(int i) {
        this.sdkVersion = i;
    }

    public int getSdkVersion() {
        return this.sdkVersion;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public String getSessionId() {
        return this.sessionId;
    }
}
