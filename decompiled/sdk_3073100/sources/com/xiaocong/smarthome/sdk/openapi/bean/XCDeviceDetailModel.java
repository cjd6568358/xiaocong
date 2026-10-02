package com.xiaocong.smarthome.sdk.openapi.bean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCDeviceDetailModel {
    public DevDetail device;

    public DevDetail getDevice() {
        return this.device;
    }

    public void setDevice(DevDetail device) {
        this.device = device;
    }

    public class DevDetail {
        private String deviceId;
        private String deviceMac;
        private String deviceName;
        private String deviceType;
        private String deviceVersion;
        private int isGw;
        private String productId;
        private int top;
        private int update;

        public DevDetail() {
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getProductId() {
            return this.productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getDeviceMac() {
            return this.deviceMac;
        }

        public void setDeviceMac(String deviceMac) {
            this.deviceMac = deviceMac;
        }

        public String getDeviceVersion() {
            return this.deviceVersion;
        }

        public void setDeviceVersion(String deviceVersion) {
            this.deviceVersion = deviceVersion;
        }

        public int getUpdate() {
            return this.update;
        }

        public void setUpdate(int update) {
            this.update = update;
        }

        public int getTop() {
            return this.top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getIsGw() {
            return this.isGw;
        }

        public void setIsGw(int isGw) {
            this.isGw = isGw;
        }

        public String getDeviceType() {
            return this.deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }
    }
}
