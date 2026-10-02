package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ChildDevicesModel {
    private List<ChildDevice> devices;

    public void setDevices(List<ChildDevice> devices) {
        this.devices = devices;
    }

    public List<ChildDevice> getDevices() {
        return this.devices;
    }

    public static class ChildDevice {
        private String deviceId;
        private String deviceName;
        private int isAdmin;
        private int productId;
        private String productImage;
        private String snapshot;
        private int status;
        private int top;

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public void setSnapshot(String snapshot) {
            this.snapshot = snapshot;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public int getProductId() {
            return this.productId;
        }

        public String getProductImage() {
            return this.productImage;
        }

        public int getIsAdmin() {
            return this.isAdmin;
        }

        public int getTop() {
            return this.top;
        }

        public String getSnapshot() {
            return this.snapshot;
        }

        public int getStatus() {
            return this.status;
        }
    }
}
