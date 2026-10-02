package com.xiaocong.smarthome.httplib.model;

import com.xiaocong.smarthome.greendao.model.insert.SceneDB;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class ScenePanelConfigModel {
    private IftttListModel.IftttTriggers deviceTrigger;
    private List<SceneDB> scenes;

    public List<SceneDB> getScenes() {
        return this.scenes;
    }

    public void setScenes(List<SceneDB> scenes) {
        this.scenes = scenes;
    }

    public IftttListModel.IftttTriggers getDeviceTrigger() {
        return this.deviceTrigger;
    }

    public void setDeviceTrigger(IftttListModel.IftttTriggers deviceTrigger) {
        this.deviceTrigger = deviceTrigger;
    }

    public IftttListModel.IftttTriggers getTrigger() {
        return this.deviceTrigger;
    }
}
