package com.ixiaocong.smarthome.phone.android.common.manager;

import android.content.Context;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class SceneEditManager {
    public static SceneEditManager getInstance() {
        return SceneEditManagerHolder.editManager;
    }

    public void startScene(final Context context, int sceneId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("sceneId", String.valueOf(sceneId));
        httpSetting.setParamsMap(params);
        httpSetting.setPath("scene/start");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.SceneEditManager.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "场景启用成功");
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, var1.getErrorMessage());
            }
        });
    }

    private static class SceneEditManagerHolder {
        private static final SceneEditManager editManager = new SceneEditManager();
    }
}
