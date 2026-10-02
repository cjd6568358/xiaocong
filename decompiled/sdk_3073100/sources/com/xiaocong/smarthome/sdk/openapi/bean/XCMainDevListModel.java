package com.xiaocong.smarthome.sdk.openapi.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCMainDevListModel {
    public List<XCDeviceModel> devices;
    public List<XCSceneModel> scenes;

    public List<XCDeviceModel> getDevices() {
        return this.devices;
    }

    public void setDevices(List<XCDeviceModel> devices) {
        this.devices = devices;
    }

    public List<XCSceneModel> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<XCSceneModel> scenes) {
        this.scenes = scenes;
    }
}
