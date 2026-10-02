package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SharedDeviceModel {
    private List<DeviceListBean> deviceList;

    public List<DeviceListBean> getDeviceList() {
        return this.deviceList;
    }

    public void setDeviceList(List<DeviceListBean> deviceList) {
        this.deviceList = deviceList;
    }

    public static class DeviceListBean {
        private int count;
        private String deviceId;
        private String deviceName;
        private String productImage;

        public int getCount() {
            return this.count;
        }

        public void setCount(int count) {
            this.count = count;
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

        public String getProductImage() {
            return this.productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }
    }
}
