package com.xiaocong.smarthome.sdk.openapi.bean;

import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCScenePanelConfigModel {
    private XCDeviceRelationListModel.TriggersBean deviceTrigger;
    private List<XCSceneModel> scenes;

    public List<XCSceneModel> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<XCSceneModel> scenes) {
        this.scenes = scenes;
    }

    public XCDeviceRelationListModel.TriggersBean getDeviceTrigger() {
        return this.deviceTrigger;
    }

    public void setDeviceTrigger(XCDeviceRelationListModel.TriggersBean deviceTrigger) {
        this.deviceTrigger = deviceTrigger;
    }
}
