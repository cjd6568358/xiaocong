package com.huawei.hms.support.api.entity.auth;

import com.huawei.hms.core.aidl.IMessageEntity;
import com.huawei.hms.core.aidl.a.a;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class AppInfo implements IMessageEntity {

    @a
    private String appID;

    @a
    private String packageName;

    public AppInfo() {
    }

    public AppInfo(String str, String str2) {
        this.appID = str;
        this.packageName = str2;
    }

    public String getAppID() {
        return this.appID;
    }

    public void setAppID(String str) {
        this.appID = str;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }
}
