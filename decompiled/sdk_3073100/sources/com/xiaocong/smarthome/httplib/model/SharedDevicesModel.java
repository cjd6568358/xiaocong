package com.xiaocong.smarthome.httplib.model;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class SharedDevicesModel {
    private List<SharedDevices> list;

    public void setList(List<SharedDevices> list) {
        this.list = list;
    }

    public List<SharedDevices> getList() {
        return this.list;
    }

    public class SharedDevices {
        private int count;
        private String deviceId;
        private String deviceName;
        private boolean isChecked;
        private int productId;
        private String productImage;

        public SharedDevices() {
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getProductImage() {
            return this.productImage;
        }

        public int getProductId() {
            return this.productId;
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public int getCount() {
            return this.count;
        }

        public void setChecked(boolean checked) {
            this.isChecked = checked;
        }

        public boolean isChecked() {
            return this.isChecked;
        }
    }
}
