package com.ixiaocong.smarthome.phone.android.common.manager;

import android.content.Context;
import android.content.Intent;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.RenameCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RenameRequestManager {
    public static void deviceRenameRequest(final Context context, final RenameCallback callback, final String deivceId, final String commitName) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deivceId);
        HashMap<String, Object> noSignParams = new HashMap<>();
        noSignParams.put("deviceName", commitName);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(noSignParams);
        httpSetting.setPath("device/update");
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.RenameRequestManager.1
            public void onComplete(XCResponseBean var1) {
                ToastUtils.showShort(context, "您的设备名称已修改成功");
                Intent intent = new Intent("renameParameterAction");
                intent.putExtra("deviceName", commitName);
                intent.putExtra(Constants.FLAG_DEVICE_ID, deivceId);
                context.sendBroadcast(intent);
                callback.deviceRenameListener(true, commitName);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
            }
        });
    }

    public static void userRenameRequest(final Context context, final RenameCallback callback, final String nickName) {
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("nickname", nickName);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("user/modifyNickname");
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.RenameRequestManager.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "您的昵称已修改成功");
                callback.userRenameListener(true, nickName);
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, var1.getErrorMessage());
            }
        });
    }
}
