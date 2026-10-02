package com.youzan.systemweb;

import android.os.Handler;
import android.webkit.WebView;
import com.youzan.jsbridge.dispatcher.BridgeTrigger;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone3073100.dex */
public class JsTrigger extends BridgeTrigger {
    private Handler mMainThreadHandler = new Handler();
    private WebView mWebView;

    public JsTrigger(WebView webView) {
        this.mWebView = webView;
    }
}
