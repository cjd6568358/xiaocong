package com.xiaocong.smarthome.httplib.model;

import com.xiaocong.smarthome.httplib.model.inside.DeviceParameterModel;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class DeviceListModel {
    private List<DeviceParameterModel> controlParameter;
    private String deviceId;
    private String deviceMac;
    private String deviceName;
    private String deviceSn;
    private String displayMessage;
    private String groupId;
    private int isAdmin;
    private boolean isDownload;
    private String partner;
    private int productId;
    private String productImage;
    private int progress;
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

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public void setControlParameter(List<DeviceParameterModel> controlParameter) {
        this.controlParameter = controlParameter;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
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

    public String getPartner() {
        return this.partner;
    }

    public List<DeviceParameterModel> getControlParameter() {
        return this.controlParameter;
    }

    public String getDisplayMessage() {
        return this.displayMessage;
    }

    public String getDeviceMac() {
        return this.deviceMac;
    }

    public String getDeviceSn() {
        return this.deviceSn;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
