package com.youzan.systemweb;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.youzan.jsbridge.JsBridgeManager;
import com.youzan.spiderman.cache.CacheHandler;
import com.youzan.spiderman.cache.SpiderCacheCallback;
import com.youzan.spiderman.cache.SpiderMan;
import java.util.Map;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class YZBaseWebView extends WebView {
    private static final String TAG = "YZBaseWebView";
    private WebChromeClientWrapper mChromeClient;
    private JsBridgeManager mJsBridgeManager;
    private JsInjecter mJsInjecter;
    private WebViewClientWrapper mWebViewClient;

    public YZBaseWebView(Context context) {
        super(context);
        init(context);
    }

    public YZBaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public YZBaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public YZBaseWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Deprecated
    public YZBaseWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init(context);
    }

    private void init(Context context) {
        this.mJsInjecter = new JsInjecter(this);
        this.mJsBridgeManager = new JsBridgeManager(this.mJsInjecter.getDispatcher(), this.mJsInjecter.getDispatcherCompat());
        initSettings(context);
        this.mChromeClient = new WebChromeClientWrapper(this.mJsInjecter);
        this.mWebViewClient = new WebViewClientWrapper(this.mJsInjecter);
        super.setWebChromeClient(this.mChromeClient);
        super.setWebViewClient(this.mWebViewClient);
        injectCache();
    }

    private void initSettings(Context context) {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setCacheMode(-1);
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " youzan_container_android/2.0.7");
        if (Build.VERSION.SDK_INT >= 19) {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            int i = applicationInfo.flags & 2;
            applicationInfo.flags = i;
            if (i != 0) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath(context.getFilesDir().getPath());
        webSettings.setBuiltInZoomControls(false);
    }

    private void injectCache() {
        CacheHandler cacheHandler = new CacheHandler(getContext(), new 1(this));
        this.mWebViewClient.setCacheHandler(cacheHandler);
        this.mChromeClient.setCacheHandler(cacheHandler);
        SpiderMan.getInstance().initLru();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callCacheStatistic(String name, String desc, Map<String, String> staticData) {
        try {
            SpiderCacheCallback spiderCacheCallback = SpiderMan.getInstance().getSpiderCacheCallback();
            if (spiderCacheCallback != null) {
                spiderCacheCallback.onStatistic(name, desc, staticData);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // android.webkit.WebView
    public void setWebChromeClient(WebChromeClient client) {
        if (!(client instanceof WebChromeClientWrapper)) {
            this.mChromeClient.setDelegate(client);
        } else {
            super.setWebChromeClient(client);
        }
    }

    @Override // android.webkit.WebView
    public void setWebViewClient(WebViewClient client) {
        if (!(client instanceof WebViewClientWrapper)) {
            this.mWebViewClient.setDelegate(client);
        } else {
            super.setWebViewClient(client);
        }
    }

    public JsBridgeManager getJsBridgeManager() {
        return this.mJsBridgeManager;
    }
}
