package com.ixiaocong.smarthome.phone.android.common.manager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.ixiaocong.smarthome.phone.android.common.utils.ActivityManagerUtil;
import com.ixiaocong.smarthome.phone.android.complete.prompt.dialog.OperationHintDialog;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.ixiaocong.smarthome.phone.android.detail.activity.device.config.DeviceAddSuccessActivity;
import com.ixiaocong.smarthome.phone.android.event.callback.HintDialogCallback;
import com.ixiaocong.smarthome.phone.softap.callback.XConfigSoftApCallback;
import com.ixiaocong.smarthome.phone.softap.utils.SoftApStage;
import com.tencent.android.tpush.common.Constants;
import com.tencent.mid.api.MidEntity;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.QueryBindInfoModel;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class BindSoftApManager {
    public static void bindSoftApDevice(final Context context, final String deviceId, final String productId, final String mac, final String productName, final String devImgUrl, final int discoverWay) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> signParams = new HashMap<>();
        HashMap<String, Object> commonParams = new HashMap<>();
        signParams.put(Constants.FLAG_DEVICE_ID, deviceId);
        commonParams.put("discoverWay", discoverWay == 0 ? "coap" : "api");
        commonParams.put("discoverTime", SoftApStage.getInstance().getDiscoverTime());
        httpSetting.setParamsMap(signParams);
        httpSetting.setParamsMapNoSign(commonParams);
        httpSetting.setPath("device/bind");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.BindSoftApManager.1
            public void onComplete(XCResponseBean var1) {
                ToastUtils.showShort(context, "设备绑定成功");
                HashMap<String, Object> signParams2 = new HashMap<>();
                signParams2.put("productId", productId);
                signParams2.put("errorCode", 0);
                HashMap<String, Object> commonParams2 = new HashMap<>();
                commonParams2.put("deviceMac", mac);
                commonParams2.put(Constants.FLAG_DEVICE_ID, deviceId);
                commonParams2.put("discoverTime", SoftApStage.getInstance().getDiscoverTime());
                commonParams2.put("discoverWay", discoverWay == 0 ? "coap" : "api");
                BindSoftApManager.bindDeviceLog(context, signParams2, commonParams2);
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
                HashMap<String, Object> signParams2 = new HashMap<>();
                signParams2.put("productId", productId);
                signParams2.put("errorCode", 2);
                HashMap<String, Object> commonParams2 = new HashMap<>();
                commonParams2.put("deviceMac", mac);
                commonParams2.put(Constants.FLAG_DEVICE_ID, deviceId);
                commonParams2.put("discoverTime", SoftApStage.getInstance().getDiscoverTime());
                commonParams2.put("discoverWay", discoverWay == 0 ? "coap" : "api");
                BindSoftApManager.bindDeviceLog(context, signParams2, commonParams2);
                SoftApStage.getInstance().setError(true);
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ActivityManagerUtil.getScreenManager().popAllActivity();
            }
        });
    }

    public static void querySoftApDeviceInfo(final Context context, final HintDialogCallback callback, final String deviceId, final String productId, final String mac, final int discoverWay) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/getBindInfo");
        HttpLoadingHelper.getInstance().showProcessLoading(context);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.BindSoftApManager.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                QueryBindInfoModel infoModel = (QueryBindInfoModel) JSON.parseObject(var1.getData(), QueryBindInfoModel.class);
                if (infoModel.getIsBind() == 0) {
                    BindSoftApManager.bindSoftApDevice(context, deviceId, productId, mac, infoModel.getDeviceName(), infoModel.getProductImg(), discoverWay);
                } else if (infoModel.getIsAdmin() == 0) {
                    OperationHintDialog.getInstance().showHintDialog(context, callback, "友情提示", "设备编号: " + deviceId + "\n已被账号: " + infoModel.getPhone() + " 绑定", "我知道了");
                } else {
                    OperationHintDialog.getInstance().showHintDialog(context, callback, "友情提示", "该设备已绑定在您当前账户下", "我知道了");
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(context, var1.getErrorMessage());
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                HashMap<String, Object> signParams = new HashMap<>();
                signParams.put("productId", productId);
                signParams.put("errorCode", 1);
                HashMap<String, Object> commonParams = new HashMap<>();
                commonParams.put(Constants.FLAG_DEVICE_ID, deviceId);
                commonParams.put("discoverTime", SoftApStage.getInstance().getDiscoverTime());
                commonParams.put("discoverWay", discoverWay == 0 ? "coap" : "api");
                commonParams.put("deviceMac", mac);
                BindSoftApManager.bindDeviceLog(context, signParams, commonParams);
                SoftApStage.getInstance().setError(true);
                ActivityManagerUtil.getScreenManager().popAllActivity();
            }
        });
    }

    public static void findScanDeviceInfo(Context context, final XConfigSoftApCallback callback, String checkCode, final String productId, final String mac) {
        if (NetworkUtils.isNetworkAvailable(context)) {
            XCHttpSetting httpSetting = new XCHttpSetting();
            HashMap<String, Object> params = new HashMap<>();
            params.put("checkCode", checkCode);
            httpSetting.setParamsMap(params);
            httpSetting.setPath("device/getDiscovered");
            XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.BindSoftApManager.3
                public void onComplete(XCResponseBean var1) {
                    if (!TextUtils.isEmpty(var1.getData())) {
                        XcLogger.e("softAp", "21---通过接口查询到=" + var1.getData());
                        try {
                            JSONObject jsonObject = new JSONObject(var1.getData());
                            String mDeviceId = jsonObject.optString(Constants.FLAG_DEVICE_ID);
                            String mProductId = jsonObject.optString("productId");
                            String mMac = jsonObject.optString(MidEntity.TAG_MAC);
                            if (!TextUtils.isEmpty(mProductId) && !TextUtils.isEmpty(mMac) && !TextUtils.isEmpty(mDeviceId) && mProductId.equals(productId) && mMac.toUpperCase().equals(mac.toUpperCase()) && !TextUtils.isEmpty(mDeviceId)) {
                                callback.xconfigCoapCallback(1, mDeviceId, mac);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                public void onError(XCErrorMessage var1) {
                }
            });
        }
    }

    public static void bindDeviceLog(Context context, HashMap<String, Object> signParams, HashMap<String, Object> commonParams) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("device/bind/log");
        httpSetting.setParamsMap(signParams);
        httpSetting.setParamsMapNoSign(commonParams);
        XCRequest.getInstance().request(context, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.android.common.manager.BindSoftApManager.5
            public void onComplete(XCResponseBean var1) {
            }

            public void onError(XCErrorMessage var1) {
            }
        });
    }
}
