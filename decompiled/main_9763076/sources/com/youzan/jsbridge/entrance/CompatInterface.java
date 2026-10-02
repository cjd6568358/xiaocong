package com.youzan.jsbridge.entrance;

import android.support.annotation.Keep;
import android.webkit.JavascriptInterface;
import com.youzan.jsbridge.dispatcher.MethodDispatcher;
import com.youzan.jsbridge.method.JsMethodCompat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public class CompatInterface {
    private MethodDispatcher<JsMethodCompat> mDispatcher;

    public CompatInterface(MethodDispatcher<JsMethodCompat> dispatcher) {
        this.mDispatcher = dispatcher;
    }

    @Keep
    @JavascriptInterface
    public void putData(String params) {
        dispatch("putData", params);
    }

    @Keep
    @JavascriptInterface
    public void gotoWebview(String params) {
        dispatch("gotoWebview", params);
    }

    @Keep
    @JavascriptInterface
    public void gotoNative(String params) {
        dispatch("gotoNative", params);
    }

    @Keep
    @JavascriptInterface
    public void doAction(String params) {
        dispatch("doAction", params);
    }

    @Keep
    @JavascriptInterface
    public void getData(String params) {
        dispatch("getData", params);
    }

    @Keep
    @JavascriptInterface
    public void configNative(String params) {
        dispatch("configNative", params);
    }

    @Keep
    @JavascriptInterface
    public void setRightMenu(String params) {
        dispatch("setRightMenu", params);
    }

    @Keep
    @JavascriptInterface
    public void turnOffPullDownRefresh(String params) {
        dispatch("turnOffPullDownRefresh", params);
    }

    @Keep
    @JavascriptInterface
    public void webReady(String params) {
        dispatch("webReady", params);
    }

    @Keep
    @JavascriptInterface
    public void returnShareData(String params) {
        dispatch("returnShareData", params);
    }

    @Keep
    @JavascriptInterface
    public void getUserInfo(String params) {
        dispatch("getUserInfo", params);
    }

    private void dispatch(String methodName, String params) {
        JsMethodCompat method = new JsMethodCompat(methodName, params);
        this.mDispatcher.dispatch(method);
    }
}
