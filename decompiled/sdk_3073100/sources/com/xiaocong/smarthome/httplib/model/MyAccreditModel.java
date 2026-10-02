package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MyAccreditModel {
    private List<AuthoredAppListBean> authoredAppList;

    public List<AuthoredAppListBean> getAuthoredAppList() {
        return this.authoredAppList;
    }

    public void setAuthoredAppList(List<AuthoredAppListBean> authoredAppList) {
        this.authoredAppList = authoredAppList;
    }

    public static class AuthoredAppListBean {
        private String appId;
        private String logo;
        private String name;
        private List<String> permissionList;

        public String getAppId() {
            return this.appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return this.logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public List<String> getPermissionList() {
            return this.permissionList;
        }

        public void setPermissionList(List<String> permissionList) {
            this.permissionList = permissionList;
        }
    }
}
