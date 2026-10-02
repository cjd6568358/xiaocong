package com.xiaocong.smarthome.httplib.model.scene;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class TriggerDeviceListModel {
    private List<SceneDeviceModel> commonList;
    private List<SceneDeviceModel> deviceList;

    public void setDeviceList(List<SceneDeviceModel> deviceList) {
        this.deviceList = deviceList;
    }

    public void setCommonList(List<SceneDeviceModel> commonList) {
        this.commonList = commonList;
    }

    public List<SceneDeviceModel> getCommonList() {
        return this.commonList;
    }

    public List<SceneDeviceModel> getDeviceList() {
        return this.deviceList;
    }
}
