package com.youzan.jsbridge.entrance;

import android.support.annotation.Keep;
import android.webkit.JavascriptInterface;
import com.google.gson.Gson;
import com.youzan.jsbridge.dispatcher.MethodDispatcher;
import com.youzan.jsbridge.method.JsMethod;
import com.youzan.jsbridge.method.Method;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class CommonInterface {
    private MethodDispatcher<JsMethod> mMethodDispatcher;

    public CommonInterface(MethodDispatcher<JsMethod> dispatcher) {
        this.mMethodDispatcher = dispatcher;
    }

    @Keep
    @JavascriptInterface
    public void doCall(String params) {
        this.mMethodDispatcher.dispatch((Method) new Gson().fromJson(params, JsMethod.class));
    }
}
