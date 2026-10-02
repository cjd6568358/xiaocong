package com.xiaocong.smarthome.sdk.network.wg;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.xiaocong.smarthome.network.util.NetworkUtils;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.http.util.SignatureUtil;
import com.xiaocong.smarthome.sdk.openapi.constant.XCConfig;
import com.xiaocong.smarthome.sdk.openapi.util.XCHelp;
import com.xiaocong.smarthome.util.DeviceInfoUtils;
import com.xiaocong.smarthome.xcnetwork.wg.WgReqAsyncService;
import java.util.HashMap;
import java.util.Map;
import rx.Observable;
import rx.functions.Func1;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class BaseWgRequest implements Func1<String, XCResponseBean> {
    private WgReqAsyncService<XCResponseBean> wgReqAsync;

    public BaseWgRequest() {
        String url = XCHelp.getString("config_http_url", "");
        this.wgReqAsync = new WgReqAsyncService<>(url.length() < 1 ? "https://gw.ixiaocong.com/" : url);
        this.wgReqAsync.setResultFunc(this);
    }

    @Override // rx.functions.Func1
    public XCResponseBean call(String text) {
        XCResponseBean bean = null;
        JSONObject jo = JSONObject.parseObject(text);
        if (jo != null) {
            bean = new XCResponseBean();
            bean.setCode(jo.getInteger("code"));
            bean.setMsg(jo.getString("msg"));
            Object data = jo.get("data");
            if (data != null) {
                bean.setData(data.toString());
            }
            bean.setSuccess(jo.getBoolean("success"));
        }
        return bean;
    }

    public Observable<XCResponseBean> wgRequest(Context context, XCHttpSetting httpSetting) {
        preHandlerRequestParams(context, httpSetting);
        return this.wgReqAsync.wgReq(httpSetting.getPath(), new HashMap(), httpSetting.getParamsMap(), new HashMap());
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
        if (httpSetting.getParamsMapNoSign().size() > 0) {
            for (Map.Entry<String, Object> entry : httpSetting.getParamsMapNoSign().entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
                bizParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }
    }
}
