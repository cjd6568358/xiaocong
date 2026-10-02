package com.ixiaocong.smarthome.phone.android.event.callback;

import com.xiaocong.smarthome.httplib.model.request.SceneCommandsModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface EditSceneCallback {
    void deleteOnlyDeviceListener(boolean z, int i);

    void deleteSceneListener(boolean z);

    void editSceneListener(SceneCommandsModel.CommandsModel commandsModel);

    void renameSceneListener(String str);

    void updateSceneListener(SceneCommandsModel.CommandsModel commandsModel);
}
