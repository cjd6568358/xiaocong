package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DevDetailModel {
    public DevDetail device;

    public void setDevice(DevDetail device) {
        this.device = device;
    }

    public DevDetail getDevice() {
        return this.device;
    }

    public class DevDetail {
        private String deviceId;
        private String deviceMac;
        private String deviceName;
        private String deviceType;
        private String deviceVersion;
        private int isAdmin;
        private int isGw;
        private int parameterModifiable;
        private String productId;
        private String productName;
        private String rssi;
        private int sdkId;
        private String showReset;
        private String showRssi;
        private String showShare;
        private String showSsid;
        private String showSwitchRelation;
        private String showUpdate;
        private String ssid;
        private int top;
        private int update;

        public DevDetail() {
        }

        public int getParameterModifiable() {
            return this.parameterModifiable;
        }

        public void setParameterModifiable(int parameterModifiable) {
            this.parameterModifiable = parameterModifiable;
        }

        public int getIsAdmin() {
            return this.isAdmin;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }

        public int getSdkId() {
            return this.sdkId;
        }

        public void setSdkId(int sdkId) {
            this.sdkId = sdkId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setProductName(String productName) {
            this.productName = productName;
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

        public void setUpdate(int update) {
            this.update = update;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public void setIsGw(int isGw) {
            this.isGw = isGw;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
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

        public String getDeviceMac() {
            return this.deviceMac;
        }

        public int getUpdate() {
            return this.update;
        }

        public int getTop() {
            return this.top;
        }

        public String getDeviceType() {
            return this.deviceType;
        }

        public int getIsGw() {
            return this.isGw;
        }

        public String getProductName() {
            return this.productName;
        }

        public String getShowShare() {
            return this.showShare;
        }

        public void setShowShare(String showShare) {
            this.showShare = showShare;
        }

        public String getShowReset() {
            return this.showReset;
        }

        public void setShowReset(String showReset) {
            this.showReset = showReset;
        }

        public String getShowUpdate() {
            return this.showUpdate;
        }

        public void setShowUpdate(String showUpdate) {
            this.showUpdate = showUpdate;
        }

        public String getShowSsid() {
            return this.showSsid;
        }

        public void setShowSsid(String showSsid) {
            this.showSsid = showSsid;
        }

        public String getShowRssi() {
            return this.showRssi;
        }

        public void setShowRssi(String showRssi) {
            this.showRssi = showRssi;
        }

        public String getShowSwitchRelation() {
            return this.showSwitchRelation;
        }

        public void setShowSwitchRelation(String showSwitchRelation) {
            this.showSwitchRelation = showSwitchRelation;
        }

        public String getRssi() {
            return this.rssi;
        }

        public void setRssi(String rssi) {
            this.rssi = rssi;
        }

        public String getSsid() {
            return this.ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String toString() {
            return "DevDetail{deviceId='" + this.deviceId + "', deviceName='" + this.deviceName + "', productId=" + this.productId + ", deviceMac='" + this.deviceMac + "', version='" + this.deviceVersion + "',update=" + this.update + "'}";
        }
    }
}
