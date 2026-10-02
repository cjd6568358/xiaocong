package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SystemMsgSettingModel {
    private List<DevicesBean> devices;
    private int notification;

    public int getNotification() {
        return this.notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
    }

    public List<DevicesBean> getDevices() {
        return this.devices;
    }

    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

    public static class DevicesBean {
        private String deviceName;
        private int notification;
        private int userDeviceId;

        public int getNotification() {
            return this.notification;
        }

        public void setNotification(int notification) {
            this.notification = notification;
        }

        public int getUserDeviceId() {
            return this.userDeviceId;
        }

        public void setUserDeviceId(int userDeviceId) {
            this.userDeviceId = userDeviceId;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }
    }
}
