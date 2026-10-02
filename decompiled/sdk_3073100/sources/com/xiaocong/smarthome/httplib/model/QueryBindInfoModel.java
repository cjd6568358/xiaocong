package com.xiaocong.smarthome.httplib.model;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class QueryBindInfoModel {
    private String deviceId;
    private String deviceName;
    private int isAdmin;
    private int isBind;
    private String phone;
    private String productId;
    private String productImage;
    private String productName;

    public void setIsBind(int isBind) {
        this.isBind = isBind;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductImage() {
        return this.productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getIsBind() {
        return this.isBind;
    }

    public int getIsAdmin() {
        return this.isAdmin;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public String getPhone() {
        return this.phone;
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
        return this.productImage;
    }

    public void setProductImg(String productImg) {
        this.productImage = productImg;
    }
}
