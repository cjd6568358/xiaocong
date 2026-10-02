package com.xiaocong.smarthome.sdk.mqtt.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCManageMessage extends XCMessage {
    private String deviceId;
    private String moduleId;
    private String productId;
    private String type;

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}
