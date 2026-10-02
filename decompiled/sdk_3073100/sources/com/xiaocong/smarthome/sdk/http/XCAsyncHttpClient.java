package com.xiaocong.smarthome.sdk.http;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.client.CommonAsyncHttpClient;
import com.xiaocong.smarthome.network.interfaces.IHttpRequest;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.callback.XCDownloadFileCallBack;
import com.xiaocong.smarthome.sdk.http.util.SignatureUtil;
import com.xiaocong.smarthome.sdk.openapi.constant.XCConfig;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import com.xiaocong.smarthome.util.DeviceInfoUtils;
import java.io.File;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCAsyncHttpClient {
    public static IHttpRequest sendOpenHttpRequest(Context mContext, XCHttpSetting httpSetting) {
        return CommonAsyncHttpClient.getInstance().sendHttpRequest(mContext, httpSetting);
    }

    public static IHttpRequest uploadFile(Context mContext, XCHttpSetting httpSetting) {
        preHandlerRequestParams(mContext, httpSetting);
        return CommonAsyncHttpClient.getInstance().uploadFile(mContext, httpSetting);
    }

    public static IHttpRequest downloadFile(Context mContext, XCHttpSetting httpSetting) {
        preHandlerRequestParams(mContext, httpSetting);
        XCDownloadFileCallBack callBack = (XCDownloadFileCallBack) httpSetting.getCallback();
        callBack.setDestinationFile(new File(httpSetting.getDestinationFile()));
        return CommonAsyncHttpClient.getInstance().downloadFile(mContext, httpSetting);
    }

    private static void preHandlerRequestParams(Context mContext, XCHttpSetting httpSetting) {
        if (TextUtils.isEmpty(httpSetting.getUrl())) {
            String url = XCHelp.getString("config_http_url", "");
            if (url.length() < 1) {
                url = "https://gw.ixiaocong.com/";
            }
            httpSetting.setUrl(url);
        }
        HashMap<String, Object> bizParams = httpSetting.getParamsMap();
        if (bizParams == null) {
            bizParams = new HashMap<>();
            httpSetting.setParamsMap(bizParams);
        }
        HashMap<String, Object> baseParams = new HashMap<>();
        String xc_token = XCConfig.getInstance().getToken();
        long xc_timestamp = System.currentTimeMillis();
        String appId = XCConfig.getInstance().getAppId();
        String clientId = XCHelp.mClientId;
        String uuid = XCHelp.getUUID();
        String clientKey = XCHelp.mClientKey;
        String appKey = XCConfig.getInstance().getAppKey();
        baseParams.put("xc-token", xc_token);
        baseParams.put("xc-timestamp", xc_timestamp + "");
        baseParams.put("xc-sign", SignatureUtil.signMD5(xc_token, appId, clientId, clientKey, xc_timestamp, uuid, bizParams, httpSetting.getNeedConfig(), httpSetting.getNeedSign(), appKey));
        baseParams.put("appId", appId);
        baseParams.put("clientId", clientId);
        baseParams.put("udid", uuid);
        baseParams.put("platform", "phone");
        baseParams.put("clientVersion", XCHelp.VERSION_NAME);
        baseParams.put("os", "android");
        baseParams.put("osVersion", DeviceInfoUtils.getDeviceVersion(mContext));
        baseParams.put("network", NetworkUtils.getNetWorkType(mContext));
        baseParams.put("brand", DeviceInfoUtils.getBrandName(mContext));
        baseParams.put("model", DeviceInfoUtils.getPhoneModel(mContext));
        baseParams.put("screen", DeviceInfoUtils.getScreenHeight(mContext) + "x" + DeviceInfoUtils.getScreenWidth(mContext));
        if (ActivityCompat.checkSelfPermission(mContext, "android.permission.READ_PHONE_STATE") == 0) {
            baseParams.put("clientUdid", DeviceInfoUtils.getDeviceId(mContext));
        } else {
            baseParams.put("clientUdid", "Unauthorized");
        }
        baseParams.put("channel", XCHelp.getAppMetaData(mContext, "XC_APP_CHANNEL"));
        bizParams.putAll(baseParams);
        if (!httpSetting.getParamsMapNoSign().isEmpty()) {
            bizParams.putAll(httpSetting.getParamsMapNoSign());
        }
    }
}
