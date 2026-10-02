package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SceneDevicesModel {
    private List<DevicesBean> devices;

    public void setDevices(List<DevicesBean> devices) {
        this.devices = devices;
    }

    public List<DevicesBean> getDevices() {
        return this.devices;
    }

    public static class DevicesBean {
        private String deviceId;
        private String deviceName;
        private String productId;
        private String productImage;

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public String getProductId() {
            return this.productId;
        }

        public String getProductImage() {
            return this.productImage;
        }
    }
}
