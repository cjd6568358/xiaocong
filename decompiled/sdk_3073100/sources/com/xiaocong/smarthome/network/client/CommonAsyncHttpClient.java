package com.xiaocong.smarthome.network.client;

import android.content.Context;
import android.text.TextUtils;
import com.xiaocong.smarthome.network.bean.CommonHttpSetting;
import com.xiaocong.smarthome.network.constant.CommonRequestType;
import com.xiaocong.smarthome.network.constant.NetworkConstant;
import com.xiaocong.smarthome.network.httplib.AsyncHttpClient;
import com.xiaocong.smarthome.network.httplib.RequestHandle;
import com.xiaocong.smarthome.network.httplib.RequestParams;
import com.xiaocong.smarthome.network.interfaces.IHttpRequest;
import com.xiaocong.smarthome.network.util.XCHttpLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.apache.http.entity.StringEntity;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CommonAsyncHttpClient implements IHttpRequest {
    RequestHandle mRequestHandle;

    private CommonAsyncHttpClient() {
    }

    public static CommonAsyncHttpClient getInstance() {
        return new CommonAsyncHttpClient();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.xiaocong.smarthome.network.interfaces.IHttpRequest
    public IHttpRequest sendHttpRequest(Context mContext, CommonHttpSetting httpSetting) {
        if (XCHttpLog.printLog) {
            XCHttpLog.e(httpSetting.toString());
        }
        String url = httpSetting.getUrl() + (!TextUtils.isEmpty(httpSetting.getPath()) ? httpSetting.getPath() : "");
        AsyncHttpClient httpClient = AsyncHttpClient.getInstance(url);
        if (httpSetting.getHttpTimeout() > 1000) {
            httpClient.setTimeout(httpSetting.getHttpTimeout());
        } else {
            httpClient.setTimeout(NetworkConstant.HTTP_TIMEOUT);
        }
        if (httpSetting.getHeaderMap() != null) {
            httpClient.getHeaderMap().putAll(httpSetting.getHeaderMap());
        }
        if (httpSetting.getCallback() != null) {
            httpSetting.getCallback().setWeakContext(new WeakReference<>(mContext));
            httpSetting.getCallback().setCommonHttpSetting(httpSetting);
        }
        switch (httpSetting.getHttpMethod()) {
            case GET:
                httpClient.get(url, mapToRequestParams(httpSetting.getParamsMap()), httpSetting.getCallback());
                return this;
            case POST:
                if (httpSetting.getRequstType() == null) {
                    this.mRequestHandle = httpClient.post(null, url, mapToRequestParams(httpSetting.getParamsMap()), httpSetting.getCallback());
                } else if (CommonRequestType.JSONENTITY.equals(httpSetting.getRequstType())) {
                    StringEntity entity = null;
                    try {
                        if (!TextUtils.isEmpty(httpSetting.getStringEntity())) {
                            entity = new StringEntity(httpSetting.getStringEntity(), "utf-8");
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    this.mRequestHandle = httpClient.post(null, url, entity, "application/json", httpSetting.getCallback());
                }
                return this;
            default:
                return this;
        }
    }

    @Override // com.xiaocong.smarthome.network.interfaces.IHttpRequest
    public IHttpRequest uploadFile(Context mContext, CommonHttpSetting httpSetting) {
        if (XCHttpLog.printLog) {
            XCHttpLog.e(httpSetting.toString());
        }
        String url = httpSetting.getUrl() + (!TextUtils.isEmpty(httpSetting.getPath()) ? httpSetting.getPath() : "");
        AsyncHttpClient httpClient = AsyncHttpClient.getInstance(url);
        if (httpSetting.getHttpTimeout() > 1000) {
            httpClient.setTimeout(httpSetting.getHttpTimeout());
        } else {
            httpClient.setTimeout(30000);
        }
        if (httpSetting.getHeaderMap() != null) {
            httpClient.getHeaderMap().putAll(httpSetting.getHeaderMap());
        }
        if (httpSetting.getCallback() != null) {
            httpSetting.getCallback().setWeakContext(new WeakReference<>(mContext));
            httpSetting.getCallback().setCommonHttpSetting(httpSetting);
        }
        this.mRequestHandle = httpClient.post(url, mapToRequestParams(httpSetting.getParamsMap()), httpSetting.getCallback());
        return this;
    }

    @Override // com.xiaocong.smarthome.network.interfaces.IHttpRequest
    public IHttpRequest downloadFile(Context mContext, CommonHttpSetting httpSetting) {
        if (XCHttpLog.printLog) {
            XCHttpLog.e(httpSetting.toString());
        }
        String url = httpSetting.getUrl() + (!TextUtils.isEmpty(httpSetting.getPath()) ? httpSetting.getPath() : "");
        AsyncHttpClient httpClient = AsyncHttpClient.getInstance(url);
        if (httpSetting.getHttpTimeout() > 1000) {
            httpClient.setTimeout(httpSetting.getHttpTimeout());
        } else {
            httpClient.setTimeout(30000);
        }
        if (httpSetting.getHeaderMap() != null) {
            httpClient.getHeaderMap().putAll(httpSetting.getHeaderMap());
        }
        if (httpSetting.getCallback() != null) {
            httpSetting.getCallback().setWeakContext(new WeakReference<>(mContext));
            httpSetting.getCallback().setCommonHttpSetting(httpSetting);
        }
        this.mRequestHandle = httpClient.get(url, mapToRequestParams(httpSetting.getParamsMap()), httpSetting.getCallback());
        return this;
    }

    @Override // com.xiaocong.smarthome.network.interfaces.IHttpRequest
    public void cancelRequest() {
        if (this.mRequestHandle != null && !this.mRequestHandle.isFinished()) {
            this.mRequestHandle.cancel(true);
        }
    }

    private RequestParams mapToRequestParams(Map<String, Object> params) {
        RequestParams requestParams = new RequestParams();
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof File) {
                    try {
                        requestParams.put(entry.getKey(), (File) entry.getValue());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    requestParams.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return requestParams;
    }
}
