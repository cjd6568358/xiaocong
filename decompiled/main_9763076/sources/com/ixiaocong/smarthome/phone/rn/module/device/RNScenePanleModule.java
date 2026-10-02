package com.ixiaocong.smarthome.phone.rn.module.device;

import android.content.Context;
import android.text.TextUtils;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.httplib.model.request.IftttUpdateRelateModel;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNScenePanleModule extends ReactContextBaseJavaModule {
    private Context mContext;

    public RNScenePanleModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "ScenePanleModule";
    }

    @ReactMethod
    public void getRelationParamList(String deviceId, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        HashMap<String, Object> paramsNoSign = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        paramsNoSign.put("excludes", Constants.MAIN_VERSION_TAG);
        httpSetting.setParamsMap(params);
        httpSetting.setParamsMapNoSign(paramsNoSign);
        httpSetting.setPath("relation/device/parameter/list");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNScenePanleModule.1
            public void onComplete(XCResponseBean var1) {
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                    XcLogger.e("callback", var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNScenePanleModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void getSceneDetail(String deviceId, String productParameterId, String threshold, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("productParameterId", productParameterId);
        params.put("triggerCondition", "eq");
        params.put("threshold", threshold);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("relation/sceneDetail");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNScenePanleModule.2
            public void onComplete(XCResponseBean var1) {
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNScenePanleModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void updatePanelData(String deviceId, String threshold, String triggerId, String triggerName, String triggerCondition, String triggerIcon, int status, String parameterId, String parameterType, String parameterKey, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("deviceTriggerId", triggerId);
        params.put("triggerName", triggerName);
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("productParameterId", parameterId);
        params.put("triggerCondition", triggerCondition);
        params.put("threshold", threshold);
        params.put("triggerIcon", triggerIcon);
        params.put("status", status + Constants.MAIN_VERSION_TAG);
        params.put("parameterType", parameterType);
        params.put("parameterKey", parameterKey);
        params.put("alias", Constants.MAIN_VERSION_TAG);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("relation/tigger/device/update");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNScenePanleModule.3
            public void onComplete(XCResponseBean var1) {
                callback.invoke(0);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNScenePanleModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void iftttUpdateAction(String actionId, String transactionId, String actionType, final Callback callback) {
        HashMap<String, Object> signParams = new HashMap<>();
        signParams.put("actionId", actionId);
        HashMap<String, Object> params = new HashMap<>();
        params.put("transactionId", transactionId);
        params.put("actionType", actionType);
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setParamsMap(signParams);
        httpSetting.setParamsMapNoSign(params);
        httpSetting.setPath("ifttt/updateAction");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNScenePanleModule.4
            public void onComplete(XCResponseBean var1) {
                callback.invoke(0);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNScenePanleModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    private void addPanelData(String deviceId, String threshold, String parameterId, String parameterType, String parameterKey, String sceneName, String sceneIcon, int sceneId, final Callback callback) {
        List<IftttUpdateRelateModel> relateList = new ArrayList<>();
        IftttUpdateRelateModel relateModel = new IftttUpdateRelateModel();
        relateModel.setTransactionId(sceneId + Constants.MAIN_VERSION_TAG);
        relateModel.setRelateName(sceneName);
        relateModel.setRelateIcon(sceneIcon);
        relateModel.setRelateType("scene");
        relateModel.setParameterType(parameterType);
        relateModel.setParameterKey(parameterKey);
        relateModel.setProductParameterId(parameterId);
        relateList.add(relateModel);
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put("triggerName", sceneName);
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("productParameterId", parameterId);
        params.put("parameterType", parameterType);
        params.put("parameterKey", parameterKey);
        params.put("triggerCondition", "eq");
        params.put("threshold", threshold);
        params.put("triggerIcon", sceneIcon);
        params.put("status", "1");
        params.put("relateList", new Gson().toJson(relateList));
        params.put("alias", Constants.MAIN_VERSION_TAG);
        httpSetting.setNeedSign(false);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("relation/add");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNScenePanleModule.5
            public void onComplete(XCResponseBean var1) {
                callback.invoke(0);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNScenePanleModule.this.mContext, var1.getErrorMessage());
            }
        });
    }
}
