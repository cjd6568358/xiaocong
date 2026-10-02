package com.ixiaocong.smarthome.phone.rn.module;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeMap;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.tencent.android.tpush.common.Constants;
import com.tencent.android.tpush.common.MessageKey;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNDeviceInfoModule extends ReactContextBaseJavaModule {
    private Context mContext;

    public RNDeviceInfoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "DeviceInfo";
    }

    @ReactMethod
    public String getDeviceInfo(String deviceId) {
        XcLogger.i("RNDeviceInfoModule", "Get device info for:" + deviceId);
        return "TestInfo";
    }

    @ReactMethod
    public void getDeviceSnapshots(String deviceId, int pageSize, String queryId, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("pageSize", pageSize + Constants.MAIN_VERSION_TAG);
        paramsNoSign.put("queryId", queryId);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("device/snapshots");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNDeviceInfoModule.1
            public void onComplete(XCResponseBean var1) {
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                    XcLogger.e("callback", var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNDeviceInfoModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void getDeviceElectricity(String deviceId, String type, String date, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("type", type);
        params.put(MessageKey.MSG_DATE, Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("electricity/count");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNDeviceInfoModule.2
            public void onComplete(XCResponseBean var1) {
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNDeviceInfoModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void getDeviceElectList(String deviceId, String queryId, String pageSize, String type, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("queryId", queryId);
        params.put("pageSize", pageSize);
        params.put("type", type);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("electricity/list");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNDeviceInfoModule.3
            public void onComplete(XCResponseBean var1) {
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                    XcLogger.e("callback", var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNDeviceInfoModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void request(String url, ReadableMap noSignParams, ReadableMap signParams, final Promise promise) {
        try {
            if (TextUtils.isEmpty(url)) {
                promise.reject("url is empty");
                return;
            }
            XCHttpSetting httpSetting = new XCHttpSetting();
            new HashMap();
            new HashMap();
            ReadableNativeMap SignReadableNativeMap = (ReadableNativeMap) signParams;
            HashMap<String, Object> params = SignReadableNativeMap.toHashMap();
            ReadableNativeMap noSignReadableNativeMap = (ReadableNativeMap) noSignParams;
            HashMap<String, Object> paramsNoSign = noSignReadableNativeMap.toHashMap();
            if (!params.isEmpty()) {
                httpSetting.setParamsMap(params);
            }
            if (!paramsNoSign.isEmpty()) {
                httpSetting.setParamsMapNoSign(paramsNoSign);
            }
            httpSetting.setPath(url);
            XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNDeviceInfoModule.4
                public void onComplete(XCResponseBean var1) {
                    promise.resolve(JSON.toJSONString(var1));
                }

                public void onError(XCErrorMessage var1) {
                    promise.reject(var1.getErrorMessage());
                    ToastUtils.showShort(RNDeviceInfoModule.this.mContext, var1.getErrorMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }

    @ReactMethod
    public void requestTimeout(String url, ReadableMap noSignParams, ReadableMap signParams, String timeout, final Promise promise) {
        try {
            if (TextUtils.isEmpty(url)) {
                promise.reject("url is empty");
                return;
            }
            XCHttpSetting httpSetting = new XCHttpSetting();
            new HashMap();
            new HashMap();
            int httpTimeout = Integer.valueOf(timeout).intValue();
            if (httpTimeout > 0) {
                httpSetting.setHttpTimeout(httpTimeout);
            }
            ReadableNativeMap SignReadableNativeMap = (ReadableNativeMap) signParams;
            HashMap<String, Object> params = SignReadableNativeMap.toHashMap();
            ReadableNativeMap noSignReadableNativeMap = (ReadableNativeMap) noSignParams;
            HashMap<String, Object> paramsNoSign = noSignReadableNativeMap.toHashMap();
            if (!params.isEmpty()) {
                httpSetting.setParamsMap(params);
            }
            if (!paramsNoSign.isEmpty()) {
                httpSetting.setParamsMapNoSign(paramsNoSign);
            }
            httpSetting.setPath(url);
            XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.RNDeviceInfoModule.5
                public void onComplete(XCResponseBean var1) {
                    promise.resolve(JSON.toJSONString(var1));
                }

                public void onError(XCErrorMessage var1) {
                    promise.reject(var1.getErrorMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            promise.reject(e);
        }
    }
}
