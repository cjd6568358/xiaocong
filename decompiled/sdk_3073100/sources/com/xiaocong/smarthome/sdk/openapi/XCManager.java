package com.xiaocong.smarthome.sdk.openapi;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.mqtt.XCDeviceController;
import com.xiaocong.smarthome.sdk.mqtt.service.XCMqttService;
import com.xiaocong.smarthome.sdk.network.RequestNetwork;
import com.xiaocong.smarthome.sdk.network.WGRxObserver;
import com.xiaocong.smarthome.sdk.openapi.bean.XCConfigModel;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.constant.XCConfig;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import com.xiaocong.smarthome.util.log.XCLog;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public final class XCManager {
    private static XCManager mInstance;
    private boolean isLogin;
    private Context mApplicationContext;
    private boolean mInitialSuccess;

    private XCManager() {
    }

    public static XCManager getInstance() {
        if (mInstance == null) {
            synchronized (XCManager.class) {
                if (mInstance == null) {
                    mInstance = new XCManager();
                }
            }
        }
        return mInstance;
    }

    public void initialWithAppId(Context applicationContext, String appId, String appKey, XCDataCallback<String> callback) {
        this.mApplicationContext = applicationContext.getApplicationContext();
        XCHelp.getUUID();
        XCConfig.getInstance().setAppId(appId);
        XCHelp.putString("appId", appId);
        XCConfig.getInstance().setAppKey(appKey);
        XCHelp.putString("appKey", appKey);
        requestConfig(callback);
    }

    private void requestConfig(final XCDataCallback<String> callback) {
        XCHttpSetting httpSetting = new XCHttpSetting();
        httpSetting.setPath("client/config");
        httpSetting.setNeedSign(false);
        httpSetting.setNeedConfig(true);
        RequestNetwork.getInstance().request(this.mApplicationContext, httpSetting).subscribe(new WGRxObserver(this.mApplicationContext) { // from class: com.xiaocong.smarthome.sdk.openapi.XCManager.1
            @Override // com.xiaocong.smarthome.xcnetwork.RxObserver
            public void onSuccess(XCResponseBean wgResponseBean) {
                XCConfigModel configModel = (XCConfigModel) JSON.parseObject(wgResponseBean.getData(), XCConfigModel.class);
                XCHelp.putString("NLC_ahe_key", configModel.getClientKey());
                XCHelp.putString("vic_jastion_dd", configModel.getClientId());
                XCHelp.putString("vic_klmn_jast_like", configModel.getLiveServerUrl());
                XCHelp.putString("NLC_ahe_uid", configModel.getUid());
                XCHelp.putString("app_upgrade", configModel.getUpgrade());
                XCHelp.putString("app_upgradeMode", configModel.getUpgradeMode());
                XCHelp.putString("app_downloadUrl", configModel.getUpgradeDownloadUrl());
                XCHelp.putString("app_update_version", configModel.getUpgradeVersion());
                XCHelp.putString("app_update_info", configModel.getUpgradeIntro());
                XCHelp.updateConfig();
                XCManager.this.mInitialSuccess = true;
                if (callback != null) {
                    callback.onComplete(configModel.getUid());
                }
            }

            @Override // com.xiaocong.smarthome.sdk.network.WGRxObserver
            public void onFail(XCResponseBean wgResponseBean) {
                if (callback != null) {
                    callback.onError(new XCErrorMessage(wgResponseBean.getCode().intValue(), wgResponseBean.getMsg()));
                }
            }
        });
    }

    public void loginWithToken(String token) {
        this.isLogin = true;
        XCConfig.getInstance().setToken(token);
        XCHelp.putString("NLC_ahe_9l", token);
        startMqtt();
    }

    public void logout(Context context) {
        this.mInitialSuccess = false;
        this.isLogin = false;
        XCHelp.putString("NLC_ahe_9l", "");
        XCConfig.getInstance().setToken("");
        XCDeviceController.getInstance().XCDeviceControllerStop(context);
    }

    public boolean isInitialSuccess() {
        if (!this.mInitialSuccess) {
            if (!TextUtils.isEmpty(XCHelp.mClientId) && !TextUtils.isEmpty(XCHelp.mClientKey) && !TextUtils.isEmpty(XCHelp.mLive)) {
                this.mInitialSuccess = true;
            } else {
                String appId = XCConfig.getInstance().getAppId();
                String appKey = XCConfig.getInstance().getAppKey();
                if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appKey)) {
                    XCLog.i("initialWithAppId", "-----reconnection------");
                    requestConfig(new XCDataCallback<String>() { // from class: com.xiaocong.smarthome.sdk.openapi.XCManager.2
                        @Override // com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback
                        public void onComplete(String var1) {
                        }

                        @Override // com.xiaocong.smarthome.sdk.openapi.interfaces.XCErrorCallback
                        public void onError(XCErrorMessage var1) {
                        }
                    });
                }
            }
        }
        XCLog.i("isInitialSuccess", "-----" + this.mInitialSuccess + "------");
        return this.mInitialSuccess;
    }

    public void startMqtt() {
        if ((this.isLogin || this.mInitialSuccess) && this.mApplicationContext != null && NetworkUtils.isNetworkAvailable(this.mApplicationContext) && !XCMqttService.isConnected()) {
            XCLog.i("startMqtt", "start-----------");
            XCMqttService.actionStart(this.mApplicationContext);
        }
    }
}
