package com.ixiaocong.smarthome.phone.rn.module;

import android.content.Context;
import android.text.TextUtils;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.loading.HttpLoadingHelper;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNDeviceParameterModule extends ReactContextBaseJavaModule {
    private Context mContext;

    public RNDeviceParameterModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "DeviceParameter";
    }

    @ReactMethod
    public void getParameter(String deviceId, int productId, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("parameter/list");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNDeviceParameterModule.1
            public void onComplete(XCResponseBean var1) {
                XcLogger.e("getParameter---", var1.getData());
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                    XcLogger.e("callback", var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNDeviceParameterModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void getGatewayChild(String gatewayId, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("gatewayId", gatewayId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("device/list/gw/child");
        HttpLoadingHelper.getInstance().showProcessLoading(getCurrentActivity());
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNDeviceParameterModule.2
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                    XcLogger.e("callback", var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(RNDeviceParameterModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void getRooAirData(String deviceId, String pageSize, String type, String date, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("pageSize", pageSize);
        params.put("type", type);
        params.put(MessageKey.MSG_DATE, date);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("room/air/v2");
        HttpLoadingHelper.getInstance().showProcessLoading(getCurrentActivity());
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNDeviceParameterModule.3
            public void onComplete(XCResponseBean var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                    XcLogger.e("callback", var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                HttpLoadingHelper.getInstance().dismissProcessLoading();
                ToastUtils.showShort(RNDeviceParameterModule.this.mContext, var1.getErrorMessage());
            }
        });
    }
}
