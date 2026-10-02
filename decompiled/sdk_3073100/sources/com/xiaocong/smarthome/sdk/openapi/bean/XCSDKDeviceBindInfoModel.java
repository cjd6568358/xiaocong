package com.xiaocong.smarthome.sdk.openapi.bean;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCSDKDeviceBindInfoModel {
    private String deviceId;
    private String deviceName;
    private int isAdmin;
    private int isBind;
    private String phone;
    private String productId;
    private String productImg;
    private String productName;

    public int getIsBind() {
        return this.isBind;
    }

    public void setIsBind(int isBind) {
        this.isBind = isBind;
    }

    public int getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return this.productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
