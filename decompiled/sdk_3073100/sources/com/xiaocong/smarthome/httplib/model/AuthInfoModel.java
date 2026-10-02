package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class AuthInfoModel {
    private String appName;
    private String logo;
    private String[] permissionList;
    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setPermissionList(String[] permissionList) {
        this.permissionList = permissionList;
    }

    public int getStatus() {
        return this.status;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getLogo() {
        return this.logo;
    }

    public String[] getPermissionList() {
        return this.permissionList;
    }
}
