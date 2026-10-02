package com.youzan.systemweb;

import android.webkit.WebView;
import com.youzan.jsbridge.method.JsMethod;
import com.youzan.jsbridge.subscriber.MethodSubscriber;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public abstract class JsSubscriber implements MethodSubscriber {
    private JsTrigger mJstrigger;
    private WebView mWebView;

    public abstract void onCall(WebView webView, JsMethod jsMethod, JsTrigger jsTrigger);

    public void withCall(WebView webView, JsTrigger jsTrigger) {
        this.mWebView = webView;
        this.mJstrigger = jsTrigger;
    }

    public final void onCall(JsMethod method) {
        onCall(this.mWebView, method, this.mJstrigger);
    }
}
