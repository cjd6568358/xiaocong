package com.xiaocong.smarthome.sdk.openapi.business;

import android.content.Context;
import com.xiaocong.smarthome.network.httplib.TextHttpResponseHandler;
import com.xiaocong.smarthome.sdk.http.XCAsyncHttpClient;
import com.xiaocong.smarthome.sdk.http.bean.XCHttpSetting;
import com.xiaocong.smarthome.sdk.http.bean.XCResponseBean;
import com.xiaocong.smarthome.sdk.network.RequestNetwork;
import com.xiaocong.smarthome.sdk.network.WGRxObserver;
import com.xiaocong.smarthome.sdk.openapi.bean.XCErrorMessage;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCDataCallback;
import com.xiaocong.smarthome.sdk.openapi.interfaces.XCRequestService;
import com.xiaocong.smarthome.util.log.XCLog;
import com.xiaocong.smarthome.xcnetwork.utils.SPUtil;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import org.apache.http.Header;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class XCRequest implements XCRequestService {
    private static XCRequestService mInstance;

    public static XCRequestService getInstance() {
        if (mInstance == null) {
            synchronized (XCRequest.class) {
                if (mInstance == null) {
                    XCRequestService requestService = new XCRequest();
                    InvocationHandler invocationHandler = new XCServiceInvocationHandler(requestService);
                    mInstance = (XCRequestService) Proxy.newProxyInstance(requestService.getClass().getClassLoader(), requestService.getClass().getInterfaces(), invocationHandler);
                }
            }
        }
        return mInstance;
    }

    @Override // com.xiaocong.smarthome.sdk.openapi.interfaces.XCRequestService
    public void request(Context mContext, XCHttpSetting httpSetting, final XCDataCallback<XCResponseBean> callback) {
        RequestNetwork.getInstance().request(mContext, httpSetting).subscribe(new WGRxObserver(mContext) { // from class: com.xiaocong.smarthome.sdk.openapi.business.XCRequest.1
            @Override // com.xiaocong.smarthome.xcnetwork.RxObserver
            public void onSuccess(XCResponseBean wgResponseBean) {
                try {
                    callback.onComplete(wgResponseBean);
                } catch (Exception e) {
                    XCLog.e(e);
                    callback.onError(new XCErrorMessage(-1001, "数据解析异常"));
                }
            }

            @Override // com.xiaocong.smarthome.sdk.network.WGRxObserver
            public void onFail(XCResponseBean wgResponseBean) {
                super.onFail(wgResponseBean);
                callback.onError(new XCErrorMessage(wgResponseBean.getCode().intValue(), wgResponseBean.getMsg()));
            }
        });
    }

    @Override // com.xiaocong.smarthome.sdk.openapi.interfaces.XCRequestService
    public void openRequest(Context mContext, XCHttpSetting httpSetting, final XCDataCallback<String> callback) {
        httpSetting.setCallback(new TextHttpResponseHandler() { // from class: com.xiaocong.smarthome.sdk.openapi.business.XCRequest.2
            @Override // com.xiaocong.smarthome.network.httplib.TextHttpResponseHandler
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.onError(new XCErrorMessage(statusCode, responseString));
            }

            @Override // com.xiaocong.smarthome.network.httplib.TextHttpResponseHandler
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    callback.onComplete(responseString);
                } catch (Exception e) {
                    XCLog.e(e);
                    callback.onError(new XCErrorMessage(-1001, "数据解析异常"));
                }
            }
        });
        XCAsyncHttpClient.sendOpenHttpRequest(mContext, httpSetting);
    }

    @Override // com.xiaocong.smarthome.sdk.openapi.interfaces.XCRequestService
    public void okRequest(Context mContext, XCHttpSetting httpSetting, final XCDataCallback<XCResponseBean> callback) {
        RequestNetwork.getInstance().request(mContext, httpSetting).subscribe(new WGRxObserver(mContext) { // from class: com.xiaocong.smarthome.sdk.openapi.business.XCRequest.3
            @Override // com.xiaocong.smarthome.xcnetwork.RxObserver
            public void onSuccess(XCResponseBean wgResponseBean) {
                try {
                    callback.onComplete(wgResponseBean);
                } catch (Exception e) {
                    XCLog.e(e);
                    callback.onError(new XCErrorMessage(-1001, "数据解析异常"));
                }
            }

            @Override // com.xiaocong.smarthome.sdk.network.WGRxObserver
            public void onFail(XCResponseBean wgResponseBean) {
                super.onFail(wgResponseBean);
                callback.onError(new XCErrorMessage(wgResponseBean.getCode().intValue(), wgResponseBean.getMsg()));
            }
        });
    }

    @Override // com.xiaocong.smarthome.sdk.openapi.interfaces.XCRequestService
    public void removeRequstClient(Context context) {
        RequestNetwork.getInstance().removeRequst();
        SPUtil.getInstance(context).removeSP("mqtt_dns_ip");
        SPUtil.getInstance(context).removeSP("dns_ip");
    }
}
