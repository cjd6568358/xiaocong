package com.youzan.systemweb;

import android.webkit.WebView;
import com.youzan.jsbridge.method.JsMethodCompat;
import com.youzan.jsbridge.subscriber.MethodSubscriberCompat;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
@Deprecated
public abstract class JsSubscriberCompat implements MethodSubscriberCompat {
    private JsTrigger mJstrigger;
    private WebView mWebView;

    public abstract void onCall(WebView webView, JsMethodCompat jsMethodCompat, JsTrigger jsTrigger);

    public void withCall(WebView webView, JsTrigger jsTrigger) {
        this.mWebView = webView;
        this.mJstrigger = jsTrigger;
    }

    public final void onCall(JsMethodCompat method) {
        onCall(this.mWebView, method, this.mJstrigger);
    }
}
