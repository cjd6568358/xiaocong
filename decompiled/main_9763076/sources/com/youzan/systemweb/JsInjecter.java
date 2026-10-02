package com.youzan.systemweb;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.JsPromptResult;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.youzan.jsbridge.dispatcher.MethodDispatcher;
import com.youzan.jsbridge.entrance.CommonEntrance;
import com.youzan.jsbridge.entrance.CommonInterface;
import com.youzan.jsbridge.entrance.CompatEntrance;
import com.youzan.jsbridge.entrance.CompatInterface;
import com.youzan.jsbridge.entrance.JsBridgeEntrance;
import com.youzan.jsbridge.internal.JsMethodModel;
import com.youzan.jsbridge.internal.JsMethodParser;
import com.youzan.jsbridge.method.JsMethod;
import com.youzan.jsbridge.method.JsMethodCompat;
import com.youzan.jsbridge.util.BridgeUtil;
import com.youzan.jsbridge.util.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class JsInjecter {
    private MethodDispatcher<JsMethod> mDispatcher;
    private MethodDispatcher<JsMethodCompat> mDispatcherCompat;
    private List<JsBridgeEntrance> mEntrances;
    private boolean mIsJsInjected = false;
    private JsMethodParser mJsMethodParser;
    private String mLastUrl;
    private WebView mWebView;

    public JsInjecter(WebView webView) {
        this.mWebView = webView;
        init();
    }

    MethodDispatcher<JsMethod> getDispatcher() {
        return this.mDispatcher;
    }

    @Deprecated
    MethodDispatcher<JsMethodCompat> getDispatcherCompat() {
        return this.mDispatcherCompat;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void init() {
        String packageDir;
        this.mDispatcher = new JsMethodDispatcher(this.mWebView);
        this.mDispatcherCompat = new JsMethodDispatcher(this.mWebView);
        if (BridgeUtil.shouldInjectJs()) {
            this.mEntrances = new ArrayList();
            this.mJsMethodParser = new JsMethodParser();
            WebSettings settings = this.mWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (Build.VERSION.SDK_INT < 19 && (packageDir = BridgeUtil.getDataPath(this.mWebView.getContext())) != null) {
                File dataDir = new File(packageDir, "databases/");
                settings.setDatabasePath(dataDir.getAbsolutePath());
            }
            blockJsInterface(this.mWebView);
            addEntrance(new CompatEntrance());
            addEntrance(new CommonEntrance());
            return;
        }
        initJavascriptInterfaces(this.mDispatcher, this.mDispatcherCompat);
    }

    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
    @TargetApi(17)
    void initJavascriptInterfaces(MethodDispatcher<JsMethod> dispatcher, MethodDispatcher<JsMethodCompat> dispatcherCompat) {
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.addJavascriptInterface(new CommonInterface(dispatcher), "YZAndroidJS");
        this.mWebView.addJavascriptInterface(new CompatInterface(dispatcherCompat), "androidJS");
    }

    private void blockJsInterface(WebView web) {
        web.removeJavascriptInterface("searchBoxJavaBridge_");
        web.removeJavascriptInterface("accessibility");
        web.removeJavascriptInterface("accessibilityTraversal");
    }

    void addEntrance(JsBridgeEntrance entrance) {
        this.mEntrances.add(entrance);
    }

    public void shouldInjectJs(WebView webView, int newProgress) {
        if (BridgeUtil.shouldInjectJs()) {
            if (newProgress <= 25) {
                this.mIsJsInjected = false;
            } else if (!this.mIsJsInjected && !TextUtils.equals(this.mLastUrl, webView.getUrl())) {
                injectJs(webView);
                this.mLastUrl = webView.getUrl();
                this.mIsJsInjected = true;
            }
            if (newProgress > 75 && !this.mIsJsInjected) {
                injectJs(webView);
                this.mLastUrl = webView.getUrl();
                this.mIsJsInjected = true;
            }
        }
    }

    private void injectJs(WebView web) {
        for (JsBridgeEntrance item : this.mEntrances) {
            web.loadUrl(item.toJavaScript());
        }
    }

    public void injectJsReady(WebView webView) {
        webView.loadUrl("javascript:window.isReadyForYouZanJSBridge=true;");
    }

    public boolean jsPrompt(String message, JsPromptResult result) {
        if (!BridgeUtil.shouldInjectJs() || !parsePromptMessage(message)) {
            return false;
        }
        result.confirm("{\"code\": 200, \"result\":\"\"}");
        return true;
    }

    private boolean parsePromptMessage(String message) {
        JsMethodModel jsMethodModel = this.mJsMethodParser.deserialize(message);
        if (jsMethodModel == null) {
            return false;
        }
        JsMethod method = this.mJsMethodParser.parse(jsMethodModel);
        if (method != null) {
            Logger.d("Dispatching method " + method.getName());
            return this.mDispatcher.dispatch(method);
        }
        JsMethodCompat compatMethod = this.mJsMethodParser.parseCompat(jsMethodModel);
        Logger.d("Dispatching compat method " + compatMethod.getName());
        return this.mDispatcherCompat.dispatch(compatMethod);
    }
}
