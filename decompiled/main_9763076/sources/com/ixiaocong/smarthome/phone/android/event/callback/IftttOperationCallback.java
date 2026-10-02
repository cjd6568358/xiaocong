package com.ixiaocong.smarthome.phone.android.event.callback;

import com.xiaocong.smarthome.httplib.model.scene.RelateActionModel;
import com.xiaocong.smarthome.httplib.model.scene.SceneDetailModel;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public interface IftttOperationCallback {
    void addRelateCallback(RelateActionModel relateActionModel);

    void deleteRelateCallback(boolean z, int i);

    void updateRelateCallback(int i, RelateActionModel relateActionModel);

    void updateTriggerCallback(int i, SceneDetailModel.TriggerModel triggerModel);
}
