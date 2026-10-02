package com.xiaocong.smarthome.sdk.openapi.bean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCAuthInfoModel {
    private String appName;
    private String logo;
    private String[] permissionList;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String[] getPermissionList() {
        return this.permissionList;
    }

    public void setPermissionList(String[] permissionList) {
        this.permissionList = permissionList;
    }
}
