package com.ixiaocong.smarthome.phone.android.common.manager;

import android.content.Context;
import android.content.Intent;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddSuccessActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.model.QueryBindInfoModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BindDeviceManager {
    public static void bindOtherDevice(final Context context, final String deviceId, final String productName, final String devImgUrl) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/bind");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.BindDeviceManager.1
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(context, "设备绑定成功");
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                Intent intent = new Intent(context, (Class<?>) DeviceAddSuccessActivity.class);
                intent.putExtra(Constants.FLAG_DEVICE_ID, deviceId);
                intent.putExtra("productName", productName);
                intent.putExtra("productImg", devImgUrl);
                context.startActivity(intent);
                ActivityManagerUtil.getScreenManager().popAllActivity();
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }

    public static void queryOtherDeviceInfo(final Context context, final HintDialogCallback callback, final String deviceId) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/getBindInfo");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.BindDeviceManager.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                QueryBindInfoModel infoModel = (QueryBindInfoModel) JSON.parseObject(var1.getData(), QueryBindInfoModel.class);
                if (infoModel.getIsBind() == 0) {
                    BindDeviceManager.bindOtherDevice(context, deviceId, infoModel.getDeviceName(), infoModel.getProductImg());
                    return;
                }
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                if (infoModel.getIsAdmin() == 0) {
                    OperationHintDialog.getInstance().showHintDialog(context, callback, "友情提示", "设备编号: " + deviceId + "\n已被账号: " + infoModel.getPhone() + " 绑定", "我知道了");
                } else {
                    OperationHintDialog.getInstance().showHintDialog(context, callback, "友情提示", "该设备已绑定在您当前账户下", "我知道了");
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
            }
        });
    }
}
