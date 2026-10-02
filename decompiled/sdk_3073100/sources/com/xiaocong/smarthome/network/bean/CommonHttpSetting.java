package com.xiaocong.smarthome.network.bean;

import com.xiaocong.smarthome.network.constant.CommonHttpMethod;
import com.xiaocong.smarthome.network.constant.CommonRequestType;
import com.xiaocong.smarthome.network.httplib.AsyncHttpResponseHandler;
import java.util.HashMap;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class CommonHttpSetting {
    private AsyncHttpResponseHandler callback;
    private String destinationFile;
    private int httpTimeout;
    private String path;
    private CommonRequestType requstType;
    private String stringEntity;
    private int type;
    private String url;
    private CommonHttpMethod httpMethod = CommonHttpMethod.POST;
    private HashMap<String, String> headerMap = new HashMap<>();
    private HashMap<String, Object> paramsMapNoSign = new HashMap<>();
    private HashMap<String, Object> paramsMap = new HashMap<>();
    private boolean removeCallbackWhenActivityDestroy = true;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CommonHttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(CommonHttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AsyncHttpResponseHandler getCallback() {
        return this.callback;
    }

    public void setCallback(AsyncHttpResponseHandler callback) {
        this.callback = callback;
    }

    public HashMap<String, String> getHeaderMap() {
        return this.headerMap;
    }

    public void setHeaderMap(HashMap<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public CommonRequestType getRequstType() {
        return this.requstType;
    }

    public void setRequstType(CommonRequestType requstType) {
        this.requstType = requstType;
    }

    public HashMap<String, Object> getParamsMap() {
        return this.paramsMap;
    }

    public void setParamsMap(HashMap<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }

    public String getStringEntity() {
        return this.stringEntity;
    }

    public void setStringEntity(String stringEntity) {
        this.stringEntity = stringEntity;
    }

    public String toString() {
        return "url=" + this.url + "\npath=" + this.path + "\nhttpMethod=" + this.httpMethod + "\r\nheaderMap=" + this.headerMap + "\nparamsMap=" + this.paramsMap + "\nstringEntity=" + this.stringEntity;
    }

    public boolean isRemoveCallbackWhenActivityDestroy() {
        return this.removeCallbackWhenActivityDestroy;
    }

    public void setRemoveCallbackWhenActivityDestroy(boolean removeCallbackWhenActivityDestroy) {
        this.removeCallbackWhenActivityDestroy = removeCallbackWhenActivityDestroy;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HashMap<String, Object> getParamsMapNoSign() {
        return this.paramsMapNoSign;
    }

    public void setParamsMapNoSign(HashMap<String, Object> paramsMapNoSign) {
        this.paramsMapNoSign = paramsMapNoSign;
    }

    public String getDestinationFile() {
        return this.destinationFile;
    }

    public void setDestinationFile(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    public int getHttpTimeout() {
        return this.httpTimeout;
    }

    public void setHttpTimeout(int httpTimeout) {
        this.httpTimeout = httpTimeout;
    }
}
