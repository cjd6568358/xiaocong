package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class HomeDeviceListModel {
    private List<DeviceListBean> deviceList;

    public List<DeviceListBean> getDeviceList() {
        return this.deviceList;
    }

    public void setDeviceList(List<DeviceListBean> deviceList) {
        this.deviceList = deviceList;
    }

    public static class DeviceListBean {
        private String deviceId;
        private String groupId;
        private String image;
        private String name;

        public String getImage() {
            return this.image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGroupId() {
            return this.groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }
}
