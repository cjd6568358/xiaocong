package com.ixiaocong.smarthome.phone.android.common.manager;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.event.callback.DeviceDetailCallBack;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.model.DevDetailModel;
import com.xiaocong.smarthome.httplib.utils.SpUtils;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class DeviceDetailManager {
    public static void requestDevDetail(final Context context, final DeviceDetailCallBack callBack, String deviceId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/detail");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.DeviceDetailManager.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                DevDetailModel devDetailModel = (DevDetailModel) JSON.parseObject(var1.getData(), DevDetailModel.class);
                callBack.requestDetailSuccess(devDetailModel);
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, var1.getErrorMessage());
            }
        });
    }

    public static void deleteDeviceHttp(final Context context, final DeviceDetailCallBack callBack, String deviceId, int productId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("productId", String.valueOf(productId));
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/unbind");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.DeviceDetailManager.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                callBack.unbindDeviceResponse(true);
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, var1.getErrorMessage());
            }
        });
    }

    public static void unsharedDeviceHttp(final Context context, final DeviceDetailCallBack callBack, String deviceId) {
        String uid = Constants.MAIN_VERSION_TAG;
        if (!TextUtils.isEmpty((CharSequence) SpUtils.getFromLocal(context, "xiao_cong_uid", "xiao_cong_uid", Constants.MAIN_VERSION_TAG))) {
            uid = (String) SpUtils.getFromLocal(context, "xiao_cong_uid", "xiao_cong_uid", Constants.MAIN_VERSION_TAG);
        }
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("uid", String.valueOf(uid));
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("share/cancel");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.DeviceDetailManager.4
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                callBack.unbindDeviceResponse(true);
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, var1.getErrorMessage());
            }
        });
    }
}
