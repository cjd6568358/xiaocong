package com.ixiaocong.smarthome.phone.rn.module.device;

import android.content.Context;
import android.text.TextUtils;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.ixiaocong.smarthome.phone.android.complete.toast.ToastUtils;
import com.tencent.android.tpush.common.Constants;
import com.xiaocong.smarthome.httplib.helper.XcLogger;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.business.XCRequest;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class RNInfraredTransmitModule extends ReactContextBaseJavaModule {
    private Context mContext;

    public RNInfraredTransmitModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.mContext = reactContext;
    }

    @Override // com.facebook.react.bridge.NativeModule
    public String getName() {
        return "InfraredTransmitModule";
    }

    @ReactMethod
    public void getInfraredCodeList(String deviceId, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("infrared/code/list");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNInfraredTransmitModule.1
            public void onComplete(XCResponseBean var1) {
                if (!TextUtils.isEmpty(var1.getData().toString())) {
                    callback.invoke(var1.getData());
                    XcLogger.e("callback", var1.getData());
                }
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNInfraredTransmitModule.this.mContext, var1.getErrorMessage());
            }
        });
    }

    @ReactMethod
    public void addInfaredCode(String deviceId, int code, String codeName, final Callback callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        HashMap<String, Object> params = new HashMap<>();
        params.put(Constants.FLAG_DEVICE_ID, deviceId);
        params.put("code", code + Constants.MAIN_VERSION_TAG);
        params.put("codeName", codeName);
        httpSetting.setParamsMap(params);
        httpSetting.setPath("infrared/code/add");
        XCRequest.getInstance().request(this.mContext, httpSetting, new XCDataCallback<XCResponseBean>() { // from class: com.ixiaocong.smarthome.phone.rn.module.device.RNInfraredTransmitModule.2
            public void onComplete(XCResponseBean var1) {
                ToastUtils.showShort(RNInfraredTransmitModule.this.mContext, "命令新增成功");
                callback.invoke(true);
            }

            public void onError(XCErrorMessage var1) {
                ToastUtils.showShort(RNInfraredTransmitModule.this.mContext, var1.getErrorMessage());
            }
        });
    }
}
