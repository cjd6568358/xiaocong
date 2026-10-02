package com.xiaocong.smarthome.sdk.openapi.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCSDKGatewayModel {
    private List<GwModel> devices;

    public List<GwModel> getDevices() {
        return this.devices;
    }

    public void setDevices(List<GwModel> devices) {
        this.devices = devices;
    }

    public class GwModel {
        private String deviceId;
        private String deviceName;
        private int status;

        public GwModel() {
        }

        public String getDeviceId() {
            return this.deviceId;
        }

        public String getDeviceName() {
            return this.deviceName;
        }

        public int getStatus() {
            return this.status;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
