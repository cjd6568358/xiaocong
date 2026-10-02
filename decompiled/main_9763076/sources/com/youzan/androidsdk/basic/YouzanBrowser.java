package com.youzan.androidsdk.basic;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import com.tencent.android.tpush.common.Constants;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.basic.tool.WebParameter;
import com.youzan.androidsdk.basic.tool.a;
import com.youzan.androidsdk.basic.tool.c;
import com.youzan.androidsdk.basic.tool.e;
import com.youzan.androidsdk.basic.web.plugin.ChromeClientWrapper;
import com.youzan.androidsdk.basic.web.plugin.WebClientWrapper;
import com.youzan.androidsdk.event.Event;
import com.youzan.androidsdk.event.EventCenter;
import com.youzan.androidsdk.event.EventSubscriber;
import com.youzan.androidsdk.tool.Preference;
import com.youzan.androidsdk.tool.UserAgent;
import com.youzan.androidsdk.ui.YouzanClient;
import com.youzan.jsbridge.JsBridgeManager;
import com.youzan.spiderman.cache.SpiderCacheCallback;
import com.youzan.spiderman.cache.SpiderMan;
import com.youzan.systemweb.WebChromeClientWrapper;
import com.youzan.systemweb.WebViewClientWrapper;
import com.youzan.systemweb.YZBaseWebView;
import com.youzan.systemweb.YZWebSDK;

/* JADX INFO: loaded from: C:\workspace\xiaocong\dex\com.ixiaocong.smarthome.phone9763076.dex */
public class YouzanBrowser extends YZBaseWebView implements YouzanClient {

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private static final int f134 = 2000;

    /* JADX INFO: renamed from: ʻ, reason: contains not printable characters */
    private SpiderCacheCallback f135;

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private volatile boolean f136;

    /* JADX INFO: renamed from: ˎ, reason: contains not printable characters */
    private ChromeClientWrapper f137;

    /* JADX INFO: renamed from: ˏ, reason: contains not printable characters */
    private WebClientWrapper f138;

    /* JADX INFO: renamed from: ᐝ, reason: contains not printable characters */
    private EventCenter f139;

    @Deprecated
    public interface OnChooseFile {
        @Deprecated
        void onWebViewChooseFile(Intent intent, int i) throws ActivityNotFoundException;
    }

    public YouzanBrowser(Context context) {
        super(context);
        this.f136 = false;
        m114(context);
    }

    public YouzanBrowser(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.f136 = false;
        m114(context);
    }

    public YouzanBrowser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.f136 = false;
        m114(context);
    }

    @TargetApi(21)
    public YouzanBrowser(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.f136 = false;
        m114(context);
    }

    @Deprecated
    public YouzanBrowser(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        this.f136 = false;
        m114(context);
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private void m114(Context context) {
        if (!isInEditMode()) {
            if (!YouzanSDK.isReady()) {
                throw new IllegalStateException("You should init YouzanSDK at first!!!");
            }
            this.f139 = new EventCenter();
            Preference.renew(context);
            initWrappers(context, null, null);
            m113();
            m117(context);
            m116();
            postDelayed(new 1(this, context), 2000L);
        }
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private void m117(Context context) {
        a.b.ˊ(context);
        a.b.ˎ(context, "6.3.3");
        hideTopbar(true);
        WebParameter.initWebViewParameter(this);
        WebParameter.webViewUAConfiguration(this, UserAgent.clintId, Constants.MAIN_VERSION_TAG);
        WebParameter.blockDangerJsInterface(this);
    }

    protected void initWrappers(Context context, ChromeClientWrapper chromeClientWrapper, WebClientWrapper webClientWrapper) {
        if (chromeClientWrapper != null) {
            this.f137 = chromeClientWrapper;
        } else {
            this.f137 = new ChromeClientWrapper(this, this.f139);
        }
        if (webClientWrapper != null) {
            this.f138 = webClientWrapper;
        } else {
            this.f138 = new WebClientWrapper(context);
        }
        super.setWebChromeClient(this.f137);
        super.setWebViewClient(this.f138);
    }

    /* JADX INFO: renamed from: ˊ, reason: contains not printable characters */
    private void m113() {
        this.f135 = new 2(this);
        YZWebSDK.setWeakRefCacheCallback(this.f135);
        SpiderMan.getInstance().initLru();
    }

    /* JADX INFO: renamed from: ˋ, reason: contains not printable characters */
    private void m116() {
        setOnLongClickListener(new 3(this));
    }

    @Override // com.youzan.androidsdk.ui.YouzanClient
    public int getPageType() {
        return 1;
    }

    @Override // com.youzan.androidsdk.ui.YouzanClient
    public final boolean pageGoBack() {
        if (!this.f136) {
            return false;
        }
        if (Build.VERSION.SDK_INT <= 19) {
            return this.f138.pageGoBack(this);
        }
        if (!pageCanGoBack()) {
            return false;
        }
        if (WebParameter.shouldSkipUrl(WebParameter.getPreviousUrl(this))) {
            goBackOrForward(-2);
        } else {
            goBack();
        }
        return true;
    }

    @Override // com.youzan.androidsdk.ui.YouzanClient
    public final boolean pageCanGoBack() {
        if (Build.VERSION.SDK_INT <= 19) {
            return !TextUtils.isEmpty(this.f138.getUrl());
        }
        return WebParameter.validPreviousUrl(this) && canGoBack();
    }

    @Override // com.youzan.androidsdk.ui.YouzanClient
    public final void sharePage() {
        c.ˊ(this);
    }

    @Override // com.youzan.systemweb.YZBaseWebView, android.webkit.WebView
    public final void setWebChromeClient(WebChromeClient client) {
        if ((client instanceof WebChromeClientWrapper) || (client instanceof ChromeClientWrapper)) {
            super.setWebChromeClient(client);
        } else {
            this.f137.setDelegate(client);
        }
    }

    @Override // com.youzan.systemweb.YZBaseWebView, android.webkit.WebView
    public final void setWebViewClient(WebViewClient client) {
        if ((client instanceof WebViewClientWrapper) || (client instanceof WebClientWrapper)) {
            super.setWebViewClient(client);
        } else {
            this.f138.setDelegate(client);
        }
    }

    public final void hideTopbar(boolean hide) {
        a.b.ˊ(getContext(), hide);
    }

    @Override // com.youzan.androidsdk.ui.YouzanClient
    public final void subscribe(Event event) {
        JsBridgeManager jsBridgeManager = getJsBridgeManager();
        jsBridgeManager.subscribe(new EventSubscriber(event));
        this.f139.subscribe(event);
    }

    @Override // com.youzan.androidsdk.ui.YouzanClient
    public void sync(YouzanToken token) {
        e.ˊ(getContext(), token);
        reload();
        if (token != null) {
            YZWebSDK.syncToken(token.getAccessToken());
        }
    }

    @Override // com.youzan.androidsdk.ui.YouzanClient
    public boolean syncNot() {
        if (pageCanGoBack()) {
            return pageGoBack();
        }
        return false;
    }

    public final boolean isReceiveFileForWebView(int requestCode, Intent data) {
        return receiveFile(requestCode, data);
    }

    @Override // com.youzan.androidsdk.ui.YouzanClient
    public boolean receiveFile(int requestCode, Intent data) {
        if (requestCode != this.f137.ˊ.intValue()) {
            return false;
        }
        this.f137.receiveImage(data);
        return true;
    }

    @Deprecated
    public void setOnChooseFileCallback(OnChooseFile listener) {
        subscribe(new 4(this, listener));
    }

    @Override // android.webkit.WebView
    public void destroy() {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
        removeAllViews();
        destroyDrawingCache();
        clearCache(true);
        SpiderMan.getInstance().unInitLru();
        super.destroy();
    }
}
