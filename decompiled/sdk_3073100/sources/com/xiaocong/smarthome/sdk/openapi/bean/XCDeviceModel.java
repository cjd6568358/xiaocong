package com.xiaocong.smarthome.sdk.openapi.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCDeviceModel {
    private List<XCDeviceParameterModel> controlParameter;
    private String deviceId;
    private String deviceMac;
    private String deviceName;
    private String deviceSn;
    private String displayMessage;
    private int isAdmin;
    private boolean isDownload;
    private String partner;
    private int productId;
    private String productImage;
    private int progress;
    private String snapshot;
    private int status;
    private int top;

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

    public List<XCDeviceParameterModel> getControlParameter() {
        return this.controlParameter;
    }

    public void setControlParameter(List<XCDeviceParameterModel> controlParameter) {
        this.controlParameter = controlParameter;
    }

    public String getDisplayMessage() {
        return this.displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
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

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isDownload() {
        return this.isDownload;
    }

    public void setDownload(boolean download) {
        this.isDownload = download;
    }
}
