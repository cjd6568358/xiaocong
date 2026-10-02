package com.xiaocong.smarthome.greendao.model.insert;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class MainDeviceDB {
    private String deviceId;
    private String deviceMac;
    private String deviceName;
    private String deviceSn;
    private int isAdmin;
    private String partner;
    private int productId;
    private String productImage;
    private String snapshot;
    private int status;
    private int top;

    public MainDeviceDB(String deviceId, String deviceName, int productId, String productImage, int isAdmin, int top, String snapshot, int status, String partner, String deviceMac, String deviceSn) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.productId = productId;
        this.productImage = productImage;
        this.isAdmin = isAdmin;
        this.top = top;
        this.snapshot = snapshot;
        this.status = status;
        this.partner = partner;
        this.deviceMac = deviceMac;
        this.deviceSn = deviceSn;
    }

    public MainDeviceDB() {
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

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return this.productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getSnapshot() {
        return this.snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPartner() {
        return this.partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getDeviceMac() {
        return this.deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceSn() {
        return this.deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }
}
